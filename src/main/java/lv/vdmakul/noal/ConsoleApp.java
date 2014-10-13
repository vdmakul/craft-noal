package lv.vdmakul.noal;

import lv.vdmakul.noal.config.CoreConfig;
import lv.vdmakul.noal.config.PersistenceConfig;
import lv.vdmakul.noal.config.security.AuthConfig;
import lv.vdmakul.noal.config.security.SecurityConfig;
import org.springframework.boot.SpringApplication;

public class ConsoleApp {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{
                        CoreConfig.class,
                        PersistenceConfig.class,
                        SecurityConfig.class,
                        AuthConfig.class},
                args);
    }
}
