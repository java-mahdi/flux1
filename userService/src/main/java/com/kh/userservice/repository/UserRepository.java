package com.kh.userservice.repository;

import com.kh.userservice.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserModel, Integer> {

    public Mono<Page<UserModel>> findAll(Pageable pageable);

    Mono<UserModel> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);
    Mono<Void> deleteByUsername(String username);
}
