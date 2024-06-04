package ru.otus.hw.models.meals_configurator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.hw.models.Serving;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PricePosition {
    private Serving serving;

    private Long quantity;
}
