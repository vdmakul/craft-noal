package lv.vdmakul.noal.domain.transfer;

import java.io.Serializable;

public class UserTO implements Serializable {

    private static final long serialVersionUID = -6069641130987853829L;

    private final String login;
    private final String name;

    public UserTO(String login, String name) {
        this.login = login;
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }
}
