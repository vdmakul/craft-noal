package lv.vdmakul.noal.service.application.analyser;

public class RiskAnalysisFailException extends RuntimeException {

    private final String errorCode;

    public RiskAnalysisFailException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
