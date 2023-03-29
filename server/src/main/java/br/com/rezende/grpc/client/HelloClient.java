package br.com.rezende.grpc.client;
import javax.annotation.PostConstruct;

import br.com.rezende.grpc.model.HelloRequest;
import br.com.rezende.grpc.model.HelloResponse;
import br.com.rezende.grpc.model.HelloServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Component("HelloClient")
public class HelloClient {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(HelloClient.class);

    private HelloServiceGrpc.HelloServiceBlockingStub serviceBlockingStub;
    @Value("${server.port}")
    private int serverPort;

    @PostConstruct
    private void init() {
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", serverPort).usePlaintext().build();

        serviceBlockingStub =
                HelloServiceGrpc.newBlockingStub(managedChannel);
    }

    public String sayHello(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        LOGGER.info("client sending {}", request);

        HelloResponse response =
                serviceBlockingStub.sayHello(request);
        LOGGER.info("client received {}", response);

        return response.getMessage();
    }
}
