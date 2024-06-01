package ru.otus.hw.mealconfigurator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.mealconfigurator.converter.PriceListConverter;
import ru.otus.hw.mealconfigurator.dto.DayDto;
import ru.otus.hw.mealconfigurator.logic.UserService;
import ru.otus.hw.mealconfigurator.model.PriceList;
import ru.otus.hw.mealconfigurator.repositories.PriceListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceListServiceImpl implements PriceListService {
    private final PriceListRepository repository;

    private final PriceListConverter converter;

    private final PriceService priceService;

    private final UserService userService;

    @Override
    public void save(List<DayDto> dtoList) {
        String id = null;
        final var find = findForUser();
        if (find != null) {
            id = find.getId();
        }
        var priceList = converter.fromPositions(priceService.getPricePositions(dtoList));
        priceList.setId(id);
        repository.save(priceList);
    }

    @Override
    public PriceList findForUser() {
        return repository.findFirstByUserSub(userService.getCurrentUserSub());
    }
}
