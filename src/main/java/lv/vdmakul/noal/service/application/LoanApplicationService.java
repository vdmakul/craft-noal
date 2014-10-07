package lv.vdmakul.noal.service.application;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.domain.repository.LoanApplicationRepository;
import lv.vdmakul.noal.domain.repository.LoanRepository;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import lv.vdmakul.noal.service.application.analyser.ApplicationRiskAnalyzer;
import lv.vdmakul.noal.service.application.analyser.RiskAnalysisFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    @Autowired
    private ApplicationRiskAnalyzer riskAnalyzer;

    public Loan applyLoan(BigDecimal amount, LocalDate term, String userLogin) {
        LocalDateTime termDatTime = LocalDateTime.of(term, LocalTime.MAX);

        LoanApplication application = new LoanApplication(amount, termDatTime, userLogin);

        AnalysisResult analysisResult = riskAnalyzer.isAcceptable(application);
        if (analysisResult.isValid()) {
            Loan loan = new Loan(amount, termDatTime, userLogin);
            loanRepository.save(loan);
            application.setLoan(loan);
            loanApplicationRepository.save(application);
            return loan;
        } else {
            loanApplicationRepository.save(application);
            throw new RiskAnalysisFailException(analysisResult.getErrorCode());
        }
    }

}
