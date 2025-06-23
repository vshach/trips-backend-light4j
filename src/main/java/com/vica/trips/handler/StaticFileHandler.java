package com.vica.trips.handler;

import com.networknt.config.Config;
import com.networknt.handler.LightHttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

/***
 * This is what serves the uploaded photos. The folder from which to get those photos is
 * defined in values.yml
 */
public class StaticFileHandler implements LightHttpHandler {

    public static String basePath;// = "C:/vicas/professional/tripsProject/uploads/";
    static {
        // Code here runs once when the class is loaded
        Map<String, Object> values = Config.getInstance().getJsonMapConfig("values");
        basePath = (String) values.get("photoUploadDir");
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        System.out.println("\n\n\n --------------------------StaticFileHandler\n\n\n");
        /*String relativePath = exchange.getRelativePath();
        //remove leading / if exists
        relativePath = relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
        relativePath = relativePath.substring( relativePath.indexOf("/")+1);
        */
        String relativePath = exchange.getQueryParameters().get("filepath").getFirst();

        File file = new File(basePath, relativePath);
        if (!file.exists() || file.isDirectory()) {
            exchange.setStatusCode(404);
            exchange.getResponseSender().send("File Not Found");
            return;
        }

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, guessContentType(file.getName()));
        try (FileInputStream fis = new FileInputStream(file);
             FileChannel channel = fis.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            channel.read(buffer);
            buffer.flip();
            exchange.getResponseSender().send(buffer);
        } catch (IOException e) {
            exchange.setStatusCode(500);
            exchange.getResponseSender().send("Internal Server Error");
        }
    }

    private String guessContentType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return "image/jpeg";
        if (fileName.endsWith(".png")) return "image/png";
        if (fileName.endsWith(".gif")) return "image/gif";
        return "application/octet-stream";
    }
}

