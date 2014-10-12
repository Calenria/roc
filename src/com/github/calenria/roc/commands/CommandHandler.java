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
package com.github.calenria.roc.commands;

import org.bukkit.command.CommandSender;

import com.github.calenria.roc.Roc;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;

/**
 * Befehls Klasse für Roc.
 * 
 * @author Calenria
 * 
 */
public class CommandHandler {
    /**
     *
     */
    private final Roc plugin;

    /**
     * @param rcPlugin
     *            Plugin
     */
    public CommandHandler(final Roc rcPlugin) {
        this.plugin = rcPlugin;
    }

    /**
     * Delegiert Befehle an die Klasse VoteCommands.
     * 
     * @see com.github.calenria.RocCommands.commands.VoteCommands
     * @param args
     *            Befehls Argumente
     * @param sender
     *            Absender des Befehls
     */
    // @Command(aliases = { "rc", "roc" }, desc = "Roc Commands")
    // @NestedCommand({ RocCommands.class })
    // public void Roc(final CommandContext args, final CommandSender sender) {
    // }

    /**
     * Der Befehl /forcerestart, tötet den Server und startet ihn neu.
     * 
     * @param args
     *            Befehls Argumente
     * @param sender
     *            Absender des Befehls
     * @throws com.sk89q.minecraft.util.commands.CommandException
     *             CommandException
     */
    @Command(aliases = { "forcerestart" }, desc = "Force Restart", usage = "/forcerestart", min = 0, max = 0)
    @CommandPermissions("roc.restart")
    public final void vote(final CommandContext args, final CommandSender sender)
            throws CommandException {
        /* TODO: nyi */
    }

    /**
     * Return Plugin.
     * 
     * @return plugin
     */
    public final Roc getPlugin() {
        return plugin;
    }
}
