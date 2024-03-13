package  com.youcode.korea2tv.repositories;

import  com.youcode.korea2tv.models.entity.Genre;
import  com.youcode.korea2tv.models.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
    Optional<Production> findProductionByIdTmdb(Long id);

}
