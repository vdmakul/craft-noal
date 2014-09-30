package lv.vdmakul.noal.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanApplication {

    private final BigDecimal amount;
    private final LocalDateTime term;

    public LoanApplication(BigDecimal amount, LocalDateTime term) {
        this.amount = amount;
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTerm() {
        return term;
    }
}
