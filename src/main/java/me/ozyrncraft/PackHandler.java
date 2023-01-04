package me.ozyrncraft;

import com.sun.net.httpserver.HttpExchange;
import lombok.Getter;
import me.ozyrncraft.commands.PackMCCommands;
import me.ozyrncraft.handlers.BasicRequestHandler;
import me.ozyrncraft.handlers.WebRequestHandler;
import me.ozyrncraft.listeners.SendResourcePackOnJoinListener;
import me.ozyrncraft.listeners.SendResourcePackOnWorldJoinListener;
import me.ozyrncraft.utils.ConfigurationUtils;
import me.ozyrncraft.files.FileUtils;
import me.ozyrncraft.utils.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public final class PackHandler extends JavaPlugin {

    public static PackHandler plugin;

    public static WebRequestHandler requestHandler;

    // Config.yml file
    @Getter public static FileConfiguration configuration;
    public static File configFile;

    // World-data.yml file
    @Getter public static FileConfiguration worldData;
    public static File worldDataFile;


    public PackHandler() {
        requestHandler = new BasicRequestHandler();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Create data files and config files
        ConfigurationUtils.createConfig();
        ConfigurationUtils.createWorldDataConfig();

        // Copy all webpage files over
        FileUtils.copyFilesFromResources();

        // Handle world changes and server joining for sending the packs
        Bukkit.getPluginManager().registerEvents(new SendResourcePackOnWorldJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new SendResourcePackOnJoinListener(), this);

        // Register all commands
        Bukkit.getPluginCommand("pack").setExecutor(new PackMCCommands());

        // Start the web server for uploading resource packs
        try {
            ServerUtils.startWebServer(getConfiguration().getInt("host-port"));
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Cannot start resource pack server due to an error!");
            throw new RuntimeException(e);
        }

        // Send startup message to console
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Ozyrncraft pack handler has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ServerUtils.stopWebServer();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Upload server has successfully been stopped!");

        // Send shutdown message to console
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Ozyrncraft pack handler has been disabled!");
    }


    public void handle(HttpExchange exchange, File output) throws IOException {
        List<String> lines = FileUtils.readFile(output);
        String response = "";
        for (String line : lines)
            response += line;

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        writer.write(response);
        writer.close();
    }
}
