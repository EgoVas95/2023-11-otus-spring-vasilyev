package ru.otus.hw.service;

public interface LocalizedIOService extends LocalizedMessagesService, IOService {
    void printLocalized(String code);

    void prinfFormattedLineLocalized(String code, Object... args);

    String readStringWithPromtLocalized(String promptCode);

    int readIntForRangeLocalized(int min, int max, String errorMessageCode);
}
