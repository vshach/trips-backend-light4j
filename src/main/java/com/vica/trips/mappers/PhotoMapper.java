package com.vica.trips.mappers;

import com.vica.trips.databaseobj.PhotoDB;
import com.vica.trips.model.Photo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PhotoMapper {

    @Select("SELECT * FROM photos WHERE locationID = #{locId} AND date = #{date}")
    @Results({
            @Result(property = "filepath", column = "filename"),  // Only map the column that doesn't match
            @Result(property = "dateTaken", column = "date"),
            @Result(property = "people", column = "person")
    })
    List<Photo> findPhotosExact(@Param("locId") int locId, @Param("date") String date);

    @Select("select * from Photos where locationID = #{locId} and date > #{date}")
    @Results({
            @Result(property = "filepath", column = "filename"),  // Only map the column that doesn't match
            @Result(property = "dateTaken", column = "date"),
            @Result(property = "people", column = "person")
    })
    List<Photo> findPhotosSince(@Param("locId") int locId, @Param("date") String date);

    @Insert("INSERT INTO Photos (date, locationID, person, filename) " +
            "VALUES (#{date}, #{locationID}, #{person}, #{filename})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPhotoRecord(PhotoDB record);

    @Select("SELECT * FROM Photos WHERE id = #{id}")
    PhotoDB getPhotoInfoById( int id );

    @Update("UPDATE photos " +
            "SET date = #{date}, " +
            "    locationID = #{locationID}, " +
            "    person = #{person} " +
            "WHERE id = #{id}")
    void updatePhoto(PhotoDB photo);
}
