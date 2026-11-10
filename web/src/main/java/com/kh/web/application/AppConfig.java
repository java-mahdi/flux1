package com.kh.web.application;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
  public WebClient user_webClient(@LoadBalanced WebClient.Builder builder ){
    return builder
            .baseUrl("http://" + Consts.user_service_name)
            .build();
    }

}
