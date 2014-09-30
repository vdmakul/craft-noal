package lv.vdmakul.noal.service.application.analyser.rule;


import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import org.springframework.stereotype.Component;

@Component
public class AlwaysTrueValidationRule implements ValidationRule {

    @Override
    public AnalysisResult isValid(LoanApplication application) {
        return AnalysisResult.valid();
    }
}
