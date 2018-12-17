package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoquetteController {

    @Autowired
    private MoquetteBroker mb;

    @RequestMapping("/config")
    @ResponseBody
    public MoquetteInstance getConfig(){
    MoquetteInstance mi = new MoquetteInstance(mb.getMemoryConfig().getProperty("host"), Integer.parseInt(mb.getMemoryConfig().getProperty("port")));
    return mi;
    }
}