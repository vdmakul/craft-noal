package lv.vdmakul.noal.service.application.analyser;

import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.analyser.rule.ValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ApplicationRiskAnalyzer {

    @Autowired
    private List<ValidationRule> validationRules;

    @Autowired
    private MessageSource messageSource;

    public AnalysisResult isAcceptable(LoanApplication application) {
        for (ValidationRule rule : validationRules) {
            AnalysisResult result = rule.analyze(application);
            if (!result.isValid()) {
                return result;
            }
        }
        return AnalysisResult.valid();
    }

    public String getErrorMessage(AnalysisResult result) {
        return messageSource.getMessage(result.getErrorCode(), result.getErrorArgs(), Locale.getDefault());
    }

}
