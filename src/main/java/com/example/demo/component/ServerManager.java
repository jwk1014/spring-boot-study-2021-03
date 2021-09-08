package com.example.demo.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerManager {

    @Value("${server.port}")
    private Integer serverPort;

    public Integer getPort() {
        return serverPort;
    }
}
