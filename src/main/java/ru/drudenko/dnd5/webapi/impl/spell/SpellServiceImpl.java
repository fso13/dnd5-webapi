package ru.drudenko.dnd5.webapi.impl.spell;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.drudenko.dnd5.webapi.dto.common.FavoriteDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellDto;
import ru.drudenko.dnd5.webapi.dto.spell.SpellSearchDto;
import ru.drudenko.dnd5.webapi.service.SpellService;
import ru.drudenko.dnd5.webapi.service.UserService;

import java.util.Arrays;

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

        var username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        var user = userService.findByUsername(username);
        var spell = spellRepository.findByNameOrNameEn(name.toUpperCase()).orElseThrow(() -> new IllegalArgumentException("Invalid spell name:" + name));
        var spellFavorite = spellFavoriteRepository.findById(new SpellFavoriteId(user.getCurrentProfile().getId(), spell.getName())).orElseGet(() -> {
            var spellFavorite1 = new SpellFavorite();
            spellFavorite1.setSpellFavoriteId(new SpellFavoriteId(user.getCurrentProfile().getId(), spell.getName()));
            return spellFavorite1;
        });

        spellFavorite.setIsFavorite(favoriteDto.isFavorite());
        spellFavoriteRepository.save(spellFavorite);
    }

    @Transactional
    @Override
    public Page<SpellDto> search(SpellSearchDto filter) {
        var user = StringUtils.isEmpty(filter.getUserName()) ? null : userService.findByUsername(filter.getUserName());

        var pageRequest = PageRequest.of(filter.getPaginationParams().getPage() - 1, filter.getPaginationParams().getSize(), Sort.by(Sort.Direction.ASC, "level", "name"));

        var spellSpecification = Specification.where((Specification<Spell>) (root, query, criteriaBuilder) -> {


            var predicatePersonIs = criteriaBuilder.isNotNull(root.get("name"));
            var ands = criteriaBuilder.and(predicatePersonIs);

            if (!StringUtils.isEmpty(filter.getSpellClass())) {
                var classes = Arrays.asList(filter.getSpellClass().split(","));
                var tasks = root.joinSet("spellClass");
                ands = criteriaBuilder.and((tasks.get("name").in(classes)));
            }
            if (!StringUtils.isEmpty(filter.getLevel())) {

                var levels = Arrays.asList(filter.getLevel().split(","));
                var predicateLevel = root.get("level").in(levels);
                ands = criteriaBuilder.and(ands, predicateLevel);
            }

            if (!StringUtils.isEmpty(filter.getSchool())) {

                var levels = Arrays.asList(filter.getSchool().split(","));
                var predicateSchool = root.get("school").in(levels);
                ands = criteriaBuilder.and(ands, predicateSchool);
            }


            if (!StringUtils.isEmpty(filter.getName())) {
                var predicateName = criteriaBuilder.or(criteriaBuilder.like(root.get("name"), "%" + filter.getName().toUpperCase() + "%"), criteriaBuilder.like(root.get("nameEn"), "%" + filter.getName().toUpperCase() + "%"));
                ands = criteriaBuilder.and(ands, predicateName);
            }

            if (filter.getFavorite() && user != null) {
                var tasks = root.joinSet("spellFavorites");
                ands = criteriaBuilder.and(ands, criteriaBuilder.equal(tasks.get("spellFavoriteId").get("profileId"), user.getCurrentProfile().getId()));
                ands = criteriaBuilder.and(ands, criteriaBuilder.equal(tasks.get("isFavorite"), true));
            }

            return ands;

        });

        return spellRepository.findAll(spellSpecification, pageRequest)
                .map(spell -> {
                    var dto = SpellMapper.MAPPER.fromEntity(spell);
                    dto.setIsFavorite(spell.getSpellFavorites().stream()
                            .anyMatch(spellFavorite -> user != null && spellFavorite.getSpellFavoriteId().getProfileId().equals(user.getCurrentProfile().getId()) && spellFavorite.getIsFavorite()));
                    return dto;
                });
    }
}
