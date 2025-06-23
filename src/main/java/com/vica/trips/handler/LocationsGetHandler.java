package com.vica.trips.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;

import com.networknt.handler.LightHttpHandler;
import com.networknt.http.HttpMethod;
import com.networknt.http.HttpStatus;
import com.networknt.http.MediaType;
import com.vica.trips.mappers.LocationMapper;
import com.vica.trips.mappers.MyBatisSqlSessionFactory;
import com.vica.trips.model.Location;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HeaderMap;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class LocationsGetHandler implements LightHttpHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // HeaderMap requestHeaders = exchange.getRequestHeaders();
        // Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();

        List<Location> locations = loadLocations();
        String json = objectMapper.writeValueAsString(locations);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(json);
    }

    private List<Location> loadLocations()
    {
        SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            LocationMapper mapper
                    = session.getMapper(LocationMapper.class);
            List<Location> locations = mapper.selectAllLocations();

            //locations.sort(Comparator.comparing(Location::getName));

            for (Location loc : locations) {
                System.out.println(loc.getName());
            }

            return locations;
        }
    }
    /*private void bla()
    {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            PhotoMapper mapper = session.getMapper(PhotoMapper.class);
            Photo photo = mapper.selectPhoto(11, "2011-01-01");
            // do something with 'photo'
        }

    }*/
}
