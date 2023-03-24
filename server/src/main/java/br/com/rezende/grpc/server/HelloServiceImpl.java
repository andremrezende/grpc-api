package br.com.rezende.grpc.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.rezende.grpc.model.HelloRequest;
import br.com.rezende.grpc.model.HelloResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import br.com.rezende.grpc.model.HelloServiceGrpc;

@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        LOGGER.info("server received {}", request);

        String message = "Hello " + request.getName() + "!";
        HelloResponse helloResponse =
                HelloResponse.newBuilder().setMessage(message).build();
        LOGGER.info("server responded {}", helloResponse);

        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    static String toMessage(String name) {
        return "Hello, " + name + '!';
    }

    private static HelloResponse buildReply(Object message) {
        return HelloResponse.newBuilder().setMessage(String.valueOf(message)).build();
    }
}
