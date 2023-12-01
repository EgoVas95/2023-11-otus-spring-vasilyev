package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тест InputStreamForFilenameProvider")
public class InputStreamGetterTest {
    @DisplayName("Должен выдать ошибку на null'овый файл")
    @Test
    void shouldThrowNullPointerException() {
        InputStreamForFilenameProvider provider = new InputStreamForFilenameProvider(null);
        assertThrows(NullPointerException.class, provider::getInputStream);
    }

    @DisplayName("Должен выдать ошибку на пустой InputStream")
    @Test
    void shouldThrowNullInputStreamException() {
        InputStreamForFilenameProvider provider =
                new InputStreamForFilenameProvider(new AppProperties("empty_test_file_name.csv"));
        Throwable throwable = assertThrows(IOException.class, provider::getInputStream);
        assertTrue(throwable.getMessage().contains("Не удалось получить файл с именем"));
    }

    @DisplayName("Должен выдать ошибку на неподдерживаемый формат")
    @Test
    void shouldThrowUnsupportedExtensionException() {
        InputStreamForFilenameProvider provider =
                new InputStreamForFilenameProvider(new AppProperties("unsupported_extension.pdf"));
        Throwable throwable = assertThrows(IOException.class, provider::getInputStream);
        assertTrue(throwable.getMessage().contains("Неподдерживаемый формат файла!"));
    }

    @DisplayName("Должен отдать нормальный InputStream")
    @Test
    void shouldNotThrowExceptions() {
        InputStreamForFilenameProvider provider =
                new InputStreamForFilenameProvider(new AppProperties("questions.csv"));
        assertDoesNotThrow(provider::getInputStream);
    }
}
