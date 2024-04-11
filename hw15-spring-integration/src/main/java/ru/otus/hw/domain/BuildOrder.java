package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class BuildOrder {
    private String foundationType;

    private String wallsType;

    private int floorsNum;
}
