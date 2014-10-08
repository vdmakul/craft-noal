package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.User;
import lv.vdmakul.noal.domain.transfer.UserTO;
import lv.vdmakul.noal.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public UserTO findUser(Authentication authentication) {
        User user = userService.find(authentication.getName());
        return user.toTransferObject();
    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public UserTO createUser(@RequestParam("userAccount") String accountName,
                             @RequestParam("name") String name,
                             @RequestParam("password") String password) {
        User user = userService.createUser(accountName, name, password);
        return user.toTransferObject();
    }

}
