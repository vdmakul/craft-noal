package lv.vdmakul.noal.service.application.analyser.rule;


import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component @Deprecated //todo: should not be used, added for test support only
public class BillionValidationRule implements ValidationRule {

    @Override
    public AnalysisResult isValid(LoanApplication application) {
        if (new BigDecimal(1e9).compareTo(application.getAmount()) < 0) {
            return AnalysisResult.invalid("errorCode");
        } else {
            return AnalysisResult.valid();
        }
    }
}
