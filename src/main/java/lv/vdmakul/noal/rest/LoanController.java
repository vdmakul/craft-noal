package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.transfer.LoanTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
public class LoanController {

    @RequestMapping(value = "/loan/create", method = RequestMethod.POST)
    public LoanTO createLoan(@RequestParam("amount") BigDecimal amount,
                             @RequestParam("term") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate term) {
        LocalDateTime termDatTime = LocalDateTime.of(term, LocalTime.MAX);
        return new Loan(amount, termDatTime).toTransferObject();
    }


}
