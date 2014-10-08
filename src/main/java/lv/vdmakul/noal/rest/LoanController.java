package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.transfer.LoanTO;
import lv.vdmakul.noal.service.application.LoanApplicationService;
import lv.vdmakul.noal.service.application.analyser.RiskAnalysisFailException;
import lv.vdmakul.noal.service.loan.LoanNotFoundException;
import lv.vdmakul.noal.service.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoanController {

    @Autowired
    private LoanApplicationService loanApplicationService;
    @Autowired
    private LoanService loanService;

    @RequestMapping(value = "/loan/{id}", method = RequestMethod.GET)
    public LoanTO findLoan(@PathVariable("id") Long loanId, Authentication authentication) {
        Loan loan = loanService.findLoan(loanId, authentication.getName());
        return loan.toTransferObject();
    }

    @RequestMapping(value = "/loan/apply", method = RequestMethod.POST)
    public LoanTO createLoan(@RequestParam("amount") BigDecimal amount,
                             @RequestParam("term") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate term,
                             Authentication authentication) {

        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        Loan loan = loanApplicationService.applyLoan(amount, term, authentication.getName(), details.getRemoteAddress());
        return loan.toTransferObject();
    }

    @RequestMapping(value = "/loan/{id}/extend", method = RequestMethod.POST)
    public LoanTO extendLoan(@PathVariable("id") Long loanId, Authentication authentication) {
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        Loan newLoan = loanApplicationService.extendLoan(loanId, authentication.getName(), details.getRemoteAddress());
        return newLoan.toTransferObject();
    }

    @RequestMapping(value = "/loans", method = RequestMethod.GET)
    public List<LoanTO> findAll(Authentication authentication) {
        return loanService.findAllLoans(authentication.getName()).stream()
                .map(Loan::toTransferObject)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(RiskAnalysisFailException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ErrorInfo handleRiskAnalysisFailException(HttpServletRequest req, RiskAnalysisFailException ex) {
        return new ErrorInfo(ex.getMessage(), ex.getErrorCode());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ErrorInfo handleLoanNotFoundException(HttpServletRequest req, RiskAnalysisFailException ex) {
        return new ErrorInfo(ex.getMessage(), ex.getErrorCode());
    }

}
