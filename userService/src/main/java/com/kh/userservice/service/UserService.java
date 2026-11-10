package com.kh.userservice.service;

import com.kh.userservice.model.UserModel;
import com.kh.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public  Flux<UserModel> findAll(){
        return userRepository.findAll();
    }

    public Mono<Page<UserModel>> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }
    public Mono<UserModel> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Mono<UserModel> save (UserModel user){
        return userRepository.save(user);
    }
    public Mono<Void> deleteByUsername(String username){
        return userRepository.deleteByUsername(username);
        // return userRepository.existsByUsername(username);
    }
    public Mono<Boolean> existsByUsername(String username){
       return  userRepository.existsByUsername(username);

    }

}
