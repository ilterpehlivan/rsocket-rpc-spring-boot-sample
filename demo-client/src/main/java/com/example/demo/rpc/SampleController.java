package com.example.demo.rpc;

import com.example.demo.svc.SimpleRequest;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import java.util.Collection;
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
  @Autowired MeterRegistry meterRegistry;

  @RequestMapping("/hello")
  public Mono<String> sayHello() {
    log.info("say hello-incoming request");
    return serviceConfig
        .getSimpleClientStub()
        .requestReply(SimpleRequest.newBuilder().setRequestMessage("Hello Client").build())
        .doOnNext(
            simpleResponse ->
                log.info("response from service {}", simpleResponse.getResponseMessage()))
        .map(simpleResponse -> simpleResponse.getResponseMessage())
        .doFinally(
            signalType -> {
              log.info("doFinally:signal {} .. printing metrics",signalType);
              printClientMetrics("request.requestReply");
//              printAllMetrics();
            });
  }

  private void printClientMetrics(String name) {
    Search search = meterRegistry.find(name);
    Collection<Meter> meters = search.meters();
    if (meters != null) {
      meters.forEach(
          meter -> {
            log.info("client meter id {} result {}", meter.getId(), meter.measure());
          });
    } else {
      log.info("Client meter is null {}", name);
    }
  }

  private void printAllMetrics(){
    meterRegistry.forEachMeter(
        meter -> {
          log.info("client meter id {} result {}", meter.getId(), meter.measure());
        });
  }
}
