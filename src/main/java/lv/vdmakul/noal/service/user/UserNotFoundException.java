package lv.vdmakul.noal.service.user;

public class UserNotFoundException extends RuntimeException {

    private final String account;

    public UserNotFoundException(String message, String account) {
        super(message);
        this.account = account;
    }
}
