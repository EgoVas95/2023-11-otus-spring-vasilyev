package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class BuildingResult {
    private FoundationTypes foundation;

    private WallsTypes walls;

    private int floorsNum;
}
