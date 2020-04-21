package br.com.dinobri.repository;

import br.com.dinobri.domain.Revista;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Revista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RevistaRepository extends JpaRepository<Revista, Long> {
}
