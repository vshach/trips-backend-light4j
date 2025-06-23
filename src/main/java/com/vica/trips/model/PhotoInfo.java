package com.vica.trips.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhotoInfo  {

    private String dateTaken;
    private Integer locationID;
    private String people;

    public PhotoInfo () {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhotoInfo PhotoInfo = (PhotoInfo) o;

        return Objects.equals(dateTaken, PhotoInfo.dateTaken) &&
               Objects.equals(locationID, PhotoInfo.locationID) &&
               Objects.equals(people, PhotoInfo.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTaken, locationID, people);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PhotoInfo {\n");
        sb.append("    dateTaken: ").append(toIndentedString(dateTaken)).append("\n");        sb.append("    locationID: ").append(toIndentedString(locationID)).append("\n");        sb.append("    people: ").append(toIndentedString(people)).append("\n");
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
