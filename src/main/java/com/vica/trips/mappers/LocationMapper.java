package com.vica.trips.mappers;

import com.vica.trips.model.Location;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface LocationMapper {
    @Select("SELECT * FROM Locations ORDER BY name")
    List<Location> selectAllLocations();
}
