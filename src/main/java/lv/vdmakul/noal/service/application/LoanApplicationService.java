package lv.vdmakul.noal.service.application;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.domain.repository.LoanApplicationRepository;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import lv.vdmakul.noal.service.application.analyser.ApplicationRiskAnalyzer;
import lv.vdmakul.noal.service.application.analyser.RiskAnalysisFailException;
import lv.vdmakul.noal.service.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LoanApplicationService {

    @Value("${loan.extension.days}")
    protected int defaultLoanExtensionDays;
//    protected BigDecimal defaultLoanInterest; //todo apply interest to initial amount?
    @Value("${loan.extension.interest}")
    protected BigDecimal defaultExtensionInterest;


    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private ApplicationRiskAnalyzer riskAnalyzer;

    @Transactional
    public Loan extendLoan(Long loanId, String userAccount, String ipAddress) {
        Loan previousLoan = loanService.findLoan(loanId);
        BigDecimal newAmount = previousLoan.getAmount().multiply(defaultExtensionInterest);
        LocalDate newTerm = previousLoan.getTerm().plusDays(defaultLoanExtensionDays).toLocalDate();

        Loan newLoan = applyLoan(newAmount, newTerm, userAccount, ipAddress);
        loanService.deactivateByExtension(previousLoan, newLoan);
        return newLoan;
    }

    //todo extract class with user info
    public Loan applyLoan(BigDecimal amount, LocalDate term, String userAccount, String ipAddress) {
        LocalDateTime termDatTime = LocalDateTime.of(term, LocalTime.MAX);

        LoanApplication application = new LoanApplication(amount, termDatTime, LocalDateTime.now(), userAccount, ipAddress);

        AnalysisResult analysisResult = riskAnalyzer.isAcceptable(application);
        if (analysisResult.isValid()) {
            Loan loan = loanService.registerNewLoan(amount, termDatTime, userAccount);
            application.setLoan(loan);
            loanApplicationRepository.save(application);
            return loan;
        } else {
            loanApplicationRepository.save(application);
            throw new RiskAnalysisFailException(analysisResult.getErrorCode());
        }
    }

    public List<LoanApplication> findUserApplication(String accountName, LocalDateTime from, LocalDateTime till) {
        return loanApplicationRepository.findByIpAddressAndPeriod(accountName, from, till);
    }

}
