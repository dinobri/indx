package br.com.dinobri.web.rest;

import br.com.dinobri.IndxApp;
import br.com.dinobri.domain.GrupoTag;
import br.com.dinobri.repository.GrupoTagRepository;

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
 * Integration tests for the {@link GrupoTagResource} REST controller.
 */
@SpringBootTest(classes = IndxApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class GrupoTagResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_COR = "AAAAAAAAAA";
    private static final String UPDATED_COR = "BBBBBBBBBB";

    @Autowired
    private GrupoTagRepository grupoTagRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoTagMockMvc;

    private GrupoTag grupoTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoTag createEntity(EntityManager em) {
        GrupoTag grupoTag = new GrupoTag()
            .nome(DEFAULT_NOME)
            .cor(DEFAULT_COR);
        return grupoTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoTag createUpdatedEntity(EntityManager em) {
        GrupoTag grupoTag = new GrupoTag()
            .nome(UPDATED_NOME)
            .cor(UPDATED_COR);
        return grupoTag;
    }

    @BeforeEach
    public void initTest() {
        grupoTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupoTag() throws Exception {
        int databaseSizeBeforeCreate = grupoTagRepository.findAll().size();

        // Create the GrupoTag
        restGrupoTagMockMvc.perform(post("/api/grupo-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoTag)))
            .andExpect(status().isCreated());

        // Validate the GrupoTag in the database
        List<GrupoTag> grupoTagList = grupoTagRepository.findAll();
        assertThat(grupoTagList).hasSize(databaseSizeBeforeCreate + 1);
        GrupoTag testGrupoTag = grupoTagList.get(grupoTagList.size() - 1);
        assertThat(testGrupoTag.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testGrupoTag.getCor()).isEqualTo(DEFAULT_COR);
    }

    @Test
    @Transactional
    public void createGrupoTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupoTagRepository.findAll().size();

        // Create the GrupoTag with an existing ID
        grupoTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoTagMockMvc.perform(post("/api/grupo-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoTag)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoTag in the database
        List<GrupoTag> grupoTagList = grupoTagRepository.findAll();
        assertThat(grupoTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGrupoTags() throws Exception {
        // Initialize the database
        grupoTagRepository.saveAndFlush(grupoTag);

        // Get all the grupoTagList
        restGrupoTagMockMvc.perform(get("/api/grupo-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cor").value(hasItem(DEFAULT_COR)));
    }
    
    @Test
    @Transactional
    public void getGrupoTag() throws Exception {
        // Initialize the database
        grupoTagRepository.saveAndFlush(grupoTag);

        // Get the grupoTag
        restGrupoTagMockMvc.perform(get("/api/grupo-tags/{id}", grupoTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoTag.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cor").value(DEFAULT_COR));
    }

    @Test
    @Transactional
    public void getNonExistingGrupoTag() throws Exception {
        // Get the grupoTag
        restGrupoTagMockMvc.perform(get("/api/grupo-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupoTag() throws Exception {
        // Initialize the database
        grupoTagRepository.saveAndFlush(grupoTag);

        int databaseSizeBeforeUpdate = grupoTagRepository.findAll().size();

        // Update the grupoTag
        GrupoTag updatedGrupoTag = grupoTagRepository.findById(grupoTag.getId()).get();
        // Disconnect from session so that the updates on updatedGrupoTag are not directly saved in db
        em.detach(updatedGrupoTag);
        updatedGrupoTag
            .nome(UPDATED_NOME)
            .cor(UPDATED_COR);

        restGrupoTagMockMvc.perform(put("/api/grupo-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrupoTag)))
            .andExpect(status().isOk());

        // Validate the GrupoTag in the database
        List<GrupoTag> grupoTagList = grupoTagRepository.findAll();
        assertThat(grupoTagList).hasSize(databaseSizeBeforeUpdate);
        GrupoTag testGrupoTag = grupoTagList.get(grupoTagList.size() - 1);
        assertThat(testGrupoTag.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testGrupoTag.getCor()).isEqualTo(UPDATED_COR);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupoTag() throws Exception {
        int databaseSizeBeforeUpdate = grupoTagRepository.findAll().size();

        // Create the GrupoTag

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoTagMockMvc.perform(put("/api/grupo-tags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(grupoTag)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoTag in the database
        List<GrupoTag> grupoTagList = grupoTagRepository.findAll();
        assertThat(grupoTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrupoTag() throws Exception {
        // Initialize the database
        grupoTagRepository.saveAndFlush(grupoTag);

        int databaseSizeBeforeDelete = grupoTagRepository.findAll().size();

        // Delete the grupoTag
        restGrupoTagMockMvc.perform(delete("/api/grupo-tags/{id}", grupoTag.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GrupoTag> grupoTagList = grupoTagRepository.findAll();
        assertThat(grupoTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
