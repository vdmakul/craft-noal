package lv.vdmakul.noal.domain;

import lv.vdmakul.noal.domain.transfer.LoanTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    protected Long id;

    protected BigDecimal amount;
    protected LocalDateTime term;

    protected Loan() {
    }

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
