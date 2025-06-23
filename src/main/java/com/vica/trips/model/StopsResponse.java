package com.vica.trips.model;

import java.util.Arrays;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopsResponse  {

    private java.time.LocalDate date;
    private java.util.List<Stop> listStops;

    public StopsResponse () {
    }

    @JsonProperty("date")
    public java.time.LocalDate getDate() {
        return date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    @JsonProperty("listStops")
    public java.util.List<Stop> getListStops() {
        return listStops;
    }

    public void setListStops(java.util.List<Stop> listStops) {
        this.listStops = listStops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StopsResponse StopsResponse = (StopsResponse) o;

        return Objects.equals(date, StopsResponse.date) &&
               Objects.equals(listStops, StopsResponse.listStops);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, listStops);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class StopsResponse {\n");
        sb.append("    date: ").append(toIndentedString(date)).append("\n");        sb.append("    listStops: ").append(toIndentedString(listStops)).append("\n");
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
