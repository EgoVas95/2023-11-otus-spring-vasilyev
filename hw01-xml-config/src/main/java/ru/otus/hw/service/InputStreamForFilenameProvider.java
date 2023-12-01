package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class InputStreamForFilenameProvider implements InputStreamGetter {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public InputStream getInputStream() throws Exception {
        if (fileNameProvider == null || fileNameProvider.getTestFileName() == null) {
            throw new NullPointerException("Ошибка при чтении файла! Не передан файл для чтения вопросов!");
        }
        if (!fileNameProvider.getTestFileName().endsWith(".csv")) {
            throw new IOException("Ошибка при чтении файла! Неподдерживаемый формат файла!");
        }

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(fileNameProvider.getTestFileName());
        if (is == null) {
            throw new IOException("Ошибка при чтении файла! Не удалось получить файл с именем: "
                    + fileNameProvider.getTestFileName());
        }
        return is;
    }
}
