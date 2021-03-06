package com.example.demo;

import io.moquette.broker.ClientDescriptor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MoquetteController {

    private final MoquetteBroker mb;

    public MoquetteController(MoquetteBroker mb) {
        this.mb = mb;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public List<String> getConnectedClients(){
         return mb.getServer()
                 .listConnectedClients()
                 .stream()
                 .map(ClientDescriptor::getClientID).collect(Collectors.toList());

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start() throws IOException {
        mb.start();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public void stop(){
        mb.stopServer();
    }
}