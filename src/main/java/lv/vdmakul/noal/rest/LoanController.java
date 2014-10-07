package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.repository.LoanRepository;
import lv.vdmakul.noal.domain.transfer.LoanTO;
import lv.vdmakul.noal.service.application.LoanApplicationService;
import lv.vdmakul.noal.service.application.analyser.RiskAnalysisFailException;
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
import java.util.stream.StreamSupport;

@RestController
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanApplicationService loanApplicationService;

    @RequestMapping(value = "/loan/{id}", method = RequestMethod.GET)
    public LoanTO findLoan(@PathVariable("id") Long loanId) {
        Loan loan = loanRepository.findOne(loanId);
        return loan.toTransferObject(); //todo check for null
    }

    @RequestMapping(value = "/loan/apply", method = RequestMethod.POST)
    public LoanTO createLoan(@RequestParam("amount") BigDecimal amount,
                             @RequestParam("term") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate term,
                             Authentication authentication) {

        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        Loan loan = loanApplicationService.applyLoan(amount, term, authentication.getName(), details.getRemoteAddress());
        return loan.toTransferObject();
    }

    @RequestMapping(value = "/loans", method = RequestMethod.GET)
    public List<LoanTO> findAll() {
        Iterable<Loan> all = loanRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(Loan::toTransferObject)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(RiskAnalysisFailException.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ErrorInfo handleRiskAnalysisFailException(HttpServletRequest req, RiskAnalysisFailException ex) {
        return new ErrorInfo(ex.getMessage(), ex.getErrorCode());
    }

}
