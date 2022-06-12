package me.kimyounghan.grpcserver;

import io.grpc.stub.StreamObserver;
import me.kimyounghan.examples.lib.HelloReply;
import me.kimyounghan.examples.lib.HelloRequest;
import me.kimyounghan.examples.lib.SimpleGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
            .setMessage("Hello ==> : " + request.getName())
            .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
