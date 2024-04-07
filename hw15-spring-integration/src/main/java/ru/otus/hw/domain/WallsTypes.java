package ru.otus.hw.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum WallsTypes {
    BRICK("brick"),
    GAS("gas"),
    BLOCK("block"),
    CONCRETE("concrete"),
    WOOD("wood");

    private final String typeName;

    WallsTypes(String typeName) {
        this.typeName = typeName;
    }

    public static WallsTypes getFromTypeName(String typeName) {
        for (WallsTypes type : WallsTypes.values()) {
            if (type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return WallsTypes.BLOCK;
    }
}
