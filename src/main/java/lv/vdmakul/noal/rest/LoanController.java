package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.transfer.LoanTO;
import lv.vdmakul.noal.service.LoanRepository;
import lv.vdmakul.noal.service.application.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
}
