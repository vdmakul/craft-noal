package lv.vdmakul.noal.service.application.analyser.rule;


import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(1)
public class MaxAmountValidation implements ValidationRule {

    public static final String ERROR_CODE = "loan.application.max.amount";

    @Value("${loan.analysis.application.max.amount}")
    protected BigDecimal maxAmount;

    @Override
    public AnalysisResult analyze(LoanApplication application) {
        if (maxAmount.compareTo(application.getAmount()) < 0) {
            return AnalysisResult.invalid(ERROR_CODE, maxAmount);
        } else {
            return AnalysisResult.valid();
        }
    }
}
