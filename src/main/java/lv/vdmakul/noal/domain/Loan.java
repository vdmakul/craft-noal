package lv.vdmakul.noal.domain;

import lv.vdmakul.noal.domain.transfer.LoanTO;

import javax.persistence.*;
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
    protected String userAccount;
    protected Boolean active;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Loan extensionLoan;

    protected Loan() {
    }

    public Loan(BigDecimal amount, LocalDateTime term, String userAccount) {
        this.active = true;
        this.amount = amount;
        this.term = term;
        this.userAccount = userAccount;
    }

    public void deactivate() {
        this.active = false;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public LoanTO toTransferObject() {
        String termString = term.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//todo extract format
        return new LoanTO(id, amount, termString, active);
    }

    public String getUserAccount() {
        return userAccount;
    }

    public Boolean isActive() {
        return active;
    }

    public void setExtensionLoan(Loan extensionLoan) {
        this.extensionLoan = extensionLoan;
    }
}
