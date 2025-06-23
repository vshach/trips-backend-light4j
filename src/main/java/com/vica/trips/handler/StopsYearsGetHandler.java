package com.vica.trips.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.handler.LightHttpHandler;
import com.networknt.http.HttpStatus;
import com.networknt.http.MediaType;
import com.vica.trips.mappers.MyBatisSqlSessionFactory;
import com.vica.trips.mappers.StopsMapper;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class StopsYearsGetHandler implements LightHttpHandler {


    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // HeaderMap requestHeaders = exchange.getRequestHeaders();
        // Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();
        List<String> listOfYears = loadYearsStopsWereMade();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(listOfYears);

        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        exchange.setStatusCode(HttpStatus.OK.value());
        exchange.getResponseSender().send(responseBody);
    }

    private List<String> loadYearsStopsWereMade() {
        SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StopsMapper mapper
                    = session.getMapper( StopsMapper.class);
            return mapper.getDistinctYears();
        }
    }
}