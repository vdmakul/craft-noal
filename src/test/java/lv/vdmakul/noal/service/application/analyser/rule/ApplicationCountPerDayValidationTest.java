package lv.vdmakul.noal.service.application.analyser.rule;

import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.service.application.LoanApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApplicationCountPerDayValidationTest {

    private ApplicationCountPerDayValidation validation;

    private LoanApplicationService loanApplicationServiceMock;

    @Before
    public void setUp() throws Exception {
        validation = new ApplicationCountPerDayValidation();
        loanApplicationServiceMock = Mockito.mock(LoanApplicationService.class);
        validation.loanApplicationService = loanApplicationServiceMock;
        validation.maxApplicationCount = 3;
        validation.maxApplicationCountAccountingHours = 24;
    }

    @Test
    public void shouldAllowIfApplicationCountNotExceeded() throws Exception {
        LocalDateTime applicationTime = LocalDateTime.now();

        Mockito.doReturn(Arrays.asList(
                loanFrom("ip", applicationTime.minusHours(1)),
                loanFrom("ip", applicationTime.minusHours(2)),
                loanFrom("ip", applicationTime.minusHours(3))))
                .when(loanApplicationServiceMock).findUserApplication("user", applicationTime.minusHours(24), applicationTime);

        assertTrue(validation.analyze(loanFrom("ip", applicationTime)).isValid());
    }

    @Test
    public void shouldAllowIfApplicationCountFromSingleIpNotExceeded() throws Exception {
        LocalDateTime applicationTime = LocalDateTime.now();

        Mockito.doReturn(Arrays.asList(
                loanFrom("ip", applicationTime.minusHours(1)),
                loanFrom("ip", applicationTime.minusHours(2)),
                loanFrom("ip", applicationTime.minusHours(3)),
                loanFrom("another ip", applicationTime.minusHours(4))))
                .when(loanApplicationServiceMock).findUserApplication("user", applicationTime.minusHours(24), applicationTime);

        assertTrue(validation.analyze(loanFrom("ip", applicationTime)).isValid());
    }

    @Test
    public void shouldRestrictIfApplicationCountFromSingleIpExceeded() throws Exception {
        LocalDateTime applicationTime = LocalDateTime.now();

        Mockito.doReturn(Arrays.asList(
                loanFrom("ip", applicationTime.minusHours(1)),
                loanFrom("ip", applicationTime.minusHours(2)),
                loanFrom("ip", applicationTime.minusHours(3)),
                loanFrom("ip", applicationTime.minusHours(4)),
                loanFrom("another ip", applicationTime.minusHours(5))))
                .when(loanApplicationServiceMock).findUserApplication("user", applicationTime.minusHours(24), applicationTime);

        assertFalse(validation.analyze(loanFrom("ip", applicationTime)).isValid());
    }

    private LoanApplication loanFrom(String ip, LocalDateTime applicationTime) {
        return new LoanApplication(new BigDecimal(100), LocalDateTime.now(), applicationTime, "user", ip);
    }
}