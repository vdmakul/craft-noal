package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.config.WebApp;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebApp.class})
@WebAppConfiguration
public class LoanControllerTest {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreateLoan() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=123.45&term=2014-01-01").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("amount").value(123.45))
                .andExpect(jsonPath("term").value("2014-01-01"));
    }

    @Test
    public void shouldSearchAll() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=123.45&term=2014-01-01").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                get("/loans").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("[0].amount").value(123.45))
                .andExpect(jsonPath("[0].term").value("2014-01-01"));
    }

    @Test
    public void shouldCorrectlyHandleError() throws Exception {
        mockMvc.perform(
                post("/loan/apply?amount=1000000000.01&term=2014-01-01").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("errorCode").value("errorCode"));
    }

}