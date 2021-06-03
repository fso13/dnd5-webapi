package ru.drudenko.dnd5.webapi.monster.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MonsterDto {
    private String name;
    private String pdfName;
    private String lineHeight = "1.2";
    private String fiction;
    private String pdfFiction;
    private String size;
    private String type;
    private String alignment;
    private String ac;
    private String pdfAc;
    private String hp;
    private String speed;
    private String str;
    private String dex;
    private String con;
    private String intilect;
    private String wis;
    private String cha;
    private String passive;
    private List<String> languages;
    private String cr;
    private String exp;
    private String senses;
    private List<String> skill;
    private List<MonsterTraitDto> monsterTrait = new ArrayList<>();
    private List<MonsterActionDto> monsterAction = new ArrayList<>();
    private List<String> bioms = new ArrayList<>();

    private String imgStaticUrl;
    private Boolean isFavorite;

}
