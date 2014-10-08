package lv.vdmakul.noal.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"lv.vdmakul.noal.rest", "lv.vdmakul.noal.service"})
@PropertySource("classpath:application.properties")
@Order(1)
public class WebApp {
}
