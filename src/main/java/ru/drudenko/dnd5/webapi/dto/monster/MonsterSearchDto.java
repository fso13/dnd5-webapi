package ru.drudenko.dnd5.webapi.dto.monster;

import lombok.Builder;
import lombok.Data;
import ru.drudenko.dnd5.webapi.dto.common.PaginationParametersDto;

@Builder
@Data
public class MonsterSearchDto {
    private String name;
    private String cr;
    private String bioms;
    private String type;
    private String order;
    private Boolean favorite;
    private String userName;

    private PaginationParametersDto paginationParams;
}
