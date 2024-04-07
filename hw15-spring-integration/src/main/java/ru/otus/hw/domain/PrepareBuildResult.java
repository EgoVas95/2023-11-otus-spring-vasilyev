package ru.otus.hw.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class PrepareBuildResult {

    private BuildingResult buildingResult;

    private boolean hasWaterSupply = false;

    private boolean hasElectric = false;

    private boolean hasHeating = false;

    public PrepareBuildResult(BuildingResult buildingResult) {
        this.buildingResult = buildingResult;
    }
}
