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
package com.github.calenria.roc.managers;

import java.util.logging.Logger;

import com.github.calenria.roc.Roc;

/**
 * @author Calenria
 * 
 */
public class RocManager {
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
     * Initialisiert und erstellt die Datenbank falls nicht vorhanden.
     * 
     * @param rcPlugin
     *            Roc Plugin
     */
    public RocManager(final Roc rcPlugin) {
        this.setPlugin(rcPlugin);
    }

    /**
     * Return Plugin.
     * 
     * @return plugin
     */
    public final Roc getPlugin() {
        return plugin;
    }

    /**
     * Set Plugin.
     * 
     * @param rcPlugin
     *            Roc Plugin
     */
    public final void setPlugin(final Roc rcPlugin) {
        this.plugin = rcPlugin;
    }

}
