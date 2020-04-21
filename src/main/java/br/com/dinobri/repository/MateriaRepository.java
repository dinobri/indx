package br.com.dinobri.repository;

import br.com.dinobri.domain.Materia;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Materia entity.
 */
@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    @Query(value = "select distinct materia from Materia materia left join fetch materia.tags left join fetch materia.autors",
        countQuery = "select count(distinct materia) from Materia materia")
    Page<Materia> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct materia from Materia materia left join fetch materia.tags left join fetch materia.autors")
    List<Materia> findAllWithEagerRelationships();

    @Query("select materia from Materia materia left join fetch materia.tags left join fetch materia.autors where materia.id =:id")
    Optional<Materia> findOneWithEagerRelationships(@Param("id") Long id);
}
