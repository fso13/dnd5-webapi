package ru.drudenko.dnd5.webapi.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Converter
public class StringListPersistConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return String.join(SPLIT_CHAR, Optional.ofNullable(stringList).orElse(new ArrayList<>()));
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return Optional.ofNullable(string).map(s -> Arrays.asList(s.split(SPLIT_CHAR))).orElse(new ArrayList<>());
    }
}
