package ru.otus.hw.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum FoundationTypes {
    PILE("pile"),
    RIBBON("ribbon"),
    PLATE("plate"),
    COLUMNAR("columnar");

    private final String typeName;

    FoundationTypes(String typeName) {
        this.typeName = typeName;
    }

    public static FoundationTypes getFromTypeName(String typeName) {
        for (FoundationTypes type : FoundationTypes.values()) {
            if (type.getTypeName().equals(typeName)) {
                return type;
            }
        }
        return FoundationTypes.PILE;
    }
}
