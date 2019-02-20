package com.example.demo;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.moquette.BrokerConstants;
import io.moquette.server.Server;
import io.moquette.server.config.MemoryConfig;
import org.springframework.util.SocketUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

@Component
public class MoquetteBroker implements DisposableBean, InitializingBean {

    /*
    @Value("${mqtt.serverport:1883}")
    private int serverPort;

    @Value("${mqtt.host:0.0.0.0}")
    private String host;

    @Value("${mqtt.websocket_port:8080}")
    private int websocketPort;
    */

    private Server server;
    private MemoryConfig config;

    public void start() throws IOException {

        config = new MemoryConfig(new Properties());
        //config.setProperty("port", Integer.toString(SocketUtils.findAvailableTcpPort()));
        config.setProperty("port", Integer.toString(1883));
        //config.setProperty("websocket_port", Integer.toString(websocketPort));
        config.setProperty("host", InetAddress.getLocalHost().getHostAddress());
        config.setProperty(BrokerConstants.ALLOW_ANONYMOUS_PROPERTY_NAME, "true");
        //config.setProperty("authenticator_class", SpringAuthenticationWrapper.class.getName());
        //config.setProperty("authorizator_class", SpringAuthorizationWrapper.class.getName());
        server = new Server();
        server.startServer(config);
    }

    @Override
    public void destroy() throws Exception {
        server.stopServer();
        System.out.println("Server stopped!");
    }

    public MemoryConfig getMemoryConfig(){
        return this.config;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
        System.out.println("Server started!");
    }
}
