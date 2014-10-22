package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.config.CoreConfig;
import lv.vdmakul.noal.config.PersistenceConfig;
import lv.vdmakul.noal.domain.repository.LoanApplicationRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class, PersistenceConfig.class})
@WebAppConfiguration
public class LoanApplicationRiskAnalysisControllerTest extends SecurityEnabledControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @After
    public void tearDown() throws Exception {
        loanApplicationRepository.deleteAll();
    }

    @Test
    public void shouldValidateAmountSize() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=123.45&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(403))
                .andExpect(jsonPath("errorCode").value("loan.application.max.amount"));
    }

    @Test
    public void shouldValidateMaxCountForPeriod() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=123.45&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON));
        mockMvc.perform(
                post("/loan/apply?amount=123.45&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON));
        mockMvc.perform(
                post("/loan/apply?amount=123.45&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON));

        mockMvc.perform(
                post("/loan/apply?amount=12.34&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(403))
                .andExpect(jsonPath("errorCode").value("loan.application.max.count.for.period"));
    }

    @Test
    @Ignore //todo need to mock current time for this test
    public void shouldValidateMaxAmountAfterMidnight() throws Exception {

        mockMvc.perform(
                post("/loan/apply?amount=100.00&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode").value("loan.application.max.amount.after.midnight"));
    }
}