package br.com.dinobri.web.rest;

import br.com.dinobri.domain.Secao;
import br.com.dinobri.repository.SecaoRepository;
import br.com.dinobri.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.dinobri.domain.Secao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SecaoResource {

    private final Logger log = LoggerFactory.getLogger(SecaoResource.class);

    private static final String ENTITY_NAME = "secao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecaoRepository secaoRepository;

    public SecaoResource(SecaoRepository secaoRepository) {
        this.secaoRepository = secaoRepository;
    }

    /**
     * {@code POST  /secaos} : Create a new secao.
     *
     * @param secao the secao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new secao, or with status {@code 400 (Bad Request)} if the secao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/secaos")
    public ResponseEntity<Secao> createSecao(@RequestBody Secao secao) throws URISyntaxException {
        log.debug("REST request to save Secao : {}", secao);
        if (secao.getId() != null) {
            throw new BadRequestAlertException("A new secao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Secao result = secaoRepository.save(secao);
        return ResponseEntity.created(new URI("/api/secaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /secaos} : Updates an existing secao.
     *
     * @param secao the secao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secao,
     * or with status {@code 400 (Bad Request)} if the secao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the secao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/secaos")
    public ResponseEntity<Secao> updateSecao(@RequestBody Secao secao) throws URISyntaxException {
        log.debug("REST request to update Secao : {}", secao);
        if (secao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Secao result = secaoRepository.save(secao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /secaos} : get all the secaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of secaos in body.
     */
    @GetMapping("/secaos")
    public List<Secao> getAllSecaos() {
        log.debug("REST request to get all Secaos");
        return secaoRepository.findAll();
    }

    /**
     * {@code GET  /secaos/:id} : get the "id" secao.
     *
     * @param id the id of the secao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the secao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/secaos/{id}")
    public ResponseEntity<Secao> getSecao(@PathVariable Long id) {
        log.debug("REST request to get Secao : {}", id);
        Optional<Secao> secao = secaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(secao);
    }

    /**
     * {@code DELETE  /secaos/:id} : delete the "id" secao.
     *
     * @param id the id of the secao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/secaos/{id}")
    public ResponseEntity<Void> deleteSecao(@PathVariable Long id) {
        log.debug("REST request to delete Secao : {}", id);
        secaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
