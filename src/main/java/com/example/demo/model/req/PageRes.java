package com.example.demo.model.req;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PageRes<T> {
    private List<T> list;
    private Integer page;
    private Integer size;
    private Integer lastPage;
}
