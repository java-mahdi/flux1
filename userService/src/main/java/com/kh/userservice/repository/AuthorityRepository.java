package com.kh.userservice.repository;

import com.kh.userservice.model.AuthorityModel;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AuthorityRepository extends ReactiveCrudRepository<AuthorityModel, Integer> {
    Mono<AuthorityModel> findByName(String name);
}
