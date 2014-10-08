package lv.vdmakul.noal.service.application.analyser;

public class AnalysisResult {

    private boolean valid;
    private String errorCode;
    private Object[] errorArgs;

    private AnalysisResult(boolean valid, String errorCode) {
        this.valid = valid;
        this.errorCode = errorCode;
    }

    public static AnalysisResult valid() {
        return new AnalysisResult(true, null);
    }

    public static AnalysisResult invalid(String validationErrorCode) {
        return new AnalysisResult(false, validationErrorCode);
    }

    public static AnalysisResult invalid(String validationErrorCode, Object ... messageArgs) {
        AnalysisResult result = new AnalysisResult(false, validationErrorCode);
        result.errorArgs = messageArgs;
        return result;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getErrorArgs() {
        return errorArgs;
    }
}
