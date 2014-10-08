package lv.vdmakul.noal.domain.transfer;

import java.io.Serializable;
import java.math.BigDecimal;

public class LoanTO implements Serializable {

    private static final long serialVersionUID = 6796045029552402957L;

    private final Long id;
    private final BigDecimal amount;
    private final String term;
    private final Boolean active;

    public LoanTO(Long id, BigDecimal amount, String term, Boolean active) {
        this.id = id;
        this.amount = amount;
        this.term = term;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTerm() {
        return term;
    }

    public Boolean getActive() {
        return active;
    }
}
