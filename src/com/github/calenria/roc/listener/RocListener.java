/*
 * Copyright (C) 2012 Calenria <https://github.com/Calenria/> and contributors
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */
package com.github.calenria.roc.listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.github.calenria.roc.Roc;

/**
 * Eventlistener Klasse.
 * 
 * @author Calenria
 * 
 */
public class RocListener implements Listener {
    /**
     * Bukkit Logger.
     */
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger("Minecraft");
    /**
     * NextVote Plugin.
     */
    private Roc plugin = null;

    /**
     * Registriert die Eventhandler und erstellt die Datenbank falls nicht
     * vorhanden.
     * 
     * @param rcPlugin
     *            Roc Plugin
     */
    public RocListener(final Roc rcPlugin) {
        this.plugin = rcPlugin;
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }



}
