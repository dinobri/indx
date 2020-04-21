package br.com.dinobri.web.rest;

import br.com.dinobri.domain.Revista;
import br.com.dinobri.repository.RevistaRepository;
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
 * REST controller for managing {@link br.com.dinobri.domain.Revista}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RevistaResource {

    private final Logger log = LoggerFactory.getLogger(RevistaResource.class);

    private static final String ENTITY_NAME = "revista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RevistaRepository revistaRepository;

    public RevistaResource(RevistaRepository revistaRepository) {
        this.revistaRepository = revistaRepository;
    }

    /**
     * {@code POST  /revistas} : Create a new revista.
     *
     * @param revista the revista to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new revista, or with status {@code 400 (Bad Request)} if the revista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/revistas")
    public ResponseEntity<Revista> createRevista(@RequestBody Revista revista) throws URISyntaxException {
        log.debug("REST request to save Revista : {}", revista);
        if (revista.getId() != null) {
            throw new BadRequestAlertException("A new revista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Revista result = revistaRepository.save(revista);
        return ResponseEntity.created(new URI("/api/revistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /revistas} : Updates an existing revista.
     *
     * @param revista the revista to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated revista,
     * or with status {@code 400 (Bad Request)} if the revista is not valid,
     * or with status {@code 500 (Internal Server Error)} if the revista couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/revistas")
    public ResponseEntity<Revista> updateRevista(@RequestBody Revista revista) throws URISyntaxException {
        log.debug("REST request to update Revista : {}", revista);
        if (revista.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Revista result = revistaRepository.save(revista);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, revista.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /revistas} : get all the revistas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of revistas in body.
     */
    @GetMapping("/revistas")
    public List<Revista> getAllRevistas() {
        log.debug("REST request to get all Revistas");
        return revistaRepository.findAll();
    }

    /**
     * {@code GET  /revistas/:id} : get the "id" revista.
     *
     * @param id the id of the revista to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the revista, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/revistas/{id}")
    public ResponseEntity<Revista> getRevista(@PathVariable Long id) {
        log.debug("REST request to get Revista : {}", id);
        Optional<Revista> revista = revistaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(revista);
    }

    /**
     * {@code DELETE  /revistas/:id} : delete the "id" revista.
     *
     * @param id the id of the revista to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/revistas/{id}")
    public ResponseEntity<Void> deleteRevista(@PathVariable Long id) {
        log.debug("REST request to delete Revista : {}", id);
        revistaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
