package br.com.dinobri.web.rest;

import br.com.dinobri.IndxApp;
import br.com.dinobri.domain.Secao;
import br.com.dinobri.repository.SecaoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SecaoResource} REST controller.
 */
@SpringBootTest(classes = IndxApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SecaoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private SecaoRepository secaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecaoMockMvc;

    private Secao secao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Secao createEntity(EntityManager em) {
        Secao secao = new Secao()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        return secao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Secao createUpdatedEntity(EntityManager em) {
        Secao secao = new Secao()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        return secao;
    }

    @BeforeEach
    public void initTest() {
        secao = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecao() throws Exception {
        int databaseSizeBeforeCreate = secaoRepository.findAll().size();

        // Create the Secao
        restSecaoMockMvc.perform(post("/api/secaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secao)))
            .andExpect(status().isCreated());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeCreate + 1);
        Secao testSecao = secaoList.get(secaoList.size() - 1);
        assertThat(testSecao.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testSecao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createSecaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secaoRepository.findAll().size();

        // Create the Secao with an existing ID
        secao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecaoMockMvc.perform(post("/api/secaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secao)))
            .andExpect(status().isBadRequest());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSecaos() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList
        restSecaoMockMvc.perform(get("/api/secaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secao.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getSecao() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get the secao
        restSecaoMockMvc.perform(get("/api/secaos/{id}", secao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(secao.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingSecao() throws Exception {
        // Get the secao
        restSecaoMockMvc.perform(get("/api/secaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecao() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        int databaseSizeBeforeUpdate = secaoRepository.findAll().size();

        // Update the secao
        Secao updatedSecao = secaoRepository.findById(secao.getId()).get();
        // Disconnect from session so that the updates on updatedSecao are not directly saved in db
        em.detach(updatedSecao);
        updatedSecao
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);

        restSecaoMockMvc.perform(put("/api/secaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSecao)))
            .andExpect(status().isOk());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeUpdate);
        Secao testSecao = secaoList.get(secaoList.size() - 1);
        assertThat(testSecao.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testSecao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingSecao() throws Exception {
        int databaseSizeBeforeUpdate = secaoRepository.findAll().size();

        // Create the Secao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecaoMockMvc.perform(put("/api/secaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secao)))
            .andExpect(status().isBadRequest());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSecao() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        int databaseSizeBeforeDelete = secaoRepository.findAll().size();

        // Delete the secao
        restSecaoMockMvc.perform(delete("/api/secaos/{id}", secao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
