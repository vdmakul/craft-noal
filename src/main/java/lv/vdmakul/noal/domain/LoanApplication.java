package lv.vdmakul.noal.domain;

import lv.vdmakul.noal.domain.transfer.LoanApplicationTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class LoanApplication {

    @Id
    @GeneratedValue
    protected Long id;
    protected BigDecimal amount;
    protected LocalDateTime term;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Loan loan;
    protected String userAccount;

    protected LoanApplication() {
    }

    public LoanApplication(BigDecimal amount, LocalDateTime term, String userAccount) {
        this.amount = amount;
        this.term = term;
        this.userAccount = userAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public LoanApplicationTO toTransferObject() {
        String termString = term.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//todo extract format
        Long loanId = loan == null ? null : loan.id;
        return new LoanApplicationTO(amount, termString, loanId);
    }
}
