package ru.drudenko.dnd5.webapi.monster.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.drudenko.dnd5.webapi.common.dto.FavoriteDto;
import ru.drudenko.dnd5.webapi.monster.MonsterService;
import ru.drudenko.dnd5.webapi.monster.dto.MonsterDto;
import ru.drudenko.dnd5.webapi.monster.dto.MonsterSearchDto;
import ru.drudenko.dnd5.webapi.monster.model.Monster;
import ru.drudenko.dnd5.webapi.monster.model.MonsterFavorite;
import ru.drudenko.dnd5.webapi.monster.model.MonsterFavoriteId;
import ru.drudenko.dnd5.webapi.monster.model.MonsterFavoriteRepository;
import ru.drudenko.dnd5.webapi.monster.model.MonsterMapper;
import ru.drudenko.dnd5.webapi.monster.model.MonsterRepository;
import ru.drudenko.dnd5.webapi.profile.UserService;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class MonsterServiceImpl implements MonsterService {

    private static final String[] cr = {"0", "1/8", "1/4", "1/2", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "30"};
    private final MonsterRepository monsterRepository;
    private final MonsterFavoriteRepository monsterFavoriteRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    @Override
    public MonsterDto getByName(String name) {
        return Optional.ofNullable(MonsterMapper.MAPPER.fromEntity(monsterRepository.getOne(name))).orElse(null);
    }

    @Transactional
    @Override
    public void setFavorite(String name, FavoriteDto favoriteDto) {
        var username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        var user = userService.findByUsername(username);
        var monster = monsterRepository.getOne(name);
        var monsterFavorite = monsterFavoriteRepository.findById(new MonsterFavoriteId(user.getCurrentProfile().getId(), monster.getName())).orElseGet(() -> {
            var monsterFavorite1 = new MonsterFavorite();
            monsterFavorite1.setMonsterFavoriteId(new MonsterFavoriteId(user.getCurrentProfile().getId(), monster.getName()));
            return monsterFavorite1;
        });

        monsterFavorite.setIsFavorite(favoriteDto.isFavorite());
        monsterFavoriteRepository.save(monsterFavorite);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<MonsterDto> search(MonsterSearchDto filter) {

        var user = StringUtils.isEmpty(filter.getUserName()) ? null : userService.findByUsername(filter.getUserName());


        var pageRequest = PageRequest.of(filter.getPaginationParams().getPage() - 1, filter.getPaginationParams().getSize(), Sort.by(Sort.Direction.ASC, Optional.ofNullable(filter.getOrder()).orElse("name")));

        var monsterSpecification = Specification.where((Specification<Monster>) (root, query, criteriaBuilder) -> {

            var predicatePersonIs = criteriaBuilder.isNotNull(root.get("name"));
            var ands = criteriaBuilder.and(predicatePersonIs);

            if (!StringUtils.isEmpty(filter.getBiom())) {
                var predicate = root.get("bioms").in(Arrays.asList(filter.getBiom().split(",")));
                ands = criteriaBuilder.and(ands, predicate);
            }

            if (!StringUtils.isEmpty(filter.getCr())) {
                var predicateCr = root.get("cr").in(Arrays.asList(filter.getCr().split(",")));
                ands = criteriaBuilder.and(ands, predicateCr);
            }

            if (!StringUtils.isEmpty(filter.getType())) {
                var predicateType = root.get("type").in(Arrays.asList(filter.getType().split(",")));
                ands = criteriaBuilder.and(ands, predicateType);
            }
            if (!StringUtils.isEmpty(filter.getName())) {
                var predicateName = criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + filter.getName().toUpperCase() + "%");
                ands = criteriaBuilder.and(ands, predicateName);
            }

            if (filter.getFavorite() && user != null) {
                var tasks = root.joinSet("monsterFavorites");
                ands = criteriaBuilder.and(ands, criteriaBuilder.equal(tasks.get("monsterFavoriteId").get("profileId"), user.getCurrentProfile().getId()));
                ands = criteriaBuilder.and(ands, criteriaBuilder.equal(tasks.get("isFavorite"), true));
            }
            return ands;

        });

        return monsterRepository.findAll(monsterSpecification, pageRequest)
                .map(monster -> {
                    var dto = MonsterMapper.MAPPER.fromEntity(monster);
                    dto.setIsFavorite(monster.getMonsterFavorites().stream()
                            .anyMatch(monsterFavorite -> user != null && monsterFavorite.getIsFavorite() && monsterFavorite.getMonsterFavoriteId().getProfileId().equals(user.getCurrentProfile().getId())));
                    return dto;
                });

    }
}
