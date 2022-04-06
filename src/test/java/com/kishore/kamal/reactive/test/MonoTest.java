package com.kishore.kamal.reactive.test;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class MonoTest {

    @Test
    public void test(){
        log.info("Project is set up");
    }
    @Test
    public void monoSubscriber(){
        log.info("Execution Starts");
        String name = "Will";
        Mono<String> mono = Mono.just(name).log();
        mono.subscribe();

        log.info("---------");

        StepVerifier.create(mono).expectNext(name).verifyComplete();



        log.info("Execution Ends");
    }
}
