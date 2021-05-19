package ru.drudenko.dnd5.webapi.monster.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.drudenko.dnd5.webapi.common.dto.PaginationParametersDto;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonsterSearchDto {
    private String name;
    private String cr;
    private String biom;
    private String type;
    private String order;
    private Boolean favorite = false;
    private String userName;

    private PaginationParametersDto paginationParams = new PaginationParametersDto();
}
