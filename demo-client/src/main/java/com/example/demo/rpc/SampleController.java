package com.example.demo.rpc;

import com.example.demo.svc.SimpleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sample")
@Slf4j
public class SampleController {

  @Autowired ServiceConfig serviceConfig;

  @RequestMapping("/hello")
  public Mono<String> sayHello() {
    log.info("say hello-incoming request");
    return serviceConfig
        .getSimpleClientStub()
        .requestReply(SimpleRequest.newBuilder().setRequestMessage("Hello Client").build())
        .doOnNext(
            simpleResponse ->
                log.info("response from service {}", simpleResponse.getResponseMessage()))
        .map(simpleResponse -> simpleResponse.getResponseMessage());
  }
}
