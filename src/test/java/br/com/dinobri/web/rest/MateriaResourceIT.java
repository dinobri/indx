package br.com.dinobri.web.rest;

import br.com.dinobri.IndxApp;
import br.com.dinobri.domain.Materia;
import br.com.dinobri.repository.MateriaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MateriaResource} REST controller.
 */
@SpringBootTest(classes = IndxApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MateriaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGINA_INICIAL = 1;
    private static final Integer UPDATED_PAGINA_INICIAL = 2;

    private static final Integer DEFAULT_PAGINA_FINAL = 1;
    private static final Integer UPDATED_PAGINA_FINAL = 2;

    @Autowired
    private MateriaRepository materiaRepository;

    @Mock
    private MateriaRepository materiaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMateriaMockMvc;

    private Materia materia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materia createEntity(EntityManager em) {
        Materia materia = new Materia()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO)
            .paginaInicial(DEFAULT_PAGINA_INICIAL)
            .paginaFinal(DEFAULT_PAGINA_FINAL);
        return materia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materia createUpdatedEntity(EntityManager em) {
        Materia materia = new Materia()
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .paginaInicial(UPDATED_PAGINA_INICIAL)
            .paginaFinal(UPDATED_PAGINA_FINAL);
        return materia;
    }

    @BeforeEach
    public void initTest() {
        materia = createEntity(em);
    }

    @Test
    @Transactional
    public void createMateria() throws Exception {
        int databaseSizeBeforeCreate = materiaRepository.findAll().size();

        // Create the Materia
        restMateriaMockMvc.perform(post("/api/materias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materia)))
            .andExpect(status().isCreated());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeCreate + 1);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testMateria.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMateria.getPaginaInicial()).isEqualTo(DEFAULT_PAGINA_INICIAL);
        assertThat(testMateria.getPaginaFinal()).isEqualTo(DEFAULT_PAGINA_FINAL);
    }

    @Test
    @Transactional
    public void createMateriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materiaRepository.findAll().size();

        // Create the Materia with an existing ID
        materia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriaMockMvc.perform(post("/api/materias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materia)))
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMaterias() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList
        restMateriaMockMvc.perform(get("/api/materias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materia.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].paginaInicial").value(hasItem(DEFAULT_PAGINA_INICIAL)))
            .andExpect(jsonPath("$.[*].paginaFinal").value(hasItem(DEFAULT_PAGINA_FINAL)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMateriasWithEagerRelationshipsIsEnabled() throws Exception {
        MateriaResource materiaResource = new MateriaResource(materiaRepositoryMock);
        when(materiaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMateriaMockMvc.perform(get("/api/materias?eagerload=true"))
            .andExpect(status().isOk());

        verify(materiaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMateriasWithEagerRelationshipsIsNotEnabled() throws Exception {
        MateriaResource materiaResource = new MateriaResource(materiaRepositoryMock);
        when(materiaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMateriaMockMvc.perform(get("/api/materias?eagerload=true"))
            .andExpect(status().isOk());

        verify(materiaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get the materia
        restMateriaMockMvc.perform(get("/api/materias/{id}", materia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materia.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.paginaInicial").value(DEFAULT_PAGINA_INICIAL))
            .andExpect(jsonPath("$.paginaFinal").value(DEFAULT_PAGINA_FINAL));
    }

    @Test
    @Transactional
    public void getNonExistingMateria() throws Exception {
        // Get the materia
        restMateriaMockMvc.perform(get("/api/materias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Update the materia
        Materia updatedMateria = materiaRepository.findById(materia.getId()).get();
        // Disconnect from session so that the updates on updatedMateria are not directly saved in db
        em.detach(updatedMateria);
        updatedMateria
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO)
            .paginaInicial(UPDATED_PAGINA_INICIAL)
            .paginaFinal(UPDATED_PAGINA_FINAL);

        restMateriaMockMvc.perform(put("/api/materias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMateria)))
            .andExpect(status().isOk());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testMateria.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMateria.getPaginaInicial()).isEqualTo(UPDATED_PAGINA_INICIAL);
        assertThat(testMateria.getPaginaFinal()).isEqualTo(UPDATED_PAGINA_FINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Create the Materia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaMockMvc.perform(put("/api/materias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materia)))
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeDelete = materiaRepository.findAll().size();

        // Delete the materia
        restMateriaMockMvc.perform(delete("/api/materias/{id}", materia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
