package br.com.rezende.grpc.server;

import br.com.rezende.grpc.model.HelloRequest;
import br.com.rezende.grpc.model.HelloResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import br.com.rezende.grpc.model.HelloWorldServiceGrpc;

@GrpcService
public class HelloServiceImpl extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        if (request.getName().isEmpty()) {
            responseObserver.onError(
                    Status.FAILED_PRECONDITION.withDescription("Name cannot be empty").asRuntimeException());
        } else {
            responseObserver.onNext(buildReply(toMessage(request.getName())));
            responseObserver.onCompleted();
        }
    }

    static String toMessage(String name) {
        return "Hello, " + name + '!';
    }

    private static HelloResponse buildReply(Object message) {
        return HelloResponse.newBuilder().setMessage(String.valueOf(message)).build();
    }
}
