package br.com.dinobri.web.rest;

import br.com.dinobri.IndxApp;
import br.com.dinobri.domain.Edicao;
import br.com.dinobri.repository.EdicaoRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EdicaoResource} REST controller.
 */
@SpringBootTest(classes = IndxApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EdicaoResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final LocalDate DEFAULT_DATA_PUBLICACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_PUBLICACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIA = "BBBBBBBBBB";

    @Autowired
    private EdicaoRepository edicaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEdicaoMockMvc;

    private Edicao edicao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Edicao createEntity(EntityManager em) {
        Edicao edicao = new Edicao()
            .numero(DEFAULT_NUMERO)
            .dataPublicacao(DEFAULT_DATA_PUBLICACAO)
            .referencia(DEFAULT_REFERENCIA);
        return edicao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Edicao createUpdatedEntity(EntityManager em) {
        Edicao edicao = new Edicao()
            .numero(UPDATED_NUMERO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .referencia(UPDATED_REFERENCIA);
        return edicao;
    }

    @BeforeEach
    public void initTest() {
        edicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createEdicao() throws Exception {
        int databaseSizeBeforeCreate = edicaoRepository.findAll().size();

        // Create the Edicao
        restEdicaoMockMvc.perform(post("/api/edicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(edicao)))
            .andExpect(status().isCreated());

        // Validate the Edicao in the database
        List<Edicao> edicaoList = edicaoRepository.findAll();
        assertThat(edicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Edicao testEdicao = edicaoList.get(edicaoList.size() - 1);
        assertThat(testEdicao.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEdicao.getDataPublicacao()).isEqualTo(DEFAULT_DATA_PUBLICACAO);
        assertThat(testEdicao.getReferencia()).isEqualTo(DEFAULT_REFERENCIA);
    }

    @Test
    @Transactional
    public void createEdicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = edicaoRepository.findAll().size();

        // Create the Edicao with an existing ID
        edicao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEdicaoMockMvc.perform(post("/api/edicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(edicao)))
            .andExpect(status().isBadRequest());

        // Validate the Edicao in the database
        List<Edicao> edicaoList = edicaoRepository.findAll();
        assertThat(edicaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEdicaos() throws Exception {
        // Initialize the database
        edicaoRepository.saveAndFlush(edicao);

        // Get all the edicaoList
        restEdicaoMockMvc.perform(get("/api/edicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].dataPublicacao").value(hasItem(DEFAULT_DATA_PUBLICACAO.toString())))
            .andExpect(jsonPath("$.[*].referencia").value(hasItem(DEFAULT_REFERENCIA)));
    }
    
    @Test
    @Transactional
    public void getEdicao() throws Exception {
        // Initialize the database
        edicaoRepository.saveAndFlush(edicao);

        // Get the edicao
        restEdicaoMockMvc.perform(get("/api/edicaos/{id}", edicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(edicao.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.dataPublicacao").value(DEFAULT_DATA_PUBLICACAO.toString()))
            .andExpect(jsonPath("$.referencia").value(DEFAULT_REFERENCIA));
    }

    @Test
    @Transactional
    public void getNonExistingEdicao() throws Exception {
        // Get the edicao
        restEdicaoMockMvc.perform(get("/api/edicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdicao() throws Exception {
        // Initialize the database
        edicaoRepository.saveAndFlush(edicao);

        int databaseSizeBeforeUpdate = edicaoRepository.findAll().size();

        // Update the edicao
        Edicao updatedEdicao = edicaoRepository.findById(edicao.getId()).get();
        // Disconnect from session so that the updates on updatedEdicao are not directly saved in db
        em.detach(updatedEdicao);
        updatedEdicao
            .numero(UPDATED_NUMERO)
            .dataPublicacao(UPDATED_DATA_PUBLICACAO)
            .referencia(UPDATED_REFERENCIA);

        restEdicaoMockMvc.perform(put("/api/edicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEdicao)))
            .andExpect(status().isOk());

        // Validate the Edicao in the database
        List<Edicao> edicaoList = edicaoRepository.findAll();
        assertThat(edicaoList).hasSize(databaseSizeBeforeUpdate);
        Edicao testEdicao = edicaoList.get(edicaoList.size() - 1);
        assertThat(testEdicao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEdicao.getDataPublicacao()).isEqualTo(UPDATED_DATA_PUBLICACAO);
        assertThat(testEdicao.getReferencia()).isEqualTo(UPDATED_REFERENCIA);
    }

    @Test
    @Transactional
    public void updateNonExistingEdicao() throws Exception {
        int databaseSizeBeforeUpdate = edicaoRepository.findAll().size();

        // Create the Edicao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEdicaoMockMvc.perform(put("/api/edicaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(edicao)))
            .andExpect(status().isBadRequest());

        // Validate the Edicao in the database
        List<Edicao> edicaoList = edicaoRepository.findAll();
        assertThat(edicaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEdicao() throws Exception {
        // Initialize the database
        edicaoRepository.saveAndFlush(edicao);

        int databaseSizeBeforeDelete = edicaoRepository.findAll().size();

        // Delete the edicao
        restEdicaoMockMvc.perform(delete("/api/edicaos/{id}", edicao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Edicao> edicaoList = edicaoRepository.findAll();
        assertThat(edicaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
