package com.example.demo.rpc;

import brave.Tracing;
import com.example.demo.svc.RsocketSimpleServiceRpc;
import com.example.demo.svc.RsocketSimpleServiceRpc.RsocketSimpleServiceStub;
import io.micrometer.core.instrument.MeterRegistry;
import io.rsocket.rpc.core.extension.RsocketClientBuilder;
import io.rsocket.rpc.core.extension.tracing.RSocketTracing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

  @Value("${demo.service.address:localhost}")
  private String serviceAddress;

  @Value("${sample.service.port:9090}")
  private Integer servicePort;

  private RsocketSimpleServiceStub simpleServiceStub;

  @Autowired Tracing tracing;
  @Autowired MeterRegistry meterRegistry;

  public RsocketSimpleServiceStub getSimpleClientStub() {
    if (simpleServiceStub == null) {
      RsocketClientBuilder clientBuilder =
          RsocketClientBuilder.forAddress(serviceAddress, servicePort)
              .withMetrics(meterRegistry)
              .interceptor(RSocketTracing.create(tracing).newClientInterceptor());
      simpleServiceStub = RsocketSimpleServiceRpc.newReactorStub(clientBuilder);
    }
    return simpleServiceStub;
  }
}
