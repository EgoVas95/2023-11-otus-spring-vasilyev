package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public JdbcGenreRepository(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public List<Genre> findAll() {
        return namedParameterJdbcOperations.getJdbcOperations().query("select id, name from genres",
                new GenreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        Genre genre = namedParameterJdbcOperations.queryForObject(
                "select id, name from genres where id = :id",
                Map.of("id", id), new GenreRowMapper());
        return Optional.ofNullable(genre);
    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            final long id = rs.getLong("id");
            final String name = rs.getString("name");
            return new Genre(id, name);
        }
    }
}
