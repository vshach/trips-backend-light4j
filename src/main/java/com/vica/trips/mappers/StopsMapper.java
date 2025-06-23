package com.vica.trips.mappers;

import com.vica.trips.model.Stop;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StopsMapper {

    @Select("SELECT DISTINCT YEAR(date) AS year_only "+
            "FROM STOPS "+
            "order by year_only")
    List<String> getDistinctYears();

    @Select("SELECT DISTINCT date FROM Stops " +
            "WHERE date >= CAST(CONCAT(#{year}, '-01-01') AS DATE) " +
            "AND date < CAST(CONCAT(#{year} + 1, '-01-01') AS DATE)")
    List<String> getDatesByYear(@Param("year") int year);

    @Select("SELECT l.id as id, date, name, latitude, longitude, description, dayorder "+
    "from Stops s, LOCATIONS l "+
    "where s.locID = l.id "+
    "and date = #{date}" )
    List<Stop> getStopsByDate(@Param("date") String date);
    
}
