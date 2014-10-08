package lv.vdmakul.noal.rest;

import com.jayway.jsonpath.JsonPath;
import lv.vdmakul.noal.config.PersistenceConfig;
import lv.vdmakul.noal.config.WebApp;
import lv.vdmakul.noal.domain.repository.LoanApplicationRepository;
import lv.vdmakul.noal.domain.repository.LoanRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApp.class, PersistenceConfig.class})
@WebAppConfiguration
public class LoanControllerTest extends SecurityEnabledControllerTest {

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private LoanRepository loanRepository;
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
    public void shouldCreateLoan() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=12.34&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(12.34))
                .andExpect(jsonPath("term").value("2014-01-01"));
    }

    @Test
    public void shouldExtendLoan() throws Exception {
        ResultActions applyResponse = mockMvc.perform(
                post("/loan/apply?amount=10.00&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(10.00))
                .andExpect(jsonPath("term").value("2014-01-01"));

        Integer loanId = JsonPath.read(applyResponse.andReturn().getResponse().getContentAsString(), "id");

        mockMvc.perform(
                post("/loan/" + loanId + "/extend")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(15.00))
                .andExpect(jsonPath("term").value("2014-01-15"))
                .andExpect(jsonPath("id").value(not(loanId)));
    }

    @Test
    public void shouldSearchAll() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=12.34&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/loans")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].amount").value(12.34))
                .andExpect(jsonPath("[0].term").value("2014-01-01"));
    }

    @Test
    public void shouldCorrectlyHandleError() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=123.45&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode").value("loan.application.max.amount"));
    }

}