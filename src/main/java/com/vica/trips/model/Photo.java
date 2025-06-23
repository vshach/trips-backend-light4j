package com.vica.trips.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo  {

    private java.lang.Long id;
    private String dateTaken;
    private Integer locationID;
    private String people;
    private String filepath;

    public Photo () {
    }

    @JsonProperty("id")
    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    @JsonProperty("dateTaken")
    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    @JsonProperty("locationID")
    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    @JsonProperty("people")
    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    @JsonProperty("filepath")
    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Photo Photo = (Photo) o;

        return Objects.equals(id, Photo.id) &&
               Objects.equals(dateTaken, Photo.dateTaken) &&
               Objects.equals(locationID, Photo.locationID) &&
               Objects.equals(people, Photo.people) &&
               Objects.equals(filepath, Photo.filepath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTaken, locationID, people, filepath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Photo {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");        sb.append("    dateTaken: ").append(toIndentedString(dateTaken)).append("\n");        sb.append("    locationID: ").append(toIndentedString(locationID)).append("\n");        sb.append("    people: ").append(toIndentedString(people)).append("\n");        sb.append("    filepath: ").append(toIndentedString(filepath)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
