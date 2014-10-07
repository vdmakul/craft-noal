package lv.vdmakul.noal.service.application.analyser.rule;

import lv.vdmakul.noal.domain.LoanApplication;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LateApplicationRestrictionTest {

    @Test
    public void shouldRestrictApplicationAt_0_forRestriction_0_8() throws Exception {
        assertFalse(restriction(0, 8).analyze(applicationAt(0)).isValid());
    }

    @Test
    public void shouldRestrictApplicationAt_1_forRestriction_0_8() throws Exception {
        assertFalse(restriction(0, 8).analyze(applicationAt(1)).isValid());
    }

    @Test
    public void shouldRestrictApplicationAt_23_forRestriction_22_8() throws Exception {
        assertFalse(restriction(22, 8).analyze(applicationAt(23)).isValid());
    }

    @Test
    public void shouldRestrictApplicationAt_5_forRestriction_22_8() throws Exception {
        assertFalse(restriction(22, 8).analyze(applicationAt(5)).isValid());
    }

    @Test
    public void shouldAllowApplicationAt_21_forRestriction_22_8() throws Exception {
        assertTrue(restriction(22, 8).analyze(applicationAt(21)).isValid());
    }

    @Test
    public void shouldAllowApplicationAt_9_forRestriction_22_8() throws Exception {
        assertTrue(restriction(22, 8).analyze(applicationAt(9)).isValid());
    }

    @Test
    public void shouldAllowApplicationAt_0_forRestriction_1_8() throws Exception {
        assertTrue(restriction(1, 8).analyze(applicationAt(0)).isValid());
    }

    @Test
    public void shouldAllowApplicationAt_9_forRestriction_1_8() throws Exception {
        assertTrue(restriction(1, 8).analyze(applicationAt(9)).isValid());
    }

    @Test
    public void shouldAllowAtAnyTimeIfAmountExceedMaxAmount() throws Exception {
        assertTrue(restriction(0, 8).analyze(applicationAt(4, new BigDecimal(50))).isValid());
    }

    private LoanApplication applicationAt(int hour) {
        return applicationAt(hour, new BigDecimal(100));
    }

    private LoanApplication applicationAt(int hour, BigDecimal amount) {
        return new LoanApplication(amount, LocalDateTime.now(), LocalDateTime.of(2014, 1, 1, hour, 0), "", "");
    }

    private LateApplicationRestriction restriction(int from, int till) {
        LateApplicationRestriction restriction = new LateApplicationRestriction();
        restriction.from = from;
        restriction.till = till;
        restriction.maxAmount = new BigDecimal(100);
        return restriction;
    }

}