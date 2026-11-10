package com.kh.web.service;

import com.kh.web.application.CustomException;
import com.kh.web.dto.UserPageableDto;
import com.kh.web.model.UserModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final WebClient webClient;
    private  WebClient user_webClient;

    public UserService(@Qualifier("user_webClient") WebClient user_webClient, WebClient webClient) {
        this.user_webClient = user_webClient;
        this.webClient = webClient;
    }

    public Mono<UserModel> findByUsername(String username){
        return user_webClient
                .get()
                .uri("/user/findByUsername/{username}", username)
                .retrieve()

                .bodyToMono(UserModel.class)
                .onErrorMap( WebClientResponseException.class  , ex -> new CustomException(ex.getStatusCode().value() , ex.getResponseBodyAsString()) ) ;
    }

    public Flux<UserModel> findAll(){
    return webClient
            .get()
            .uri("/user/findAll")
            .retrieve()
            .bodyToFlux(UserModel.class)
            .onErrorMap(WebClientResponseException.class, ex-> new CustomException(ex.getStatusCode().value(), ex.getResponseBodyAsString()));
    }
    public Mono<Page> getPage(UserPageableDto dto){
    return webClient.get()
            .uri(uri -> uri
                   .path("/user/getPage")
                    .queryParam("number", dto.getNumber())
                            .queryParam("size", dto.getSize())
                            .queryParam("sortBy", dto.getSortBy())
                            .queryParam("asc", dto.getAsc())
                            .build()
                    )
            .retrieve()
            .bodyToMono(Page.class)
            .onErrorMap(WebClientResponseException.class, ex -> new CustomException(ex.getStatusCode().value(),  ex.getResponseBodyAsString()));
    }

    public Mono<UserModel> save(UserModel user){
    return webClient.post()
            .bodyValue(user)
            .retrieve()
            .bodyToMono(UserModel.class)
            .onErrorMap(WebClientResponseException.class, ex -> new CustomException(ex.getStatusCode().value(), ex.getResponseBodyAsString()));
    }

    public Mono<Boolean> delete(String username){
    return webClient.delete()
            .uri("user/deleteByUsername/{username}", username)
            .retrieve()
            .bodyToMono(Boolean.class)
            .onErrorMap(WebClientResponseException.class, ex -> new CustomException(ex.getStatusCode().value(), ex.getResponseBodyAsString()));
    }

}
