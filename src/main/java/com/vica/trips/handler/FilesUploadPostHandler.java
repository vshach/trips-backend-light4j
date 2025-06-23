package com.vica.trips.handler;

import com.networknt.handler.LightHttpHandler;
import com.vica.trips.databaseobj.PhotoDB;
import com.vica.trips.mappers.MyBatisSqlSessionFactory;
import com.vica.trips.mappers.PhotoMapper;
import com.vica.trips.utils.DatePathUtil;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import io.undertow.util.Headers;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class FilesUploadPostHandler implements LightHttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // HeaderMap requestHeaders = exchange.getRequestHeaders();
        // Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();

        FormData formData = exchange.getAttachment(FormDataParser.FORM_DATA);
        //above way of reading this relyes on BodyHandler or FormParserHandler
        //being in the chain in the handler.yml
        String date = formData.getFirst("date").getValue();
        FormData.FormValue fileValue = formData.getFirst("photo");

        writeFile( fileValue, date );
        saveFileMetaData( formData );

        // Respond
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send("{\"status\":\"upload successful\"}");
    }

    private void saveFileMetaData( FormData formData){

        // Metadata fields
        String people = formData.getFirst("people").getValue();
        String locationID = formData.getFirst("locationID").getValue();
        String date = formData.getFirst("date").getValue();
        FormData.FormValue fileValue = formData.getFirst("photo");
        String fileName = fileValue.getFileName();

        // Log for debug
        System.out.println("file name: "+fileName );
        System.out.println("People: " + people);
        System.out.println("Location ID: " + locationID);
        System.out.println("Date: " + date);

        PhotoDB record = new PhotoDB();
        LocalDate dateObj = LocalDate.parse(date);
        record.setDate( dateObj );
        record.setPerson(people);
        record.setFilename( fileName );
        record.setLocationID( Integer.parseInt(locationID ));
        SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PhotoMapper mapper
                    = session.getMapper(PhotoMapper.class);
            mapper.insertPhotoRecord( record );
            System.out.println("Inserted with ID: " + record.getId());
            session.commit();
        }

    }

    private void writeFile( FormData.FormValue fileValue, String dateString){
        if (fileValue != null && fileValue.isFileItem()) {
            try (InputStream inputStream = fileValue.getFileItem().getInputStream()) {

                //transform the date into full path
                String fullPath = DatePathUtil.convertToFilepathOnDisk( dateString );

                // Make sure the directorIES along the path exist
                Files.createDirectories(Paths.get(fullPath).toAbsolutePath().normalize());

                String originalFilename = fileValue.getFileName();
                File savedFile = new File(fullPath, originalFilename);

                // Save to disk
                Files.copy(inputStream, savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File saved to: " + savedFile.getAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
