package lv.vdmakul.noal.service.application.analyser.rule;

import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.LoanApplicationService;
import lv.vdmakul.noal.service.application.analyser.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Order(3)
public class ApplicationCountPerDayValidation implements ValidationRule {

    public static final String ERROR_CODE = "loan.application.max.count.for.period";

    @Autowired
    protected LoanApplicationService loanApplicationService;

    @Value("${loan.analysis.application.restricted.application.count}")
    protected int maxApplicationCount;
    @Value("${loan.analysis.application.restricted.application.count.period.size}")
    protected int maxApplicationCountAccountingHours;

    @Override
    public AnalysisResult analyze(LoanApplication application) {
        LocalDateTime till = application.getApplicationTime();
        LocalDateTime from = till.minusHours(maxApplicationCountAccountingHours);

        List<LoanApplication> applications = loanApplicationService.findUserApplication(application.getUserAccount(), from, till);

        String ipAddress = application.getIpAddress();
        long count = applications.stream().filter(app -> ipAddress.equals(app.getIpAddress())).count();

        if (count >= maxApplicationCount) {
            return AnalysisResult.invalid(ERROR_CODE);
        } else {
            return AnalysisResult.valid();
        }
    }
}
