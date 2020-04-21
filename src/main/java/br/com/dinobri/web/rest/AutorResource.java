package br.com.dinobri.web.rest;

import br.com.dinobri.domain.Autor;
import br.com.dinobri.repository.AutorRepository;
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
 * REST controller for managing {@link br.com.dinobri.domain.Autor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AutorResource {

    private final Logger log = LoggerFactory.getLogger(AutorResource.class);

    private static final String ENTITY_NAME = "autor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutorRepository autorRepository;

    public AutorResource(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    /**
     * {@code POST  /autors} : Create a new autor.
     *
     * @param autor the autor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autor, or with status {@code 400 (Bad Request)} if the autor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/autors")
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) throws URISyntaxException {
        log.debug("REST request to save Autor : {}", autor);
        if (autor.getId() != null) {
            throw new BadRequestAlertException("A new autor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Autor result = autorRepository.save(autor);
        return ResponseEntity.created(new URI("/api/autors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /autors} : Updates an existing autor.
     *
     * @param autor the autor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autor,
     * or with status {@code 400 (Bad Request)} if the autor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/autors")
    public ResponseEntity<Autor> updateAutor(@RequestBody Autor autor) throws URISyntaxException {
        log.debug("REST request to update Autor : {}", autor);
        if (autor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Autor result = autorRepository.save(autor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /autors} : get all the autors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autors in body.
     */
    @GetMapping("/autors")
    public List<Autor> getAllAutors() {
        log.debug("REST request to get all Autors");
        return autorRepository.findAll();
    }

    /**
     * {@code GET  /autors/:id} : get the "id" autor.
     *
     * @param id the id of the autor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/autors/{id}")
    public ResponseEntity<Autor> getAutor(@PathVariable Long id) {
        log.debug("REST request to get Autor : {}", id);
        Optional<Autor> autor = autorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(autor);
    }

    /**
     * {@code DELETE  /autors/:id} : delete the "id" autor.
     *
     * @param id the id of the autor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/autors/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        log.debug("REST request to delete Autor : {}", id);
        autorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
