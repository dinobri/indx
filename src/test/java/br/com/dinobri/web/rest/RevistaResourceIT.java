package br.com.dinobri.web.rest;

import br.com.dinobri.IndxApp;
import br.com.dinobri.domain.Revista;
import br.com.dinobri.repository.RevistaRepository;

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

import br.com.dinobri.domain.enumeration.Periodicidade;
/**
 * Integration tests for the {@link RevistaResource} REST controller.
 */
@SpringBootTest(classes = IndxApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class RevistaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Periodicidade DEFAULT_PERIODICIDADE = Periodicidade.MENSAL;
    private static final Periodicidade UPDATED_PERIODICIDADE = Periodicidade.ESPORADICO;

    @Autowired
    private RevistaRepository revistaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRevistaMockMvc;

    private Revista revista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revista createEntity(EntityManager em) {
        Revista revista = new Revista()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .periodicidade(DEFAULT_PERIODICIDADE);
        return revista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Revista createUpdatedEntity(EntityManager em) {
        Revista revista = new Revista()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .periodicidade(UPDATED_PERIODICIDADE);
        return revista;
    }

    @BeforeEach
    public void initTest() {
        revista = createEntity(em);
    }

    @Test
    @Transactional
    public void createRevista() throws Exception {
        int databaseSizeBeforeCreate = revistaRepository.findAll().size();

        // Create the Revista
        restRevistaMockMvc.perform(post("/api/revistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(revista)))
            .andExpect(status().isCreated());

        // Validate the Revista in the database
        List<Revista> revistaList = revistaRepository.findAll();
        assertThat(revistaList).hasSize(databaseSizeBeforeCreate + 1);
        Revista testRevista = revistaList.get(revistaList.size() - 1);
        assertThat(testRevista.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testRevista.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testRevista.getPeriodicidade()).isEqualTo(DEFAULT_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void createRevistaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = revistaRepository.findAll().size();

        // Create the Revista with an existing ID
        revista.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRevistaMockMvc.perform(post("/api/revistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(revista)))
            .andExpect(status().isBadRequest());

        // Validate the Revista in the database
        List<Revista> revistaList = revistaRepository.findAll();
        assertThat(revistaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRevistas() throws Exception {
        // Initialize the database
        revistaRepository.saveAndFlush(revista);

        // Get all the revistaList
        restRevistaMockMvc.perform(get("/api/revistas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(revista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].periodicidade").value(hasItem(DEFAULT_PERIODICIDADE.toString())));
    }
    
    @Test
    @Transactional
    public void getRevista() throws Exception {
        // Initialize the database
        revistaRepository.saveAndFlush(revista);

        // Get the revista
        restRevistaMockMvc.perform(get("/api/revistas/{id}", revista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(revista.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.periodicidade").value(DEFAULT_PERIODICIDADE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRevista() throws Exception {
        // Get the revista
        restRevistaMockMvc.perform(get("/api/revistas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRevista() throws Exception {
        // Initialize the database
        revistaRepository.saveAndFlush(revista);

        int databaseSizeBeforeUpdate = revistaRepository.findAll().size();

        // Update the revista
        Revista updatedRevista = revistaRepository.findById(revista.getId()).get();
        // Disconnect from session so that the updates on updatedRevista are not directly saved in db
        em.detach(updatedRevista);
        updatedRevista
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .periodicidade(UPDATED_PERIODICIDADE);

        restRevistaMockMvc.perform(put("/api/revistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRevista)))
            .andExpect(status().isOk());

        // Validate the Revista in the database
        List<Revista> revistaList = revistaRepository.findAll();
        assertThat(revistaList).hasSize(databaseSizeBeforeUpdate);
        Revista testRevista = revistaList.get(revistaList.size() - 1);
        assertThat(testRevista.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testRevista.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testRevista.getPeriodicidade()).isEqualTo(UPDATED_PERIODICIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingRevista() throws Exception {
        int databaseSizeBeforeUpdate = revistaRepository.findAll().size();

        // Create the Revista

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRevistaMockMvc.perform(put("/api/revistas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(revista)))
            .andExpect(status().isBadRequest());

        // Validate the Revista in the database
        List<Revista> revistaList = revistaRepository.findAll();
        assertThat(revistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRevista() throws Exception {
        // Initialize the database
        revistaRepository.saveAndFlush(revista);

        int databaseSizeBeforeDelete = revistaRepository.findAll().size();

        // Delete the revista
        restRevistaMockMvc.perform(delete("/api/revistas/{id}", revista.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Revista> revistaList = revistaRepository.findAll();
        assertThat(revistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
