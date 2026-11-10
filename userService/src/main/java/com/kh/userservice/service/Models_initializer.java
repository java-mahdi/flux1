package com.kh.userservice.service;

import com.kh.userservice.application.Consts;
import com.kh.userservice.model.AuthorityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Models_initializer implements CommandLineRunner {

    private final AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {
        for(Consts.authority auth: Consts.authority.values()){
            authorityService.save(new AuthorityModel(auth.name()));
        }
    }
}
