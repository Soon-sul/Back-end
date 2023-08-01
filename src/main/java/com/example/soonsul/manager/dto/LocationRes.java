package com.example.soonsul.manager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationRes {

    @JsonProperty("documents")
    public Document[] documents;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class Document{
        @JsonProperty("x")
        public Double longitude;

        @JsonProperty("y")
        public Double latitude;
    }
}
