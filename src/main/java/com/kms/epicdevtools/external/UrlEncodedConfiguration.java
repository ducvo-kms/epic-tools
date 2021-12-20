package com.kms.epicdevtools.external;

import feign.Logger;
import feign.Logger.Level;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class UrlEncodedConfiguration {
  @Bean
  Encoder feignFormEncoder() {
     return new SpringFormEncoder();
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Level.FULL;
  }
}
