package lv.vdmakul.noal.service.user;

import lv.vdmakul.noal.domain.User;
import lv.vdmakul.noal.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserService {

    public static final String ERROR_CODE_USER_EXISTS = "user.creation.exists";
    public static final String ERROR_CODE_USER_NOT_FOUND = "user.not.found";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;

    public User find(String account) {
        User user = userRepository.findOne(account);
        if (user == null) {
            String message = messageSource.getMessage(ERROR_CODE_USER_NOT_FOUND, new Object[]{account}, Locale.getDefault());
            throw new UserNotFoundException(account, message);
        }
        return user;
    }

    public User createUser(String account, String name, String password) {
        if (userRepository.findOne(account) != null) {
            String message = messageSource.getMessage(ERROR_CODE_USER_EXISTS, new Object[]{account}, Locale.getDefault());
            throw new UserCreationException(message);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPass = encoder.encode(password);
        User user = new User(account, name, encodedPass, "USER");
        userRepository.save(user);
        return user;
    }

}
