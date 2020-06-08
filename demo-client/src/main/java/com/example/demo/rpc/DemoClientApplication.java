package com.example.demo.rpc;

import com.example.demo.svc.RsocketSimpleServiceRpc;
import com.example.demo.svc.RsocketSimpleServiceRpc.RsocketSimpleServiceStub;
import com.example.demo.svc.SimpleRequest;
import io.rsocket.rpc.core.extension.RsocketClientBuilder;
import io.rsocket.rpc.core.extension.tracing.RSocketTracing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DemoClientApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(DemoClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("Starting the client application");
  }
}
