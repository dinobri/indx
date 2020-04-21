package br.com.dinobri.repository;

import br.com.dinobri.domain.Edicao;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Edicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EdicaoRepository extends JpaRepository<Edicao, Long> {
}
