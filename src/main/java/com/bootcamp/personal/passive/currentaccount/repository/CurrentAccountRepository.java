package com.bootcamp.personal.passive.currentaccount.repository;

import com.bootcamp.personal.passive.currentaccount.entity.CurrentAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CurrentAccountRepository extends ReactiveMongoRepository<CurrentAccount, String> {

    Mono<CurrentAccount> findByIdClient(String idClient);
}