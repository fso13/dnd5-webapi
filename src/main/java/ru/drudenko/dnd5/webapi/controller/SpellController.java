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
import ru.drudenko.dnd5.webapi.dto.spell.SpellClassDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellLevelDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellSchoolDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellSearchDto;
import ru.drudenko.dnd5.webapi.service.SpellService;
import ru.drudenko.dnd5.webapi.service.UserService;
import ru.drudenko.dnd5.webapi.utils.SecurityHelper;
import ru.drudenko.dnd5.webapi.utils.StringNullEditor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("spells")
@RequiredArgsConstructor
public class SpellController {
    public static final List<String> SCHOOLS_STREAM = Arrays.asList("Очарование", "Воплощение", "Преобразование", "Некромантия", "Ограждение", "Проявление", "Иллюзия", "Прорицание", "Вызов");
    public static final List<String> CLASSES_STREAM = Arrays.asList("Жрец", "Друид", "Бард", "Паладин", "Рейнджер", "Чародей", "Колдун", "Волшебник");
    public static final List<String> LEVELS_SPELL_STREAM = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private final SpellService spellService;
    private final UserService userService;

    @InitBinder
    public void allowEmptyDateBinding(WebDataBinder binder) {
        // tell spring to set empty values as null instead of empty string.
        binder.registerCustomEditor(String.class, new StringNullEditor(true));
    }

    @ApiOperation(value = "Добавить в закладки")
    @PatchMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void setFavoriteSpells(@PathVariable("id") String id, @RequestBody FavoriteDto favoriteDto) {
        spellService.setFavorite(id, favoriteDto);
    }

    @GetMapping("/{id}")
    public String getSpells(@PathVariable("id") String id, Model model) {
        SpellDto spell = spellService.getByName(id);
        model.addAttribute("spell", spell);
        SecurityHelper.getUsernameAndAddProfilesAttributes(model, userService);

        return "get-spell";
    }

    @GetMapping
    public String getAllSpells(Model model,
                               @ModelAttribute SpellSearchDto spellSearchDto,
                               @ModelAttribute PaginationParametersDto paginationParams) {

        Optional.ofNullable(spellSearchDto.getLevel()).ifPresent(level -> model.addAttribute("level", level));
        Optional.ofNullable(spellSearchDto.getName()).ifPresent(name -> model.addAttribute("name", name));
        Optional.ofNullable(spellSearchDto.getSpellClass()).ifPresent(spellClass -> model.addAttribute("spellClass", spellClass));
        Optional.ofNullable(spellSearchDto.getSchool()).ifPresent(school -> model.addAttribute("school", school));
        model.addAttribute("favorite", spellSearchDto.getFavorite());

        addSpellLevelAttributes(model, spellSearchDto.getLevel());
        addSpellClassAttributes(model, spellSearchDto.getSpellClass());
        addSpellSchoolsAttributes(model, spellSearchDto.getSchool());

        String username = SecurityHelper.getUsernameAndAddProfilesAttributes(model, userService);

        spellSearchDto.setUserName(username);
        spellSearchDto.setPaginationParams(paginationParams);

        Page<SpellDto> spells = spellService.search(spellSearchDto);
        model.addAttribute("spells", spells);

        int totalPages = spells.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "get-spells";
    }

    private void addSpellClassAttributes(Model model, String spellClass) {
        model.addAttribute("classes", CLASSES_STREAM.stream()
                .map(ss -> SpellClassDto.builder().name(ss).selected(Optional.ofNullable(spellClass).filter(s2 -> s2.contains(ss)).isPresent()).build())
                .collect(Collectors.toList()));
    }

    private void addSpellSchoolsAttributes(Model model, String school) {
        model.addAttribute("schools", SCHOOLS_STREAM.stream()
                .map(ss -> SpellSchoolDto.builder().name(ss).selected(Optional.ofNullable(school).filter(s2 -> s2.contains(ss)).isPresent()).build())
                .collect(Collectors.toList()));
    }

    private void addSpellLevelAttributes(Model model, String levels) {
        model.addAttribute("levels", LEVELS_SPELL_STREAM.stream()
                .map(ss -> SpellLevelDto.builder().name(ss).selected(Optional.ofNullable(levels).filter(s2 -> s2.contains(ss)).isPresent()).build())
                .collect(Collectors.toList()));
    }
}
