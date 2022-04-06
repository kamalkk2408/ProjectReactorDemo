package com.kishore.kamal.reactive.test;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

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

    @Test
    public void monoSubscriberWithConsumer(){
        log.info("Execution Starts");
        String name = "Will";
        Mono<String> mono = Mono.just(name).log();

        log.info("---------");
        mono.subscribe(s -> log.info("value is : {}", s));
        log.info("---------");

        StepVerifier.create(mono).expectNext(name).verifyComplete();



        log.info("Execution Ends");
    }

    @Test
    public void monoSubscriberWithConsumerWithError(){
        log.info("Execution Starts");
        String name = "Will";
        Mono<String> mono = Mono.just(name)
                .map(a -> {throw new RuntimeException("Testing");});

        log.info("---------");
        mono.subscribe(s -> log.info("value is : {}", s), s -> log.error("error occured   ") );
        log.info("---------");

        StepVerifier.create(mono).expectError(RuntimeException.class).verify();



        log.info("Execution Ends");
    }
    @Test
    public void monoSubscriberWithConsumerWithErrorComplete(){
        log.info("Execution Starts");
        String name = "Will";
        Mono<String> mono = Mono.just(name)
                .map(String::toUpperCase);

        log.info("---------");
        mono.subscribe(s -> log.info("value is : {}", s), Throwable::printStackTrace, () -> log.info("finished"));
        log.info("---------");

        StepVerifier.create(mono).expectNext(name.toUpperCase()).verifyComplete();

        log.info("Execution Ends");
    }

    @Test
    public void monoSubscriberWithConsumerWithErrorCompleteDisposable(){
        log.info("Execution Starts");
        String name = "Will";
        Mono<String> mono = Mono.just(name)
                .map(String::toUpperCase).log();

        log.info("---------");
        mono.subscribe(s -> log.info("value is : {}", s),
                Throwable::printStackTrace,
                () -> log.info("finished"),
                Subscription::cancel);

        log.info("---------");
        mono.subscribe(s -> log.info("value is : {}", s),
                Throwable::printStackTrace,
                () -> log.info("finished"),
                subs -> subs.request(2));


        log.info("Execution Ends");
    }
}
