package ru.drudenko.dnd5.webapi.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginationParametersDto implements Serializable {

    public static final int DEFAULT_PAGE_SIZE = 50;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    private int size = DEFAULT_PAGE_SIZE;
    private int page = DEFAULT_PAGE_NUMBER;
}
