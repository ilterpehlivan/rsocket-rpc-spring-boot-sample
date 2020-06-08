package com.example.demo.rpc;

import brave.Tracing;
import io.micrometer.core.instrument.MeterRegistry;
import io.rsocket.rpc.core.extension.RpcServer;
import io.rsocket.rpc.core.extension.RsocketServerBuilder;
import io.rsocket.rpc.core.extension.tracing.RSocketTracing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoServerApplication implements CommandLineRunner {

  @Value("${sample.service.port:9090}")
  private Integer servicePort;

  @Autowired Tracing tracing;

  public static void main(String[] args) {
    SpringApplication.run(DemoServerApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    RpcServer server =
        RsocketServerBuilder.forPort(9090)
            .addService(new SimpleServiceImpl())
//            .withMetrics(meterRegistry)
            .interceptor(RSocketTracing.create(tracing).newServerInterceptor())
            .build()
            .start();
    server.awaitTermination();
  }
}
