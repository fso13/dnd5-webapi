package ru.drudenko.dnd5.webapi.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.drudenko.dnd5.webapi.dto.common.FavoriteDto;
import ru.drudenko.dnd5.webapi.dto.common.PaginationParametersDto;
import ru.drudenko.dnd5.webapi.dto.monster.MonsterBiomDto;
import ru.drudenko.dnd5.webapi.dto.monster.MonsterCrDto;
import ru.drudenko.dnd5.webapi.dto.monster.MonsterDto;
import ru.drudenko.dnd5.webapi.dto.monster.MonsterSearchDto;
import ru.drudenko.dnd5.webapi.dto.monster.MonsterTypeDto;
import ru.drudenko.dnd5.webapi.service.MonsterService;
import ru.drudenko.dnd5.webapi.service.UserService;
import ru.drudenko.dnd5.webapi.utils.SecurityHelper;
import ru.drudenko.dnd5.webapi.utils.StringNullEditor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("monsters")
@RequiredArgsConstructor
public class MonsterController {
    public static final List<String> CRS_STREAM = Arrays.asList("0", "1/8", "1/4", "1/2", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "30");
    public static final List<String> MONSTER_TYPES_STREAM = Arrays.asList("гуманоид", "аберрация", "монстр", "нежить", "зверь", "великан", "конструкт", "небожитель", "слизь", "дракон", "растение", "демон", "рой крошечных зверей", "элементаль", "фея", "исчадие");
    public static final List<String> BIOMS_STREAM = Arrays.asList("Холмы", "Побережье", "Подземье", "Болота", "Лес", "Заполярье", "Равнина", "Город", "Горы", "Пустыня");
    private final MonsterService monsterService;
    private final UserService userService;

    @InitBinder
    public void allowEmptyDateBinding(WebDataBinder binder) {
        // tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor(String.class, new StringNullEditor(true));
    }

    @ApiOperation(value = "Добавить в закладки")
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void setFavoriteMonster(@PathVariable("id") String id, @RequestBody FavoriteDto favoriteDto) {
        monsterService.setFavorite(id, favoriteDto);
    }

    @GetMapping("/{id}")
    public String getSpells(@PathVariable("id") String id, Model model) {
        MonsterDto monster = monsterService.getByName(id);
        model.addAttribute("monster", monster);
        SecurityHelper.getUsernameAndAddProfilesAttributes(model, userService);
        return "get-monster";
    }

    @GetMapping
    public String getAllSpells(Model model,
                               @ModelAttribute MonsterSearchDto monsterSearchDto,
                               @ModelAttribute PaginationParametersDto paginationParams) {

        String username = SecurityHelper.getUsernameAndAddProfilesAttributes(model, userService);
        monsterSearchDto.setUserName(username);
        monsterSearchDto.setPaginationParams(paginationParams);
        Page<MonsterDto> monsters = monsterService.search(monsterSearchDto);

        Optional.ofNullable(monsterSearchDto.getName()).ifPresent(name -> model.addAttribute("name", name));
        Optional.ofNullable(monsterSearchDto.getCr()).ifPresent(cr -> model.addAttribute("cr", cr));
        Optional.ofNullable(monsterSearchDto.getBiom()).ifPresent(biom -> model.addAttribute("biom", biom));
        Optional.ofNullable(monsterSearchDto.getType()).ifPresent(type -> model.addAttribute("type", type));
        Optional.ofNullable(monsterSearchDto.getOrder()).ifPresent(order -> model.addAttribute("order", order));

        model.addAttribute("favorite", monsterSearchDto.getFavorite());
        model.addAttribute("monsters", monsters);

        addMonsterBiomsAttributes(model, monsterSearchDto.getBiom());
        addMonsterCrAttributes(model, monsterSearchDto.getCr());
        addMonsterTypeAttributes(model, monsterSearchDto.getType());

        int totalPages = monsters.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-monsters";
    }

    private void addMonsterCrAttributes(Model model, String crs) {
        List<String> strings = Optional.ofNullable(crs).map(s -> Arrays.asList(s.split(","))).orElse(Collections.emptyList());
        model.addAttribute("crs", CRS_STREAM.stream()
                .map(ss -> MonsterCrDto.builder().name(ss).selected(Optional.of(strings).filter(s2 -> s2.contains(ss)).isPresent()).build()).collect(Collectors.toList()));
    }

    private void addMonsterTypeAttributes(Model model, String types) {

        model.addAttribute("types", MONSTER_TYPES_STREAM.stream()
                .map(ss -> MonsterTypeDto.builder().name(ss).selected(Optional.ofNullable(types).filter(s2 -> s2.contains(ss)).isPresent()).build()).collect(Collectors.toList()));
    }

    private void addMonsterBiomsAttributes(Model model, String bioms) {

        model.addAttribute("bioms", BIOMS_STREAM.stream()
                .map(ss -> MonsterBiomDto.builder().name(ss).selected(Optional.ofNullable(bioms).filter(s2 -> s2.contains(ss)).isPresent()).build()).collect(Collectors.toList()));
    }
}
