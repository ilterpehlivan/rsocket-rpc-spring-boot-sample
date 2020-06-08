package com.example.demo.rpc;

import com.example.demo.svc.RsocketSimpleServiceRpc.SimpleServiceImplBase;
import com.example.demo.svc.SimpleRequest;
import com.example.demo.svc.SimpleResponse;
import com.google.protobuf.Empty;
import io.netty.buffer.ByteBuf;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class SimpleServiceImpl implements SimpleServiceImplBase {

  @Override
  public Mono<SimpleResponse> requestReply(SimpleRequest request, ByteBuf metadata) {
    log.info("received requestReply message {}", request.getRequestMessage());
    return Mono.just(SimpleResponse.newBuilder().setResponseMessage("Server Hello").build());
  }

  @Override
  public Mono<Empty> fireAndForget(SimpleRequest request, ByteBuf metadata) {
    log.info("fireAndForget:got message -> {}", request.getRequestMessage());
    return Mono.just(Empty.getDefaultInstance());
  }

  @Override
  public Flux<SimpleResponse> requestStream(SimpleRequest request, ByteBuf metadata) {
    String requestMessage = request.getRequestMessage();
    log.info("requestStream:message {}", requestMessage);
    return Flux.interval(Duration.ofMillis(200))
        .onBackpressureDrop()
        .map(i -> i + " Server- got message - " + requestMessage)
        .map(s -> SimpleResponse.newBuilder().setResponseMessage(s).build());
  }

  @Override
  public Flux<SimpleResponse> streamingRequestAndResponse(
      Flux<SimpleRequest> request, ByteBuf metadata) {
    log.info("streamingRequestAndResponse request is received");
    return Flux.from(request).flatMap(e -> requestReply(e, metadata));
  }
}
