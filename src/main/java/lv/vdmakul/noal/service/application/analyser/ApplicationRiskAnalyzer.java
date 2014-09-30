package lv.vdmakul.noal.service.application.analyser;

import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.analyser.rule.ValidationRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationRiskAnalyzer {

    @Autowired
    private List<ValidationRule> validationRules; //fixme: order undefined

    public AnalysisResult isAcceptable(LoanApplication application) {
        for (ValidationRule rule : validationRules) {
            AnalysisResult result = rule.isValid(application);
            if (!result.isValid()) {
                return result;
            }
        }
        return AnalysisResult.valid();
    }

}
