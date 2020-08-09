package ru.drudenko.dnd5.webapi.impl.spell;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.drudenko.dnd5.webapi.dto.common.FavoriteDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellSearchDto;
import ru.drudenko.dnd5.webapi.dto.user.UserDto;
import ru.drudenko.dnd5.webapi.service.SpellService;
import ru.drudenko.dnd5.webapi.service.UserService;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.SetJoin;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
class SpellServiceImpl implements SpellService {
    private final SpellRepository spellRepository;
    private final SpellFavoriteRepository spellFavoriteRepository;
    private final UserService userService;

    @Transactional
    @Override
    public SpellDto getByName(String name) {
        return SpellMapper.MAPPER.fromEntity(spellRepository.findByNameOrNameEn(name.toUpperCase())
                .orElseThrow(() -> new IllegalArgumentException("Invalid spell name:" + name)));
    }

    @Transactional
    @Override
    public void setFavorite(String name, FavoriteDto favoriteDto) {

        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        UserDto user = userService.findByUsername(username);
        Spell spell = spellRepository.findByNameOrNameEn(name.toUpperCase()).orElseThrow(() -> new IllegalArgumentException("Invalid spell name:" + name));
        SpellFavorite spellFavorite = spellFavoriteRepository.findById(new SpellFavoriteId(user.getCurrentProfile().getId(), spell.getName())).orElseGet(() -> {
            SpellFavorite spellFavorite1 = new SpellFavorite();
            spellFavorite1.setSpellFavoriteId(new SpellFavoriteId(user.getCurrentProfile().getId(), spell.getName()));
            return spellFavorite1;
        });

        spellFavorite.setIsFavorite(favoriteDto.isFavorite());
        spellFavoriteRepository.save(spellFavorite);
    }

    @Transactional
    @Override
    public Page<SpellDto> search(SpellSearchDto filter) {
        UserDto user = StringUtils.isEmpty(filter.getUserName()) ? null : userService.findByUsername(filter.getUserName());

        Pageable pageRequest = PageRequest.of(filter.getPaginationParams().getPage() - 1, filter.getPaginationParams().getSize(), Sort.by(Sort.Direction.ASC, "level", "name"));

        Specification<Spell> spellSpecification = Specification.where((Specification<Spell>) (root, query, criteriaBuilder) -> {


            Predicate predicatePersonIs = criteriaBuilder.isNotNull(root.get("name"));
            Predicate ands = criteriaBuilder.and(predicatePersonIs);

            if (!StringUtils.isEmpty(filter.getSpellClass())) {
                List<String> classes = Arrays.asList(filter.getSpellClass().split(","));
                SetJoin<Spell, SpellClass> tasks = root.joinSet("spellClass");
                ands = criteriaBuilder.and((tasks.get("name").in(classes)));
            }
            if (!StringUtils.isEmpty(filter.getLevel())) {

                List<String> levels = Arrays.asList(filter.getLevel().split(","));
                Predicate predicateLevel = root.get("level").in(levels);
                ands = criteriaBuilder.and(ands, predicateLevel);
            }

            if (!StringUtils.isEmpty(filter.getSchool())) {

                List<String> levels = Arrays.asList(filter.getSchool().split(","));
                Predicate predicateSchool = root.get("school").in(levels);
                ands = criteriaBuilder.and(ands, predicateSchool);
            }


            if (!StringUtils.isEmpty(filter.getName())) {
                Predicate predicateName = criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + filter.getName().toUpperCase() + "%"), criteriaBuilder.like(root.get("nameEn"), "%" + filter.getName().toUpperCase() + "%"));
                ands = criteriaBuilder.and(ands, predicateName);
            }

            if (filter.getFavorite() && user != null) {
                SetJoin<Spell, SpellFavorite> tasks = root.joinSet("spellFavorites");
                ands = criteriaBuilder.and(ands, criteriaBuilder.equal(tasks.get("spellFavoriteId").get("profileId"), user.getCurrentProfile().getId()));
                ands = criteriaBuilder.and(ands, criteriaBuilder.equal(tasks.get("isFavorite"), true));
            }

            return ands;

        });

        return spellRepository.findAll(spellSpecification, pageRequest)
                .map(spell -> {
                    SpellDto dto = SpellMapper.MAPPER.fromEntity(spell);
                    dto.setIsFavorite(spell.getSpellFavorites().stream()
                            .anyMatch(spellFavorite -> user != null && spellFavorite.getSpellFavoriteId().getProfileId().equals(user.getCurrentProfile().getId()) && spellFavorite.getIsFavorite()));
                    return dto;
                });
    }
}
