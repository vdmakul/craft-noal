package lv.vdmakul.noal.domain.repository;

import lv.vdmakul.noal.config.PersistenceConfig;
import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class})
public class LoanApplicationRepositoryTest {

    @Autowired
    LoanApplicationRepository repo;
    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
        userRepository.save(new User("test", "test user", "password", "USER"));
    }

    @Test
    public void shouldSearchByIpAddressAndPeriod() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        repo.save(new LoanApplication(new BigDecimal(100), now, now.minusHours(3), "test", "ip"));
        repo.save(new LoanApplication(new BigDecimal(100), now, now.minusHours(2), "test", "ip"));
        repo.save(new LoanApplication(new BigDecimal(100), now, now.minusHours(1), "test", "ip"));

        List<LoanApplication> applications = repo.findByIpAddressAndPeriod("test", now.minusHours(4), now);
        assertEquals(3, applications.size());

    }
}