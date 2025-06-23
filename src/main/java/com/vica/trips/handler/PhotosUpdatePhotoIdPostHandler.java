package com.vica.trips.handler;

import com.networknt.body.BodyHandler;
import com.networknt.config.Config;

import com.networknt.handler.LightHttpHandler;
import com.networknt.http.HttpMethod;
import com.networknt.http.HttpStatus;
import com.networknt.http.MediaType;
import com.vica.trips.databaseobj.PhotoDB;
import com.vica.trips.mappers.MyBatisSqlSessionFactory;
import com.vica.trips.mappers.PhotoMapper;
import com.vica.trips.utils.DatePathUtil;

import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormEncodedDataDefinition;
import io.undertow.util.Headers;
import io.undertow.util.HeaderMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.Map;

import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.PathTemplateMatch;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class PhotosUpdatePhotoIdPostHandler implements LightHttpHandler {

    
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // HeaderMap requestHeaders = exchange.getRequestHeaders();
        // Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();

        //Read path parameter
        String photoIdStr = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY)
                .getParameters().get("photoNum");
        int photoId = Integer.parseInt( photoIdStr );

        updateFileMetaData( exchange, photoId );

        String responseBody = "{}";
        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        exchange.setStatusCode(HttpStatus.OK.value());
        exchange.getResponseSender().send(responseBody);
    }

    private void updateFileMetaData( HttpServerExchange exchange, int photoId ) throws IOException {

        // Metadata fields
        /*String people = formData.getFirst("people").getValue();
        String locationIdStr = formData.getFirst("locationId").getValue();
        String newdateStr = formData.getFirst("date").getValue();
        */
        String locationIdStr = exchange.getQueryParameters().get("locationId").getFirst();
        String people = exchange.getQueryParameters().get("people").getFirst();
        String newdateStr = exchange.getQueryParameters().get("date").getFirst();

        //log for debug
        System.out.println("new details: ");
        System.out.println("People: " + people);
        System.out.println("Location ID: " + locationIdStr);
        System.out.println("Date: " + newdateStr);

        int locationId = Integer.parseInt( locationIdStr );
        LocalDate newdate = LocalDate.parse( newdateStr);  //yyyy-mm-dd

        SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PhotoMapper mapper
                    = session.getMapper(PhotoMapper.class);
            PhotoDB photoDB = mapper.getPhotoInfoById( photoId );
            System.out.println("Photo filename: " + photoDB.getFilename());

            LocalDate oldDate = photoDB.getDate();
            photoDB.setLocationID( locationId );
            photoDB.setPerson( people );
            photoDB.setDate( newdate );

            mapper.updatePhoto( photoDB );
            session.commit();

            //check date, if different, move the file
            if ( !oldDate.isEqual( newdate) ){
                moveFile( photoDB.getFilename(), oldDate, newdateStr );
                System.out.println( photoDB.getFilename() + " file was moved to "+newdateStr);
            }
        }

    }

    //when a date is updated, the file needs to move to the new date based directory
    public void moveFile(String fileName, LocalDate oldDate, String newDateString ) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedOldDate = oldDate.format( formatter);
        String locationOrigin = DatePathUtil.convertToFilepathOnDisk(formattedOldDate);
        Path sourcePath = Paths.get(locationOrigin+"/"+fileName);

        String locationDestination = DatePathUtil.convertToFilepathOnDisk( newDateString );
        Path targetPath = Paths.get(locationDestination+"/"+fileName);

        // Ensure destination directory exists
        Files.createDirectories(targetPath.getParent());

        // Move the file (overwriting if it already exists at the destination)
        Files.move(sourcePath, targetPath);

    }

}
