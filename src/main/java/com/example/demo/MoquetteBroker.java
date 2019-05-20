package com.example.demo;

import io.moquette.broker.Server;
import io.moquette.broker.config.MemoryConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.stereotype.Component;
import io.moquette.BrokerConstants;



import java.io.IOException;
import java.util.Properties;

@Component
public class MoquetteBroker implements DisposableBean, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MoquetteBroker.class);

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
    private boolean started = false;

    public void start() throws IOException {
        if(started){
            logger.info("server already started!");
            return;
        }
        config = new MemoryConfig(new Properties());
        config.setProperty(BrokerConstants.ALLOW_ANONYMOUS_PROPERTY_NAME, "false");
        config.setProperty(BrokerConstants.AUTHENTICATOR_CLASS_NAME, AuthenticationWrapper.class.getName());
        server = new Server();
        server.startServer(config);
        started = true;
        logger.info("Broker Started");
    }

    public void stopServer(){
        server.stopServer();
        started = false;
        logger.info("Broker Stopped");
    }

    @Override
    public void destroy() throws Exception {
        server.stopServer();
        started = false;
        System.out.println("Server stopped!");
    }

    public MemoryConfig getMemoryConfig(){
        return this.config;
    }

    public Server getServer() { return this.server;}

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
        System.out.println("Server started!");
    }
}
