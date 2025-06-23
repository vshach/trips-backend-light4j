package com.vica.trips.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;

import com.networknt.handler.LightHttpHandler;
import com.networknt.http.HttpMethod;
import com.networknt.http.HttpStatus;
import com.networknt.http.MediaType;
import com.vica.trips.mappers.MyBatisSqlSessionFactory;
import com.vica.trips.mappers.PhotoMapper;
import com.vica.trips.mappers.StopsMapper;
import com.vica.trips.model.Stop;
import com.vica.trips.model.StopsResponse;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HeaderMap;
import io.undertow.util.PathTemplateMatch;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.time.LocalDate;
import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class StopsBydateDateGetHandler implements LightHttpHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // HeaderMap requestHeaders = exchange.getRequestHeaders();
        // Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();

        //Read path parameter
        String dateStr = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY)
                .getParameters().get("date");

        LocalDate date = LocalDate.parse(dateStr);

        SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StopsMapper mapper
                    = session.getMapper(StopsMapper.class);
            List<Stop> listOfStops = mapper.getStopsByDate( dateStr );

            //prepare response
            StopsResponse response = new StopsResponse();
            response.setDate( date );
            response.setListStops( listOfStops );

            String json = objectMapper.writeValueAsString( response );
            exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            exchange.setStatusCode(HttpStatus.OK.value());
            exchange.getResponseSender().send(json);
        }

    }
}
