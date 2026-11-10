package com.kh.userservice.controller;

import com.kh.userservice.dto.UserPageableDto;
import com.kh.userservice.model.UserModel;
import com.kh.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/findAll")
    public Flux<UserModel> findAll(){
        return userService.findAll();
    }

    @GetMapping("/getPage")
    public Mono<Page<UserModel>> getPage(@Valid @ModelAttribute UserPageableDto pageableDto){
        Sort.Direction dir = pageableDto.getAsc() ? Sort.Direction.ASC :Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageableDto.getNumber(), pageableDto.getSize(), dir, pageableDto.getSortBy() );
        return userService.findAll(pageable);
    }
    @GetMapping("/findByUsername/{username}")
        public Mono<UserModel> findByUsername( @PathVariable String username){
        return userService.findByUsername(username);
        }
     @PostMapping("/save")
     public Mono<UserModel> save(@RequestBody UserModel user){
    return userService.save(user);
     }

     @DeleteMapping("/deleteByUsername/{username}")
    public  Mono<Boolean> deleteByUsername(@PathVariable String username){
       return  userService.existsByUsername(username)
                 .flatMap(exists -> {
                     if(exists)
                         userService.deleteByUsername(username)
                                 .then( userService.existsByUsername(username))
                                 .map(exist -> ! exist);


                     //response.setStatusCode(HttpStatus.BAD_REQUEST);
                     return Mono.error(new EntityNotFoundException("user does not exists!"));
                 });

     }
}
