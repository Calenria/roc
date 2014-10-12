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
package com.github.calenria.roc.models;

import org.bukkit.configuration.file.FileConfiguration;

import com.github.calenria.roc.Roc;

/**
 * Konfigurations Klasse.
 * 
 * @author Calenria
 * 
 */
public class ConfigData {

    /**
     * Die gewählte Sprache.
     */
    private String       lang;
    /**
     * Liste von Strings die bei /vote ausgegeben wird.
     */

    /**
     * @param plugin
     *            NextVote Plugin
     */
    public ConfigData(final Roc plugin) {
        FileConfiguration config = plugin.getConfig();
        setLang(config.getString("lang"));
    }

    /**
     * @return the lang
     */
    public final String getLang() {
        return lang;
    }

    /**
     * @param cLang
     *            the lang to set
     */
    public final void setLang(final String cLang) {
        this.lang = cLang;
    }
}
