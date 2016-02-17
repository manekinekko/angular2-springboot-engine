/**
 * Created by wchegham on 07/02/16.
 */
package io.angular.universal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class UniversalApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UniversalApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UniversalApplication.class, args);
    }
}
