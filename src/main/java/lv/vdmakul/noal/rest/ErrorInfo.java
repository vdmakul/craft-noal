package lv.vdmakul.noal.rest;

public class ErrorInfo {

    private final String errorMessage;
    private final String errorCode;

    public ErrorInfo(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
