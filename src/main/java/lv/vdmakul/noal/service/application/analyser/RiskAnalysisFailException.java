package lv.vdmakul.noal.service.application.analyser;

public class RiskAnalysisFailException extends RuntimeException {

    private final String errorCode;

    public RiskAnalysisFailException(String errorCode) {
        super(messageFormErrorCode(errorCode));
        this.errorCode = errorCode;
    }

    private static String messageFormErrorCode(String errorCode) {
        return "Message for error code " + errorCode; //todo implement
    }

    public String getErrorCode() {
        return errorCode;
    }
}
