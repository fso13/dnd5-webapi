package ru.drudenko.dnd5.webapi.dto.spell;

import lombok.Builder;
import lombok.Data;
import ru.drudenko.dnd5.webapi.dto.common.PaginationParametersDto;

@Builder
@Data
public class SpellSearchDto {
    private String name;
    private String spellClass;
    private String level;
    private String school;
    private Boolean favorite;
    private String userName;
    private PaginationParametersDto paginationParams;
}
