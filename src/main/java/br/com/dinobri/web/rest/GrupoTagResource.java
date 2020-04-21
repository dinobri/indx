package br.com.dinobri.web.rest;

import br.com.dinobri.domain.GrupoTag;
import br.com.dinobri.repository.GrupoTagRepository;
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
 * REST controller for managing {@link br.com.dinobri.domain.GrupoTag}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GrupoTagResource {

    private final Logger log = LoggerFactory.getLogger(GrupoTagResource.class);

    private static final String ENTITY_NAME = "grupoTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoTagRepository grupoTagRepository;

    public GrupoTagResource(GrupoTagRepository grupoTagRepository) {
        this.grupoTagRepository = grupoTagRepository;
    }

    /**
     * {@code POST  /grupo-tags} : Create a new grupoTag.
     *
     * @param grupoTag the grupoTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoTag, or with status {@code 400 (Bad Request)} if the grupoTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-tags")
    public ResponseEntity<GrupoTag> createGrupoTag(@RequestBody GrupoTag grupoTag) throws URISyntaxException {
        log.debug("REST request to save GrupoTag : {}", grupoTag);
        if (grupoTag.getId() != null) {
            throw new BadRequestAlertException("A new grupoTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoTag result = grupoTagRepository.save(grupoTag);
        return ResponseEntity.created(new URI("/api/grupo-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-tags} : Updates an existing grupoTag.
     *
     * @param grupoTag the grupoTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoTag,
     * or with status {@code 400 (Bad Request)} if the grupoTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-tags")
    public ResponseEntity<GrupoTag> updateGrupoTag(@RequestBody GrupoTag grupoTag) throws URISyntaxException {
        log.debug("REST request to update GrupoTag : {}", grupoTag);
        if (grupoTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoTag result = grupoTagRepository.save(grupoTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoTag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupo-tags} : get all the grupoTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoTags in body.
     */
    @GetMapping("/grupo-tags")
    public List<GrupoTag> getAllGrupoTags() {
        log.debug("REST request to get all GrupoTags");
        return grupoTagRepository.findAll();
    }

    /**
     * {@code GET  /grupo-tags/:id} : get the "id" grupoTag.
     *
     * @param id the id of the grupoTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoTag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-tags/{id}")
    public ResponseEntity<GrupoTag> getGrupoTag(@PathVariable Long id) {
        log.debug("REST request to get GrupoTag : {}", id);
        Optional<GrupoTag> grupoTag = grupoTagRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(grupoTag);
    }

    /**
     * {@code DELETE  /grupo-tags/:id} : delete the "id" grupoTag.
     *
     * @param id the id of the grupoTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-tags/{id}")
    public ResponseEntity<Void> deleteGrupoTag(@PathVariable Long id) {
        log.debug("REST request to delete GrupoTag : {}", id);
        grupoTagRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
