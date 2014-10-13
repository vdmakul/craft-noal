package lv.vdmakul.noal;

import lv.vdmakul.noal.config.CoreConfig;
import lv.vdmakul.noal.config.PersistenceConfig;
import lv.vdmakul.noal.config.security.AuthConfig;
import lv.vdmakul.noal.config.security.SecurityConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class WebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(
                CoreConfig.class,
                PersistenceConfig.class,
                SecurityConfig.class,
                AuthConfig.class);
    }

}