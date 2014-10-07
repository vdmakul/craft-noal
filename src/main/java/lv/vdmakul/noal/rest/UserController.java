package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.User;
import lv.vdmakul.noal.domain.transfer.UserTO;
import lv.vdmakul.noal.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired private UserService userService;

    @RequestMapping(value = "/user/{userAccount}", method = RequestMethod.GET)
    public UserTO findUser(@PathVariable("userAccount") String accountName) {
        User user = userService.find(accountName);
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
