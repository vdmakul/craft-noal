package lv.vdmakul.noal.rest;

import com.jayway.jsonpath.JsonPath;
import lv.vdmakul.noal.config.CoreConfig;
import lv.vdmakul.noal.config.PersistenceConfig;
import lv.vdmakul.noal.domain.repository.LoanApplicationRepository;
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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class, PersistenceConfig.class})
@WebAppConfiguration
public class LoanApplicationControllerTest extends SecurityEnabledControllerTest {

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
    public void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(
                get("/applications")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFindApplicationAfterAcceptedLoanApplication() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=12.34&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ResultActions applicationResponse = mockMvc.perform(
                get("/applications")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].amount").value(12.34))
                .andExpect(jsonPath("[0].term").value("2014-01-01"))
                .andExpect(jsonPath("[0].loanId", notNullValue()));

        Integer loanId = JsonPath.read(applicationResponse.andReturn().getResponse().getContentAsString(), "[0].loanId");
        mockMvc.perform(
                get("/loan/" + loanId)
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(12.34))
                .andExpect(jsonPath("term").value("2014-01-01"));
    }

    @Test
    public void shouldFindApplicationAfterRejectedLoanApplication() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=1000000000.01&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/applications")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("[0].amount").value(1000000000.01))
                .andExpect(jsonPath("[0].term").value("2014-01-01"))
                .andExpect(jsonPath("[0].loanId", nullValue()));
    }

    @Test
    public void shouldFindApplicationAfterLoanExtension() throws Exception {
        ResultActions applyResponse = mockMvc.perform(
                post("/loan/apply?amount=50.00&term=2014-01-01")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Integer loanId = JsonPath.read(applyResponse.andReturn().getResponse().getContentAsString(), "id");

        mockMvc.perform(
                post("/loan/" + loanId + "/extend")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/applications")
                        .principal(testPrincipal)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].amount").value(50.00))
                .andExpect(jsonPath("[0].term").value("2014-01-01"))
                .andExpect(jsonPath("[0].loanId").value(loanId))

                .andExpect(jsonPath("[1].amount").value(75.00))
                .andExpect(jsonPath("[1].term").value("2014-01-15"))
                .andExpect(jsonPath("[1].loanId").value(notNullValue()))
                .andExpect(jsonPath("[1].loanId").value(not(loanId)));
    }
}