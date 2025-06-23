package com.vica.trips.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileUpload  {

    private byte[] photo;
    private String people;
    private String locationID;
    private String date;

    public FileUpload () {
    }

    @JsonProperty("photo")
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @JsonProperty("people")
    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    @JsonProperty("locationID")
    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileUpload FileUpload = (FileUpload) o;

        return Arrays.equals(photo, FileUpload.photo) &&
               Objects.equals(people, FileUpload.people) &&
               Objects.equals(locationID, FileUpload.locationID) &&
               Objects.equals(date, FileUpload.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(photo), people, locationID, date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FileUpload {\n");
        sb.append("    photo: ").append(toIndentedString(photo)).append("\n");        sb.append("    people: ").append(toIndentedString(people)).append("\n");        sb.append("    locationID: ").append(toIndentedString(locationID)).append("\n");        sb.append("    date: ").append(toIndentedString(date)).append("\n");
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
