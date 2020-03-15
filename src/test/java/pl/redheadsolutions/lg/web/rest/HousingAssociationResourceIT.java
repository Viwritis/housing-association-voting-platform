package pl.redheadsolutions.lg.web.rest;

import pl.redheadsolutions.lg.HousingAssociationVotingPlatformApp;
import pl.redheadsolutions.lg.domain.HousingAssociation;
import pl.redheadsolutions.lg.repository.HousingAssociationRepository;
import pl.redheadsolutions.lg.service.HousingAssociationService;

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
 * Integration tests for the {@link HousingAssociationResource} REST controller.
 */
@SpringBootTest(classes = HousingAssociationVotingPlatformApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class HousingAssociationResourceIT {

    private static final String DEFAULT_HOUSING_ASSOCIATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOUSING_ASSOCIATION_NAME = "BBBBBBBBBB";

    @Autowired
    private HousingAssociationRepository housingAssociationRepository;

    @Autowired
    private HousingAssociationService housingAssociationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHousingAssociationMockMvc;

    private HousingAssociation housingAssociation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingAssociation createEntity(EntityManager em) {
        HousingAssociation housingAssociation = new HousingAssociation()
            .housingAssociationName(DEFAULT_HOUSING_ASSOCIATION_NAME);
        return housingAssociation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingAssociation createUpdatedEntity(EntityManager em) {
        HousingAssociation housingAssociation = new HousingAssociation()
            .housingAssociationName(UPDATED_HOUSING_ASSOCIATION_NAME);
        return housingAssociation;
    }

    @BeforeEach
    public void initTest() {
        housingAssociation = createEntity(em);
    }

    @Test
    @Transactional
    public void createHousingAssociation() throws Exception {
        int databaseSizeBeforeCreate = housingAssociationRepository.findAll().size();

        // Create the HousingAssociation
        restHousingAssociationMockMvc.perform(post("/api/housing-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(housingAssociation)))
            .andExpect(status().isCreated());

        // Validate the HousingAssociation in the database
        List<HousingAssociation> housingAssociationList = housingAssociationRepository.findAll();
        assertThat(housingAssociationList).hasSize(databaseSizeBeforeCreate + 1);
        HousingAssociation testHousingAssociation = housingAssociationList.get(housingAssociationList.size() - 1);
        assertThat(testHousingAssociation.getHousingAssociationName()).isEqualTo(DEFAULT_HOUSING_ASSOCIATION_NAME);
    }

    @Test
    @Transactional
    public void createHousingAssociationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = housingAssociationRepository.findAll().size();

        // Create the HousingAssociation with an existing ID
        housingAssociation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHousingAssociationMockMvc.perform(post("/api/housing-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(housingAssociation)))
            .andExpect(status().isBadRequest());

        // Validate the HousingAssociation in the database
        List<HousingAssociation> housingAssociationList = housingAssociationRepository.findAll();
        assertThat(housingAssociationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkHousingAssociationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingAssociationRepository.findAll().size();
        // set the field null
        housingAssociation.setHousingAssociationName(null);

        // Create the HousingAssociation, which fails.

        restHousingAssociationMockMvc.perform(post("/api/housing-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(housingAssociation)))
            .andExpect(status().isBadRequest());

        List<HousingAssociation> housingAssociationList = housingAssociationRepository.findAll();
        assertThat(housingAssociationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHousingAssociations() throws Exception {
        // Initialize the database
        housingAssociationRepository.saveAndFlush(housingAssociation);

        // Get all the housingAssociationList
        restHousingAssociationMockMvc.perform(get("/api/housing-associations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(housingAssociation.getId().intValue())))
            .andExpect(jsonPath("$.[*].housingAssociationName").value(hasItem(DEFAULT_HOUSING_ASSOCIATION_NAME)));
    }
    
    @Test
    @Transactional
    public void getHousingAssociation() throws Exception {
        // Initialize the database
        housingAssociationRepository.saveAndFlush(housingAssociation);

        // Get the housingAssociation
        restHousingAssociationMockMvc.perform(get("/api/housing-associations/{id}", housingAssociation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(housingAssociation.getId().intValue()))
            .andExpect(jsonPath("$.housingAssociationName").value(DEFAULT_HOUSING_ASSOCIATION_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingHousingAssociation() throws Exception {
        // Get the housingAssociation
        restHousingAssociationMockMvc.perform(get("/api/housing-associations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHousingAssociation() throws Exception {
        // Initialize the database
        housingAssociationService.save(housingAssociation);

        int databaseSizeBeforeUpdate = housingAssociationRepository.findAll().size();

        // Update the housingAssociation
        HousingAssociation updatedHousingAssociation = housingAssociationRepository.findById(housingAssociation.getId()).get();
        // Disconnect from session so that the updates on updatedHousingAssociation are not directly saved in db
        em.detach(updatedHousingAssociation);
        updatedHousingAssociation
            .housingAssociationName(UPDATED_HOUSING_ASSOCIATION_NAME);

        restHousingAssociationMockMvc.perform(put("/api/housing-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHousingAssociation)))
            .andExpect(status().isOk());

        // Validate the HousingAssociation in the database
        List<HousingAssociation> housingAssociationList = housingAssociationRepository.findAll();
        assertThat(housingAssociationList).hasSize(databaseSizeBeforeUpdate);
        HousingAssociation testHousingAssociation = housingAssociationList.get(housingAssociationList.size() - 1);
        assertThat(testHousingAssociation.getHousingAssociationName()).isEqualTo(UPDATED_HOUSING_ASSOCIATION_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingHousingAssociation() throws Exception {
        int databaseSizeBeforeUpdate = housingAssociationRepository.findAll().size();

        // Create the HousingAssociation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingAssociationMockMvc.perform(put("/api/housing-associations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(housingAssociation)))
            .andExpect(status().isBadRequest());

        // Validate the HousingAssociation in the database
        List<HousingAssociation> housingAssociationList = housingAssociationRepository.findAll();
        assertThat(housingAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHousingAssociation() throws Exception {
        // Initialize the database
        housingAssociationService.save(housingAssociation);

        int databaseSizeBeforeDelete = housingAssociationRepository.findAll().size();

        // Delete the housingAssociation
        restHousingAssociationMockMvc.perform(delete("/api/housing-associations/{id}", housingAssociation.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HousingAssociation> housingAssociationList = housingAssociationRepository.findAll();
        assertThat(housingAssociationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
