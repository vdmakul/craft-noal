package lv.vdmakul.noal.domain;

import lv.vdmakul.noal.domain.transfer.LoanTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Loan {

    private final BigDecimal amount;
    private final LocalDateTime term;

    public Loan(BigDecimal amount, LocalDateTime term) {
        this.amount = amount;
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public LoanTO toTransferObject() {
        String termString = term.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//todo extract format
        return new LoanTO(amount, termString);
    }
}
