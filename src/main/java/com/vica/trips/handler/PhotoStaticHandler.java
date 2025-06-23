package com.vica.trips.handler;

import com.networknt.handler.LightHttpHandler;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;

import java.io.File;
/*
to serve the photos on this server
 */
public class PhotoStaticHandler implements LightHttpHandler {

    private static final String pathToPhotos = "C:/vicas/professional/tripsProject/uploads";

    private static final HttpHandler delegate;

    static {
        File baseDir = new File( pathToPhotos );
        delegate = Handlers.resource(new FileResourceManager(baseDir, 100))
                .setDirectoryListingEnabled(false);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        delegate.handleRequest(exchange);  // delegate to Undertow handler
    }}
