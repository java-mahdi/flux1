package com.kh.web.controller;

import com.kh.web.dto.UserPageableDto;
import com.kh.web.helper.Helper;
import com.kh.web.model.UserModel;
import com.kh.web.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class IndexController {

    @NonNull
    private UserService userService;

    private static Map emptyMap =  Collections.emptyMap();

    private Rendering getFirstIndexageRendering(Map<String, Object>... models){
        UserPageableDto pageableDto = new UserPageableDto(0,2,"username", true) ;
        return Rendering
                .view("index")
                .modelAttribute("page", userService.getPage(pageableDto))
                .model(models.length != 0 ? models[0] : emptyMap)
                .build();
    }

    @GetMapping({"/", "/index"})
    public Mono<Rendering> index(){
        return Mono.just( getFirstIndexageRendering() );
    }

  /*  @GetMapping("/findAll")
    public  Mono<Rendering> findAll(){
        return userService.findAll()
                .flatMap()
    }*/
    @GetMapping("/getPage")
    public  Mono<Rendering> getPage(@ModelAttribute UserPageableDto dto){
        Rendering rendering = Rendering.view("index")
                .modelAttribute("page", userService.
                        getPage(dto) )
                .build();
        return Mono.just(rendering);
    }

    @PostMapping("/save")
    public Mono<Rendering> saveUser(@Valid @ModelAttribute UserModel user){
    return   userService.save(user)
               .flatMap( result -> {
            String saved =  "save failed!";
                       Rendering rendering = getFirstIndexageRendering(Map.of("status", saved));
                       return Mono.just(rendering);
               } ) ;
    }
    @GetMapping("/getUserByUsername/{username}")
    public  Mono<Rendering> getUserByUsername(@PathVariable String username){

        return userService.findByUsername(username)
                .flatMap ( user ->  Mono.just( Rendering.view("index")
                        .modelAttribute("user", user)
                        .modelAttribute("page", getFirstIndexageRendering())
                        .build()
                ) );
    }
    @GetMapping("/delete/{username}")
    public  Mono<Rendering> delete(@PathVariable String username){
        return userService.findByUsername(username)
                .then(userService.delete(username))
                .flatMap( deleted ->  Mono.just( Rendering.view("index")
                        .modelAttribute("deleted", deleted)
                        .modelAttribute("page", getFirstIndexageRendering())
                        .build()
                )  );
                //.onErrorReturn(false);
    }

}
