package  com.youcode.korea2tv.repositories;

import com.youcode.korea2tv.models.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findGenreByName(String name);
    Optional<Genre> findGenreByIdTmdb(Long id);
}
