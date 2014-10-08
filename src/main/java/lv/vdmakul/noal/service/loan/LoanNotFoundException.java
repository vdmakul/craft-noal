package lv.vdmakul.noal.service.loan;

public class LoanNotFoundException extends RuntimeException {

    private final Long loanId;

    public LoanNotFoundException(String message, Long loanId) {
        super(message);
        this.loanId = loanId;
    }

    public Long getLoanId() {
        return loanId;
    }
}
