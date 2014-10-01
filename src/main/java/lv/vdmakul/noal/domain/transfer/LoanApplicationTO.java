package lv.vdmakul.noal.domain.transfer;

import java.math.BigDecimal;

public class LoanApplicationTO {

    private final BigDecimal amount;
    private final String term;
    private final Long loanId;

    public LoanApplicationTO(BigDecimal amount, String term, Long loanId) {
        this.amount = amount;
        this.term = term;
        this.loanId = loanId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTerm() {
        return term;
    }

    public Long getLoanId() {
        return loanId;
    }
}
