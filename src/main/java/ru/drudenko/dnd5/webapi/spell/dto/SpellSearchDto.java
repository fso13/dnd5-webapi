package ru.drudenko.dnd5.webapi.spell.dto;

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
public class SpellSearchDto {
    private String name;
    private String spellClass;
    private String level;
    private String school;
    private Boolean favorite = false;
    private String userName;
    private PaginationParametersDto paginationParams = new PaginationParametersDto();
}
