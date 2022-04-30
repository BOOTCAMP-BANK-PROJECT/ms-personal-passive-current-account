package com.bootcamp.personal.passive.currentaccount.service;

import com.bootcamp.personal.passive.currentaccount.entity.CurrentAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CurrentAccountService {

    public Flux<CurrentAccount> getAll();

    public Mono<CurrentAccount> getById(String id);

    public Mono<CurrentAccount> save(CurrentAccount currentAccount);

    public Mono<CurrentAccount> update(CurrentAccount currentAccount);

    public Mono<CurrentAccount> delete(String id);
}