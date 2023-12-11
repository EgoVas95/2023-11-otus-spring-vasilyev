package ru.otus.hw.dao;

import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileNameProvider.getTestFileName())) {

            MappingStrategy<QuestionDto> strategy =
                    new ColumnPositionMappingStrategyBuilder<QuestionDto>().build();
            strategy.setType(QuestionDto.class);

            List<QuestionDto> parseList = new CsvToBeanBuilder<QuestionDto>(
                    new InputStreamReader(is)).withExceptionHandler(e -> {
                throw new QuestionReadException(String.format("Ошибка при чтении из строки %d",
                        e.getLineNumber()), e.getCause());
            }).withMappingStrategy(strategy).withSeparator(';').withSkipLines(1).build().parse();

            List<Question> result = new ArrayList<>(parseList.size());
            for (QuestionDto questionDto : parseList) {
                if (questionDto != null) {
                    result.add(questionDto.toDomainObject());
                }
            }
            return result;
        } catch (Exception ex) {
            throw new QuestionReadException("Непредвиденная ошибка при чтении списка вопросов", ex);
        }
    }
}
