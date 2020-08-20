package ru.drudenko.dnd5.webapi.dto.spell;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.drudenko.dnd5.webapi.dto.common.PaginationParametersDto;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpellSearchDto {
    private String name;
    @JsonProperty("class")
    private String spellClass;
    private String level;
    private String school;
    private Boolean favorite = false;
    private String userName;
    private PaginationParametersDto paginationParams = new PaginationParametersDto();
}
