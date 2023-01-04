package me.ozyrncraft.files;

import me.ozyrncraft.PackHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {

    /**
     * Copy all html files and folders to the plugin data folder
     */
    public static void copyFilesFromResources() {
        // Create "webpages" folder
        if (!new File("plugins/PackHandler/webpages").exists() && !new File("plugins/PackHandler/webpages").mkdirs()) {
            new File("plugins/PackHandler/webpages");
        }

        // Create "uploaded-packs" folder
        if (!new File("plugins/PackHandler/uploaded-packs").exists() && !new File("plugins/PackHandler/uploaded-packs").mkdirs()) {
            new File("plugins/PackHandler/uploaded-packs");
        }

        // Create "active-packs" folder
        if (!new File("plugins/PackHandler/active-packs").exists() && !new File("plugins/PackHandler/active-packs").mkdirs()) {
            new File("plugins/PackHandler/active-packs");
        }


        // Create html files
        if (!new File("plugins/PackHandler/webpages", "webpages/index.html").exists()) {
            PackHandler.getPlugin(PackHandler.class).saveResource("webpages/index.html", false);
        }
        if (!new File("plugins/PackHandler/webpages", "webpages/successful-upload.html").exists()) {
            PackHandler.getPlugin(PackHandler.class).saveResource("webpages/successful-upload.html", false);
        }
        if (!new File("plugins/PackHandler/webpages", "webpages/unsuccessful-upload.html").exists()) {
            PackHandler.getPlugin(PackHandler.class).saveResource("webpages/unsuccessful-upload.html", false);
        }
        if (!new File("plugins/PackHandler/webpages", "webpages/404.html").exists()) {
            PackHandler.getPlugin(PackHandler.class).saveResource("webpages/404.html", false);
        }
    }

    /**
     * Reads the contents of a specified file
     * @param file file to read the contents of
     * @return a list of lines of the file
     * @throws IOException
     */
    public static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    /**
     * Gets the 404 webpage
     * @return the 404 webpage file
     */
    public static File getWebsite404() {
        return new File("plugins/PackHandler/webpages", "404.html");
    }

    /**
     * Returns the website page requested
     * @param request request file
     * @return the page requested
     */
    public static File getWebsitePage(String request) {
        if (request.endsWith("/")) // ends with folder directory
            request += "webpages/index.html";
        File file = new File("plugins/PackHandler/webpages", request);
        if (file.exists() && file.isDirectory())
            return getWebsitePage(request + "index.html");
        if (!file.exists())
            return getWebsite404();
        return file;
    }
}
