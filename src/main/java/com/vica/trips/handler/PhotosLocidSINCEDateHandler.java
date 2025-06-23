package com.vica.trips.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.networknt.handler.LightHttpHandler;
import com.networknt.http.HttpStatus;
import com.networknt.http.MediaType;
import com.vica.trips.mappers.MyBatisSqlSessionFactory;
import com.vica.trips.mappers.PhotoMapper;
import com.vica.trips.model.Photo;
import com.vica.trips.utils.DatePathUtil;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
/*GET http://<your_host>/api/v1/photos/14/2020-01-17
gets information about photos at location id 14 SINCE the date of 2020-01-17 and onwards.
*/
public class PhotosLocidSINCEDateHandler implements LightHttpHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // HeaderMap requestHeaders = exchange.getRequestHeaders();
        // Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();
        String path = exchange.getRequestPath();
        String[] parts = path.split("/");


        // Extract locid and date
        String locidStr = parts[parts.length-2];
        String dateStr = parts[parts.length-1];
        System.out.println("locid="+ locidStr+" date="+dateStr);
        /*if ("".equals(locidStr.trim()) || ".isEmpty(dateStr)) {
            exchange.setStatusCode(400);
            exchange.getResponseSender().send("Missing path parameters: locid or date.");
            return;
        }*/

        // Optionally, convert locid to int
        int locid = Integer.parseInt(locidStr);
        String baseURL = getBaseURL( exchange);
        List<Photo> photoList = loadPhotosSince( locid, dateStr, baseURL);
        String json = objectMapper.writeValueAsString( photoList );

        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        exchange.setStatusCode(HttpStatus.OK.value());
        exchange.getResponseSender().send(json);
    }

    private String getBaseURL(HttpServerExchange exchange) {
        return exchange.getRequestScheme() + "://" + exchange.getHostName() + ":" + exchange.getHostPort();
    }

    private List<Photo> loadPhotosSince( int locationId, String date, String baseURL)
    {
        SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PhotoMapper mapper = session.getMapper(PhotoMapper.class);
            List<Photo> listOfPhotos = mapper.findPhotosSince( locationId, date);

            for (Photo pic : listOfPhotos) {
                LocalDate dateOfPic = LocalDate.parse(pic.getDateTaken(), DateTimeFormatter.ISO_LOCAL_DATE);
                String url = baseURL +"/photos?filepath="+DatePathUtil.splitDateIntoFolders( dateOfPic ) +"/"+ pic.getFilepath();
                pic.setFilepath( url );
                System.out.println( pic.getFilepath() );
            }

            return listOfPhotos;
        }
    }
}
