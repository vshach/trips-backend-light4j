package com.vica.trips.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.body.BodyHandler;
import com.networknt.config.Config;

import com.networknt.handler.LightHttpHandler;
import com.networknt.http.HttpMethod;
import com.networknt.http.HttpStatus;
import com.networknt.http.MediaType;
import com.vica.trips.mappers.MyBatisSqlSessionFactory;
import com.vica.trips.mappers.StopsMapper;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HeaderMap;
import io.undertow.util.PathTemplateMatch;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Deque;
import java.util.List;
import java.util.Map;

/**
For more information on how to write business handlers, please check the link below.
https://doc.networknt.com/development/business-handler/rest/
*/
public class StopsDatesByyearYearGetHandler implements LightHttpHandler {

    
    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        // HeaderMap requestHeaders = exchange.getRequestHeaders();
        // Map<String, Deque<String>> queryParameters = exchange.getQueryParameters();
        // Get the year parameter from the path
        String yearStr = exchange.getAttachment(PathTemplateMatch.ATTACHMENT_KEY)
                .getParameters().get("year");
        int year = Integer.parseInt(yearStr);

        // Get the dates for this year
        List<String> dates = loadDatesStopsWereMadeInYear(year);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(dates);

        exchange.getResponseHeaders().add(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        exchange.setStatusCode(HttpStatus.OK.value());
        exchange.getResponseSender().send(responseBody);
    }

    private List<String> loadDatesStopsWereMadeInYear(int year ) {
        SqlSessionFactory sqlSessionFactory = MyBatisSqlSessionFactory.getSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            StopsMapper mapper = session.getMapper(StopsMapper.class);
            return mapper.getDatesByYear( year );
        }
    }
}
