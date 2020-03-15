package pl.redheadsolutions.lg.web.rest;

import pl.redheadsolutions.lg.HousingAssociationVotingPlatformApp;
import pl.redheadsolutions.lg.domain.Inhabitant;
import pl.redheadsolutions.lg.repository.InhabitantRepository;
import pl.redheadsolutions.lg.service.InhabitantService;

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
 * Integration tests for the {@link InhabitantResource} REST controller.
 */
@SpringBootTest(classes = HousingAssociationVotingPlatformApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class InhabitantResourceIT {

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private InhabitantRepository inhabitantRepository;

    @Autowired
    private InhabitantService inhabitantService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInhabitantMockMvc;

    private Inhabitant inhabitant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inhabitant createEntity(EntityManager em) {
        Inhabitant inhabitant = new Inhabitant()
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return inhabitant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inhabitant createUpdatedEntity(EntityManager em) {
        Inhabitant inhabitant = new Inhabitant()
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return inhabitant;
    }

    @BeforeEach
    public void initTest() {
        inhabitant = createEntity(em);
    }

    @Test
    @Transactional
    public void createInhabitant() throws Exception {
        int databaseSizeBeforeCreate = inhabitantRepository.findAll().size();

        // Create the Inhabitant
        restInhabitantMockMvc.perform(post("/api/inhabitants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inhabitant)))
            .andExpect(status().isCreated());

        // Validate the Inhabitant in the database
        List<Inhabitant> inhabitantList = inhabitantRepository.findAll();
        assertThat(inhabitantList).hasSize(databaseSizeBeforeCreate + 1);
        Inhabitant testInhabitant = inhabitantList.get(inhabitantList.size() - 1);
        assertThat(testInhabitant.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createInhabitantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inhabitantRepository.findAll().size();

        // Create the Inhabitant with an existing ID
        inhabitant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInhabitantMockMvc.perform(post("/api/inhabitants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inhabitant)))
            .andExpect(status().isBadRequest());

        // Validate the Inhabitant in the database
        List<Inhabitant> inhabitantList = inhabitantRepository.findAll();
        assertThat(inhabitantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInhabitants() throws Exception {
        // Initialize the database
        inhabitantRepository.saveAndFlush(inhabitant);

        // Get all the inhabitantList
        restInhabitantMockMvc.perform(get("/api/inhabitants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inhabitant.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getInhabitant() throws Exception {
        // Initialize the database
        inhabitantRepository.saveAndFlush(inhabitant);

        // Get the inhabitant
        restInhabitantMockMvc.perform(get("/api/inhabitants/{id}", inhabitant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inhabitant.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingInhabitant() throws Exception {
        // Get the inhabitant
        restInhabitantMockMvc.perform(get("/api/inhabitants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInhabitant() throws Exception {
        // Initialize the database
        inhabitantService.save(inhabitant);

        int databaseSizeBeforeUpdate = inhabitantRepository.findAll().size();

        // Update the inhabitant
        Inhabitant updatedInhabitant = inhabitantRepository.findById(inhabitant.getId()).get();
        // Disconnect from session so that the updates on updatedInhabitant are not directly saved in db
        em.detach(updatedInhabitant);
        updatedInhabitant
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restInhabitantMockMvc.perform(put("/api/inhabitants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInhabitant)))
            .andExpect(status().isOk());

        // Validate the Inhabitant in the database
        List<Inhabitant> inhabitantList = inhabitantRepository.findAll();
        assertThat(inhabitantList).hasSize(databaseSizeBeforeUpdate);
        Inhabitant testInhabitant = inhabitantList.get(inhabitantList.size() - 1);
        assertThat(testInhabitant.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingInhabitant() throws Exception {
        int databaseSizeBeforeUpdate = inhabitantRepository.findAll().size();

        // Create the Inhabitant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInhabitantMockMvc.perform(put("/api/inhabitants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inhabitant)))
            .andExpect(status().isBadRequest());

        // Validate the Inhabitant in the database
        List<Inhabitant> inhabitantList = inhabitantRepository.findAll();
        assertThat(inhabitantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInhabitant() throws Exception {
        // Initialize the database
        inhabitantService.save(inhabitant);

        int databaseSizeBeforeDelete = inhabitantRepository.findAll().size();

        // Delete the inhabitant
        restInhabitantMockMvc.perform(delete("/api/inhabitants/{id}", inhabitant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inhabitant> inhabitantList = inhabitantRepository.findAll();
        assertThat(inhabitantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
