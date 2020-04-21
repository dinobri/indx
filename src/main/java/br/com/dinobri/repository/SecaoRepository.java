package br.com.dinobri.repository;

import br.com.dinobri.domain.Secao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Secao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecaoRepository extends JpaRepository<Secao, Long> {
}
