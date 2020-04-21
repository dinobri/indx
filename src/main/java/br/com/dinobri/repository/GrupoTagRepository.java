package br.com.dinobri.repository;

import br.com.dinobri.domain.GrupoTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GrupoTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoTagRepository extends JpaRepository<GrupoTag, Long> {
}
