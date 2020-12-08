package ru.drudenko.dnd5.webapi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttributeDto {
    private String name;
    private boolean selected;
}
