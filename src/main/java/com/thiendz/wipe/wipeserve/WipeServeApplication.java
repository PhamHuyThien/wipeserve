package com.thiendz.wipe.wipeserve;

import com.thiendz.wipe.wipeserve.configs.FileUploadConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileUploadConfig.class})
public class WipeServeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WipeServeApplication.class, args);
    }

}
