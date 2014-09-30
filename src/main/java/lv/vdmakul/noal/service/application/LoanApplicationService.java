package lv.vdmakul.noal.service.application;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.LoanRepository;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import lv.vdmakul.noal.service.application.analyser.ApplicationRiskAnalyzer;
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
    private ApplicationRiskAnalyzer riskAnalyzer;

    public Loan applyLoan(BigDecimal amount, LocalDate term) {
        LocalDateTime termDatTime = LocalDateTime.of(term, LocalTime.MAX);

        LoanApplication application = new LoanApplication(amount, termDatTime);
        AnalysisResult analysisResult = riskAnalyzer.isAcceptable(application);
        if (analysisResult.isValid()) {
            Loan loan = new Loan(amount, termDatTime);
            loanRepository.save(loan);
            return loan;
        } else {
            throw new RuntimeException(analysisResult.getErrorCode());
        }
    }

}
