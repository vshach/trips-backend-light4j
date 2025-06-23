package com.vica.trips.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location  {

    private java.lang.Long id;
    private String name;
    private String latitude;
    private String longitude;
    private String description;

    public Location () {
    }

    @JsonProperty("id")
    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Location Location = (Location) o;

        return Objects.equals(id, Location.id) &&
               Objects.equals(name, Location.name) &&
               Objects.equals(latitude, Location.latitude) &&
               Objects.equals(longitude, Location.longitude) &&
               Objects.equals(description, Location.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latitude, longitude, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Location {\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");        sb.append("    name: ").append(toIndentedString(name)).append("\n");        sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");        sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");        sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
