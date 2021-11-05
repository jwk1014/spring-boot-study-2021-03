package com.example.demo.model.req;

import com.example.demo.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

public class RPost {

    @Builder
    @Getter
    public static class ListGetRes {
        private Long id;
        private String title;
        private GetUserRes user;
    }

    @Builder
    @Getter
    public static class GetUserRes {
        private Long id;
        private String name;
    }

    @Builder
    @Getter
    public static class GetRes {
        private Long id;
        private String title;
        @JsonProperty("testContent")
        private String content;
        private GetUserRes user;
    }

    @Getter
    public static class ListGetReq {
        private String query;
        @Positive
        private Integer page;
        @Positive
        @Max(50L)
        private Integer size;
    }

    @Getter
    public static class CreationReq {
        @NotBlank
        @Pattern(regexp = "^[가-힣\\s]+$")
        private String title;
        @NotBlank
        private String content;
        @JsonIgnore
        @Setter
        private User user;
    }

    @Getter
    public static class ModificationReq {
        @JsonIgnore
        @Setter
        private Long id;
        private String title;
        private String content;
        @JsonIgnore
        @Setter
        private User user;
    }
}
