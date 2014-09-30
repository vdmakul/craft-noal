package lv.vdmakul.noal.domain.transfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class LoanTO implements Serializable {

    private static final long serialVersionUID = 6796045029552402957L;

    private final BigDecimal amount;
    private final String term;

    public LoanTO(BigDecimal amount, String term) {
        this.amount = amount;
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTerm() {
        return term;
    }
}
