package me.kimyounghan.grpcclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GrpcClientController {
    private final GrpcClientService grpcClientService;

    @GetMapping("/test")
    public String printMessage() {
        return grpcClientService.sendMessage("kimyounghan");
    }
}
