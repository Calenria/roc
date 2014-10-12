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
package com.github.calenria.roc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.calenria.roc.commands.CommandHandler;
import com.github.calenria.roc.listener.RocListener;
import com.github.calenria.roc.managers.RocManager;
import com.github.calenria.roc.models.ConfigData;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.SimpleInjector;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

/**
 * NextVote ein BukkitPlugin zum verteilen von Vote Belohnungen.
 * 
 * @author Calenria
 */
public class Roc extends JavaPlugin {

    /**
     * Standart Bukkit Logger.
     */
    private static Logger log = Logger.getLogger("Minecraft");

    /**
     * Vault Economy.
     * 
     * @return the economy
     */
    public static Economy getEconomy() {
        return economy;
    }

    /**
     * Listener.
     */
    private RocListener               rocListener = null;

    /**
     * Vote Manager.
     */
    private RocManager                rocManager  = null;

    /**
     * Kommandos.
     */
    private CommandsManager<CommandSender> commands;

    /**
     * Vault Permissions.
     */
    private static Permission              permission       = null;

    /**
     * Vault Economy.
     */
    private static Economy                 economy          = null;

    /**
     * Vault Permissions.
     * 
     * @return the permission
     */
    public static Permission getPermission() {
        return permission;
    }

    /**
     * ResourceBundle der I18N Strings.
     */
    private ResourceBundle messages  = null;
    /**
     * ResourceBundle der I18N Item Namen.
     */
    private ResourceBundle items     = null;

    /**
     * Liste der heutigen Votes.
     */
    private List<String>   currVotes = new ArrayList<String>();

    /**
     * Objekt zum zugriff auf die Konfiguration.
     */
    private ConfigData     config    = null;
    /**
     * String der gewählten Sprache.
     */
    private String         lang      = "de";

    /**
     * Fügt einen Spielervote zur heutigen liste hinzu.
     * 
     * @param vote
     *            Spielername
     */
    public final synchronized void addVote(final String vote) {
        this.currVotes.add(vote);
    }

    /**
     * Lister der heutigen Spielervotes.
     * 
     * @return the currVotes
     */
    public final synchronized List<String> getCurrVotes() {
        return currVotes;
    }

    /**
     * Teilt Bukkit die verfügbaren Models mit.
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#getDatabaseClasses()
     * @return List<Class<?>> Liste aller Models
     */
    @Override
    public final List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
//        list.add(VoteData.class);
//        list.add(VoteHistory.class);
//        list.add(VoteAggregate.class);
        return list;
    }

    /**
     * Gibt ein ResourceBundle mit Itemstrings zurück (items_(lang).properties).
     * 
     * @return the items
     */
    public final ResourceBundle getItems() {
        return items;
    }

    /**
     * Die aktuell gewählte Sprache.
     * 
     * @return the lang
     */
    public final String getLang() {
        return lang;
    }

    /**
     * ResourceBundle der I18N Strings.
     * 
     * @return the messages
     */
    public final ResourceBundle getMessages() {
        return messages;
    }

    /**
     * Listener Objekt.
     * 
     * @return Listener
     */
    public final RocListener getNextVoteListener() {
        return rocListener;
    }

    /**
     * Vote Manager.
     * 
     * @return the nextVoteManager
     */
    public final RocManager getNextVoteManager() {
        return rocManager;
    }

    /**
     * @return the config
     */
    public final ConfigData getPluginConfig() {
        return config;
    }

    /**
     * Installiert die Datenbank.
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#installDDL()
     */
    @Override
    public final void installDDL() {
        super.installDDL();
    }

    /**
     * Delegiert die registierten Befehle an die jeweiligen Klassen und prüft ob die Benutzung korrekt ist.
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
     * @param sender
     *            Der Absender des Befehls
     * @param cmd
     *            Das Kommando
     * @param label
     *            Das Label
     * @param args
     *            String Array von Argumenten
     * @return <tt>true</tt> wenn der Befehl erfolgreich ausgeführt worden ist
     */
    @Override
    public final boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        try {
            commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + messages.getString("noPerms"));
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + messages.getString("numberFormat"));
            } else {
                sender.sendMessage(ChatColor.RED + messages.getString("exception"));
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }
        return true;
    }

    /**
     * Wird beim auschalten des Plugins aufgerufen.
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public final void onDisable() {
        log.log(Level.INFO, String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    /**
     * Initialisierung des Plugins.
     * 
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public final void onEnable() {
        setupConfig();
        setupLang();
        setupPermissions();
        setupEconomy();
        setupListeners();
        setupdManagers();
        setupCommands();
        log.log(Level.INFO, String.format("[%s] Enabled Version %s", getDescription().getName(), getDescription().getVersion()));
    }

    /**
     * Liest die Eigentlichen Sprachdateien ein.
     * 
     * @param type
     *            muss gesetzt sein und repräsentiert entweder die Items oder Nachrichten
     * @return <tt>PropertyResourceBundle</tt>
     */
    private PropertyResourceBundle readProperties(final String type) {
        PropertyResourceBundle bundle = null;
        File messagesFile = new File(this.getDataFolder(), type + lang + ".properties");
        if (!messagesFile.exists()) {
            try {
                Utils.copy(getResource(type + lang + ".properties"), messagesFile);
            } catch (Exception e) {
                log.info("Keine " + type + " für " + lang + " gefunden! Erstelle standard Datei!");
                try {
                    Utils.copy(getResource(type + "de.properties"), messagesFile);
                } catch (IOException e1) {
                    log.log(Level.SEVERE, e.getLocalizedMessage());
                }
            }
        }

        try {
            bundle = new PropertyResourceBundle(new FileInputStream(messagesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    /**
     * Setzt die verwendete Sprache.
     * 
     * @param language
     *            Sprache welche verwendet werden soll
     */
    public final void setLang(final String language) {
        this.lang = language;
    }

    /**
     * Initialisierung der Plugin Befehle.
     */
    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(final CommandSender sender, final String perm) {
                return permission.has(sender, perm);
            }
        };

        commands.setInjector(new SimpleInjector(this));

        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(CommandHandler.class);

    }

    /**
     * Liest die Konfiguration aus und erzeugt ein ConfigData Objekt.
     */
    public final void setupConfig() {
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveConfig();
        } else {
            this.reloadConfig();
        }
        this.config = new ConfigData(this);
        this.lang = config.getLang();

    }

    /**
     * Initialisierung des Plugin Managers und starten der Sheduler.
     */
    private void setupdManagers() {
        rocManager = new RocManager(this);
    }

    /**
     * Überprüft ob Vault vorhanden und ein passender Economyhandler verfügbar ist.
     * 
     * @return <tt>true</tt> wenn ein Vault Economyhandler gefunden wird.
     */
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

    /**
     * Initialisiert die Sprachdateien.
     */
    public final void setupLang() {
        messages = readProperties("messages_");
    }

    /**
     * Initialisierung der Eventlistener.
     */
    private void setupListeners() {
        rocListener = new RocListener(this);
    }

    /**
     * Überprüft ob Vault vorhanden und ein passender Permissionhandler verfügbar ist.
     * 
     * @return <tt>true</tt> wenn ein Vault Permissionhandler gefunden wird.
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

}
