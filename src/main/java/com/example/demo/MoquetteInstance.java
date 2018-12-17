package com.example.demo;

public class MoquetteInstance {
    private String host;
    private Integer port;

    public MoquetteInstance(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String address) {
        this.host = address;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
