package com.github.calenria.roc;


public class HandlerServerStop extends Thread {
    
    private Roc plugin;

    public HandlerServerStop(Roc plugin) {
        this.plugin = plugin;
    }

    public void run() {
        Utils.logToFile(plugin, "0-" + new Long(System.currentTimeMillis() / 1000L).toString());
    }
}
