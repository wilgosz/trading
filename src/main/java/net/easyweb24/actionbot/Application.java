package net.easyweb24.actionbot;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    //private final Environment env;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    //public Application(Environment env) {
    //  this.env = env;
    //}
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //Environment env = SpringApplication.run(Application.class, args).getEnvironment();
        //logApplicationStartup(env);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t"
                + "Application '{}' is running! Access URLs:\n\t"
                + "Local: \t\t{}://localhost:{}{}\n\t"
                + "External: \t{}://{}:{}{}\n\t"
                + "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }
}
//CREATE DATABASE trading CHARACTER SET utf8 COLLATE utf8_general_ci;
//CREATE DATABASE trading CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;