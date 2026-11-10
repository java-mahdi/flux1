package com.kh.userservice.controller;

import com.kh.userservice.model.AuthorityModel;
import com.kh.userservice.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authority")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping("/findAll")
    public Flux<AuthorityModel> findAll(){
        return authorityService.findAll();
    }
    @GetMapping("/findByName/{name}")
        public Mono<AuthorityModel> findByName( @PathVariable String name){
        return authorityService.findByName(name);
        }


}
