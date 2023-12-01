package ru.otus.hw.service;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamGetter {
    InputStream getInputStream() throws IOException;
}
