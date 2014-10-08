package lv.vdmakul.noal.service.application.analyser.rule;

import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Order(2)
public class LateApplicationRestriction implements ValidationRule {

    public static final String ERROR_CODE = "loan.application.max.amount.after.midnight";

    @Value("${loan.analysis.application.max.amount}")
    protected BigDecimal maxAmount;
    @Value("${loan.analysis.application.restricted.time.from}")
    protected int from;
    @Value("${loan.analysis.application.restricted.time.till}")
    protected int till;

    @Override
    public AnalysisResult analyze(LoanApplication application) {
        if (maxAmount.compareTo(application.getAmount()) > 0) {
            return AnalysisResult.valid();
        }

        LocalDateTime applicationTime = application.getApplicationTime();
        int hour = applicationTime.getHour() + 1;

        // restriction 23-8
        if (from > till) {
            if (hour <= from && hour > till) {
                return AnalysisResult.valid();
            }
        }
        //restriction 0-8
        if (till > from) {
            if (hour <= from || hour > till) {
                return AnalysisResult.valid();
            }
        }
        return AnalysisResult.invalid(ERROR_CODE);
    }
}

