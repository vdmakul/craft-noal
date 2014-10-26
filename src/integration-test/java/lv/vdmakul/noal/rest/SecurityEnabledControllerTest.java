package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.repository.UserRepository;
import lv.vdmakul.noal.service.user.UserService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public abstract class SecurityEnabledControllerTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    TestingAuthenticationToken testPrincipal;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
        userService.createUser("test", "test user", "testPassword");

        User user = new User("test","testPassword", AuthorityUtils.createAuthorityList("USER"));
        testPrincipal = new TestingAuthenticationToken(user,null);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");
        testPrincipal.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(testPrincipal);
    }
}
