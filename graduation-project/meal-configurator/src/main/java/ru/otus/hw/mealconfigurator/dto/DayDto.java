package ru.otus.hw.mealconfigurator.dto;

import lombok.Data;

import java.util.List;

@Data
public class DayDto {
    private List<DayPositionDto> positionList;
}
