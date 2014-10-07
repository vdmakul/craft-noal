package lv.vdmakul.noal.domain;

import lv.vdmakul.noal.domain.transfer.UserTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    protected String login;
    protected String name;
    protected String role;
    protected String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userLogin")
    protected List<LoanApplication> applications = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userLogin")
    protected List<Loan> loans = new ArrayList<>();

    protected User() {
    }

    public User(String login, String name, String password, String role) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public UserTO toTransferObject() {
        return new UserTO(login, name);
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

}
