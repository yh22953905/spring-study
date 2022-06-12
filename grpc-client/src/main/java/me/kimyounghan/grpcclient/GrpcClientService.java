package me.kimyounghan.grpcclient;

import io.grpc.StatusRuntimeException;
import me.kimyounghan.examples.lib.HelloReply;
import me.kimyounghan.examples.lib.HelloRequest;
import me.kimyounghan.examples.lib.SimpleGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {
    @GrpcClient("test")
    private SimpleGrpc.SimpleBlockingStub simpleBlockingStub;

    public String sendMessage(final String name) {
        try {
            HelloReply response = this.simpleBlockingStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCause().getMessage();
        }
    }
}
