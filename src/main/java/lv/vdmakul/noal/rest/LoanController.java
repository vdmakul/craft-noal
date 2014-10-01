package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.transfer.LoanTO;
import lv.vdmakul.noal.service.LoanRepository;
import lv.vdmakul.noal.service.application.LoanApplicationService;
import lv.vdmakul.noal.service.application.analyser.RiskAnalysisFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoanController {

    @Autowired private LoanRepository loanRepository;
    @Autowired private LoanApplicationService loanApplicationService;

    @RequestMapping(value = "/loan/apply", method = RequestMethod.POST)
    public LoanTO createLoan(@RequestParam("amount") BigDecimal amount,
                             @RequestParam("term") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate term) {

        Loan loan = loanApplicationService.applyLoan(amount, term);
        return loan.toTransferObject();
    }

    @RequestMapping(value = "/loans", method = RequestMethod.GET)
    public List<LoanTO> findAll() {
        return loanRepository.findAll().stream()
                .map(Loan::toTransferObject)
                .collect(Collectors.toList());
    }


    @ExceptionHandler(RiskAnalysisFailException.class)
    @ResponseStatus(value= HttpStatus.OK)
    @ResponseBody
    public ErrorInfo handleRiskAnalysisFailException(HttpServletRequest req, RiskAnalysisFailException ex) {
        return new ErrorInfo(ex.getMessage(), ex.getErrorCode());
    }

}
