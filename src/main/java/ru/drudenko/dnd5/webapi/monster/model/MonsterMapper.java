package ru.drudenko.dnd5.webapi.monster.model;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.drudenko.dnd5.webapi.monster.dto.MonsterActionDto;
import ru.drudenko.dnd5.webapi.monster.dto.MonsterDto;
import ru.drudenko.dnd5.webapi.monster.dto.MonsterTraitDto;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper
public interface MonsterMapper {

    Map<String, String> exps = Stream.of(new String[][]{
            {"0", "0 - 10"},
            {"1/8", "25"},
            {"1/4", "50"},
            {"1/2", "100"},
            {"1", "200"},
            {"2", "450"},
            {"3", "700"},
            {"4", "1100"},
            {"5", "1800"},
            {"6", "2300"},
            {"7", "2900"},
            {"8", "3900"},
            {"9", "5000"},
            {"10", "5900"},
            {"11", "7200"},
            {"12", "8400"},
            {"13", "10000"},
            {"14", "11500"},
            {"15", "13000"},
            {"16", "15000"},
            {"17", "18000"},
            {"18", "20000"},
            {"19", "22000"},
            {"20", "25000"},
            {"21", "33000"},
            {"22", "41000"},
            {"23", "50000"},
            {"24", "62000"},
            {"25", "75000"},
            {"26", "90000"},
            {"27", "105000"},
            {"28", "120000"},
            {"29", "135000"},
            {"30", "155000"}
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    MonsterMapper MAPPER = Mappers.getMapper(MonsterMapper.class);

    @Mappings({
            @Mapping(expression = "java(java.util.Optional.ofNullable(monster.getBioms()).map(s -> java.util.Arrays.asList(s.split(\",\"))).orElse(new java.util.ArrayList<>()))", target = "bioms"),
            @Mapping(expression = "java(monster.getStr() + \"(\" + (int) java.lang.Math.floor((Double.valueOf(monster.getStr()) - 10) / 2) + \")\")", target = "str"),
            @Mapping(expression = "java(monster.getDex() + \"(\" + (int) java.lang.Math.floor((Double.valueOf(monster.getDex()) - 10) / 2) + \")\")", target = "dex"),
            @Mapping(expression = "java(monster.getCon() + \"(\" + (int) java.lang.Math.floor((Double.valueOf(monster.getCon()) - 10) / 2) + \")\")", target = "con"),
            @Mapping(expression = "java(monster.getIntilect() + \"(\" + (int) java.lang.Math.floor((Double.valueOf(monster.getIntilect()) - 10) / 2) + \")\")", target = "intilect"),
            @Mapping(expression = "java(monster.getWis() + \"(\" + (int) java.lang.Math.floor((Double.valueOf(monster.getWis()) - 10) / 2) + \")\")", target = "wis"),
            @Mapping(expression = "java(monster.getCha() + \"(\" + (int) java.lang.Math.floor((Double.valueOf(monster.getCha()) - 10) / 2) + \")\")", target = "cha"),
    })
    MonsterDto fromEntity(Monster monster);

    @AfterMapping
    default void enrichDTOWithFuelType(Monster monster, @MappingTarget MonsterDto dto) {
        try {
            dto.setExp(exps.get(dto.getCr()));
        } catch (Exception e) {
            System.out.println(dto.getName());
            e.printStackTrace();
        }
        try {
            String name;
            int indexOf = dto.getName().indexOf("(");
            if (indexOf >= 0) {
                name = dto.getName().substring(indexOf + 1, dto.getName().indexOf(")")).trim().toUpperCase().replace(" ", "_") + ".jpg";
            } else {
                indexOf = dto.getName().indexOf("[");
                name = dto.getName().substring(indexOf + 1, dto.getName().indexOf("]")).trim().toUpperCase().replace(" ", "_") + ".jpg";
            }
            dto.setImgStaticUrl("/img/" + name);
        } catch (Exception e) {
            System.out.println(dto.getName());
            e.printStackTrace();
        }
    }

    MonsterTraitDto fromEntity(MonsterTrait trait);

    MonsterActionDto fromEntity(MonsterAction action);

}
