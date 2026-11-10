package com.kh.userservice.service;

import com.kh.userservice.model.AuthorityModel;
import com.kh.userservice.repository.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    /*public Mono<AuthorityModel> save(AuthorityModel authorityModel){
        return authorityRepository.save(authorityModel);
    }*/

    public Mono<AuthorityModel> findByName(String name){
        return authorityRepository.findByName(name);
    }
    public Flux<AuthorityModel> findAll(){
        return authorityRepository.findAll();
    }
public Mono<AuthorityModel> save( AuthorityModel authority){
        return authorityRepository.save(authority);
}
}
