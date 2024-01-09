package com.metanet.amatmu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Configuration
public class SmsConfig {
   
   @Value(("${sms.api.key}"))
    private String apiKey;
   
   @Value(("${sms.api.secret}"))
    private String secret;

   @Bean
   DefaultMessageService messageService() {
      return NurigoApp.INSTANCE.initialize(apiKey, secret, "https://api.coolsms.co.kr");
   }
}