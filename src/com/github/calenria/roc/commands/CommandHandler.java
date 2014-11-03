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

import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
     * Standart Bukkit Logger.
     */
    private static Logger log = Logger.getLogger("Minecraft");

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
    public final void forcerestart(final CommandContext args, final CommandSender sender) throws CommandException {
        /* TODO: nyi */
    }

    /**
     * Der Befehl /unloadworld, Entlädt eine welt.
     * 
     * @param args
     *            Befehls Argumente
     * @param sender
     *            Absender des Befehls
     * @throws com.sk89q.minecraft.util.commands.CommandException
     *             CommandException
     */
    @Command(aliases = { "unloadworld" }, desc = "Entlädt eine Welt", usage = "/unloadworld", min = 1, max = 1)
    @CommandPermissions("roc.unloadworld")
    public final void unloadworld(final CommandContext args, final CommandSender sender) throws CommandException {

        log.log(Level.INFO, "Start unload");
        String uWorld = args.getString(0);

        if (uWorld.equals("world")) {
            sender.sendMessage("Welt world darf nicht entladen werden!");
        }

        List<World> worlds = Bukkit.getWorlds();
        sender.sendMessage("Entlade Welt: " + uWorld);
        World wtu = null;
        Boolean found = false;
        for (World world : worlds) {
            if (world.getName().equals(uWorld)) {
                found = true;
                wtu = world;
                break;
            }
        }

        if (found && wtu != null) {
            log.log(Level.INFO, "Teleporting players");
            List<Player> players = wtu.getPlayers();
            for (Player player : players) {
                Bukkit.dispatchCommand(player, "spawn");
            }
            log.log(Level.INFO, "Saving World");
            Bukkit.unloadWorld(uWorld, true);
            sender.sendMessage("Welt " + uWorld + " entladen");
        } else {
            sender.sendMessage("Welt konnte nicht gefunden Werden!");
        }
    }

    @Command(aliases = { "tpw" }, desc = "Portet in eine Welt", usage = "<welt> [x y z]", min = 1, max = 4)
    @CommandPermissions("roc.tpw")
    public final void tpw(final CommandContext args, final CommandSender sender) throws CommandException {
        String sworld = args.getString(0);
        String worldFolder = Bukkit.getWorld("world").getWorldFolder().getPath();

        Integer x = 0;
        Integer y = 0;
        Integer z = 0;

        if (args.argsLength() > 1) {
            x = args.getInteger(1);
            y = args.getInteger(2);
            z = args.getInteger(3);
        }
        File world = new File(worldFolder + "/" + sworld);
        log.log(Level.INFO, "Teleporting to World: " + world.getName());

        if (world.isDirectory()) {
            if (x != 0 && y != 0 && z != 0) {
                log.log(Level.INFO, "Teleporting to Coords: " + x + "," + y + "," + z);
                Location loc = new Location(Bukkit.createWorld(new WorldCreator(sworld)), x, y, z);
                Bukkit.getPlayer(sender.getName()).teleport(loc);
            } else {
                Bukkit.getPlayer(sender.getName()).teleport(Bukkit.createWorld(new WorldCreator(sworld)).getSpawnLocation());
            }
        } else {
            sender.sendMessage("Welt existiert nicht!");
        }

    };

    /**
     * Return Plugin.
     * 
     * @return plugin
     */
    public final Roc getPlugin() {
        return plugin;
    }
}
