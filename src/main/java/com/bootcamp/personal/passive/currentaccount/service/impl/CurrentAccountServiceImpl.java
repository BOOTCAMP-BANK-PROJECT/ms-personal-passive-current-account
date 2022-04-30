package com.bootcamp.personal.passive.currentaccount.service.impl;

import com.bootcamp.personal.passive.currentaccount.entity.CurrentAccount;
import com.bootcamp.personal.passive.currentaccount.repository.CurrentAccountRepository;
import com.bootcamp.personal.passive.currentaccount.service.CurrentAccountService;
import com.bootcamp.personal.passive.currentaccount.util.Constant;
import com.bootcamp.personal.passive.currentaccount.util.handler.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CurrentAccountServiceImpl implements CurrentAccountService {

    public final CurrentAccountRepository repository;

    @Override
    public Flux<CurrentAccount> getAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CurrentAccount> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<CurrentAccount> save(CurrentAccount currentAccount) {
        return repository.findByIdClient(currentAccount.getIdClient())
                .map(sa -> {
                    throw new BadRequestException(
                            "ID",
                            "Client have one ore more accounts",
                            sa.getId(),
                            CurrentAccountServiceImpl.class,
                            "save.onErrorResume"
                    );
                })
                .switchIfEmpty(repository.save(currentAccount))
                .onErrorResume(e -> Mono.error(e)).cast(CurrentAccount.class);
    }

    @Override
    public Mono<CurrentAccount> update(CurrentAccount currentAccount) {

        return repository.findById(currentAccount.getId())
                .switchIfEmpty(Mono.error(new Exception("An item with the id " + currentAccount.getId() + " was not found. >> switchIfEmpty")))
                .flatMap(p -> repository.save(currentAccount))
                .onErrorResume(e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to update an item.",
                        e.getMessage(),
                        CurrentAccountServiceImpl.class,
                        "update.onErrorResume"
                )));
    }

    @Override
    public Mono<CurrentAccount> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("An item with the id " + id + " was not found. >> switchIfEmpty")))
                .flatMap(p -> {
                    p.setRegistrationStatus(Constant.STATUS_INACTIVE);
                    return repository.save(p);
                })
                .onErrorResume(e -> Mono.error(new BadRequestException(
                        "ID",
                        "An error occurred while trying to delete an item.",
                        e.getMessage(),
                        CurrentAccountServiceImpl.class,
                        "update.onErrorResume"
                )));
    }
}