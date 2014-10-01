package lv.vdmakul.noal;

import lv.vdmakul.noal.config.PersistenceConfig;
import lv.vdmakul.noal.config.WebApp;
import org.springframework.boot.SpringApplication;

public class App {

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{WebApp.class, PersistenceConfig.class}, args);
    }
}
