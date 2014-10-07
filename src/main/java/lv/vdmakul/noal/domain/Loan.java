package lv.vdmakul.noal.domain;

import lv.vdmakul.noal.domain.transfer.LoanTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
    @OneToOne
    protected LoanApplication loanApplication;
    protected String userLogin;

    protected Loan() {
    }

    public Loan(BigDecimal amount, LocalDateTime term, String userLogin) {
        this.amount = amount;
        this.term = term;
        this.userLogin = userLogin;
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
