package lv.vdmakul.noal.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"lv.vdmakul.noal.rest", "lv.vdmakul.noal.service"})
public class WebApp {
}
