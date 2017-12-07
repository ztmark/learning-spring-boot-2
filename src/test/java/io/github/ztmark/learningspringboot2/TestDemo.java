package io.github.ztmark.learningspringboot2;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Author: Mark
 * Date  : 2017/12/5
 */
public class TestDemo {

    @Test
    public void test() throws InterruptedException {
//        Flux.just("what", "are", "you", "doing").log().map(String::toUpperCase).subscribe();
        Flux.just("what", "are", "you", "doing").log().map(String::toUpperCase).subscribeOn(Schedulers.parallel()).subscribe(new Subscriber<String>() {

            private int count;
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                subscription.request(2);
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
                count++;
                if (count >= 2) {
                    count = 0;
                    subscription.request(2);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        });
        TimeUnit.SECONDS.sleep(1);

    }

    @Test
    public void testWebClient() {
        final WebClient client = WebClient.builder().build();
        final WebClient.RequestHeadersUriSpec<?> uriSpec = client.get();
        final WebClient.RequestHeadersSpec<?> uri = uriSpec.uri(URI.create("http://v2ex.com"));
        final WebClient.ResponseSpec retrieve = uri.retrieve();
        final Mono<String> stringMono = retrieve.bodyToMono(String.class);
        final String block = stringMono.block(Duration.ofSeconds(5));
        System.out.println(block);

    }

}
