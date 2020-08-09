package ru.drudenko.dnd5.webapi.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {
    @JsonProperty("isFavorite")
    private boolean isFavorite;
}
