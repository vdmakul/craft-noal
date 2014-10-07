package lv.vdmakul.noal.service.user;

import lv.vdmakul.noal.domain.User;
import lv.vdmakul.noal.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User find(String account) {
        User user = userRepository.findOne(account);
        if (user == null) {
            throw new UserNotFoundException(account, "User '" + account + "' not found");
        }
        return user;
    }

    public User createUser(String account, String name, String password) {
        if (userRepository.findOne(account) != null) {
            throw new UserCreationException("User '" + account + " already exists");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPass = encoder.encode(password);
        User user = new User(account, name, encodedPass, "USER");
        userRepository.save(user);
        return user;
    }

}
