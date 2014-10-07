package lv.vdmakul.noal.service.application.analyser.rule;

import lv.vdmakul.noal.domain.LoanApplication;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MaxAmountValidationTest {

    private MaxAmountValidation validation;

    @Before
    public void setUp() throws Exception {
        validation = new MaxAmountValidation();
        validation.maxAmount = new BigDecimal(100);
    }

    @Test
    public void shouldAllowLessThenMax() throws Exception {
        assertTrue(validation.analyze(loan(new BigDecimal(50))).isValid());
    }

    @Test
    public void shouldAllowEqualToMax() throws Exception {
        assertTrue(validation.analyze(loan(new BigDecimal(100))).isValid());
    }

    @Test
    public void shouldRestrictMoreThenMax() throws Exception {
        assertFalse(validation.analyze(loan(new BigDecimal(150))).isValid());
    }

    private LoanApplication loan(BigDecimal amount) {
        return new LoanApplication(amount, LocalDateTime.now(), LocalDateTime.MAX, "", "");
    }
}