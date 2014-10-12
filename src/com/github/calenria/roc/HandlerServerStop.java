package com.github.calenria.roc;


public class HandlerServerStop extends Thread {
    
    private Roc plugin;

    public HandlerServerStop(Roc plugin) {
        this.plugin = plugin;
    }

    public void run() {
        Utils.logToFile(plugin, "0");
    }
}
