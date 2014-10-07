package lv.vdmakul.noal.service.application.analyser.rule;


import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;

public interface ValidationRule  {

    AnalysisResult analyze(LoanApplication application);

}
