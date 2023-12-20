package ru.otus.hw.config;

import java.util.Locale;

public interface TestFileNameProvider {
    String getTestFileNameByLocaleTag(Locale locale);
}
