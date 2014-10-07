package lv.vdmakul.noal.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"lv.vdmakul.noal.rest", "lv.vdmakul.noal.service"})
@PropertySource("classpath:application.properties")
public class WebApp {
}
