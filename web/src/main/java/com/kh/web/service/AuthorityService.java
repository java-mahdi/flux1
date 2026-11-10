package com.kh.web.service;

import com.kh.web.application.CustomException;
import com.kh.web.model.AuthorityModel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class AuthorityService {


    private WebClient webClient;

    public AuthorityService(@Qualifier("user_webClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<AuthorityModel> findByName(String name){
    return webClient
            .get()
            .uri("/authority/findByName/{name}", name)
            .retrieve()
            .bodyToMono(AuthorityModel.class)
            .onErrorMap(WebClientResponseException.class , ex -> new CustomException(ex.getStatusCode().value(), ex.getResponseBodyAsString()) );
    }

    public Flux<AuthorityModel> findAll(){
        return webClient
                .get()
                .uri("/authority/findAll")
                .retrieve()

                .bodyToFlux(AuthorityModel.class)
                .onErrorMap(WebClientResponseException.class, ex ->  new CustomException(ex.getStatusCode().value(), ex.getResponseBodyAsString()) );

    }
}
