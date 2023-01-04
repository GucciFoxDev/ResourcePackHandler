package me.ozyrncraft.states;

import lombok.Getter;
import me.ozyrncraft.PackHandler;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ResourcePackState {

    // URL prefix for the download/upload server
    @Getter public static String resourcePackUrlPrefix = "http://" + PackHandler.getConfiguration().getString("host-hostname") + ":" + PackHandler.getConfiguration().getInt("host-port") + "/";
    public static FileConfiguration configuration = PackHandler.getConfiguration();

    /**
     * Sets the resource pack ID for a world
     * @param worldName world name to set it for
     * @param packID ID of pack to set for that world
     * @param forcePack should we force them to accept the pack?
     * @throws IOException
     */
    public static void setResourcePack(String worldName, String packID, Boolean forcePack) throws IOException, NoSuchAlgorithmException {
        PackHandler.getWorldData().set(worldName + ".pack-id", packID);
        PackHandler.getWorldData().set(worldName + ".force-pack", forcePack);
        PackHandler.getWorldData().save("plugins/PackHandler/world-data.yml");
    }

    /**
     * Gets the resource pack URL for a world
     * @param worldName name of world to get resource pack for
     * @return the URL for the resource pack download
     */
    public static String getResourcePackUrlForWorld(String worldName) {
        String packID = PackHandler.getWorldData().getString(worldName + ".pack-id");

        return resourcePackUrlPrefix + "get-resource-pack/" + packID;
    }

    // UNUSED METHOD DUE TO ERRORS | WILL FIX IN FUTURE
    public static byte[] getbinarySha1Hash(String worldName) throws IOException, NoSuchAlgorithmException {
        File resourcePackFile = new File(PackHandler.getConfiguration().getString("uploaded-resource-pack-folder") + "/" + PackHandler.getWorldData().getString(worldName + ".pack-id") + ".zip");
        InputStream source = Files.newInputStream(resourcePackFile.toPath());
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        byte[] dataBytes = new byte[1024];

        int nread = 0;

        while ((nread = source.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };

        byte[] mdbytes = md.digest();

        return mdbytes;
    }
}
