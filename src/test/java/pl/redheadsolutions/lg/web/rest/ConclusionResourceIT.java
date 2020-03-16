package pl.redheadsolutions.lg.web.rest;

import pl.redheadsolutions.lg.HousingAssociationVotingPlatformApp;
import pl.redheadsolutions.lg.domain.Conclusion;
import pl.redheadsolutions.lg.repository.ConclusionRepository;
import pl.redheadsolutions.lg.service.ConclusionService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConclusionResource} REST controller.
 */
@SpringBootTest(classes = HousingAssociationVotingPlatformApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ConclusionResourceIT {

    private static final String DEFAULT_CONCLUSION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONCLUSION_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ConclusionRepository conclusionRepository;

    @Autowired
    private ConclusionService conclusionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConclusionMockMvc;

    private Conclusion conclusion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conclusion createEntity(EntityManager em) {
        Conclusion conclusion = new Conclusion()
            .conclusionName(DEFAULT_CONCLUSION_NAME)
            .conclusionContent(DEFAULT_CONCLUSION_CONTENT)
            .creationDate(DEFAULT_CREATION_DATE)
            .modificationDate(DEFAULT_MODIFICATION_DATE);
        return conclusion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conclusion createUpdatedEntity(EntityManager em) {
        Conclusion conclusion = new Conclusion()
            .conclusionName(UPDATED_CONCLUSION_NAME)
            .conclusionContent(UPDATED_CONCLUSION_CONTENT)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);
        return conclusion;
    }

    @BeforeEach
    public void initTest() {
        conclusion = createEntity(em);
    }

    @Test
    @Transactional
    public void createConclusion() throws Exception {
        int databaseSizeBeforeCreate = conclusionRepository.findAll().size();

        // Create the Conclusion
        restConclusionMockMvc.perform(post("/api/conclusions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conclusion)))
            .andExpect(status().isCreated());

        // Validate the Conclusion in the database
        List<Conclusion> conclusionList = conclusionRepository.findAll();
        assertThat(conclusionList).hasSize(databaseSizeBeforeCreate + 1);
        Conclusion testConclusion = conclusionList.get(conclusionList.size() - 1);
        assertThat(testConclusion.getConclusionName()).isEqualTo(DEFAULT_CONCLUSION_NAME);
        assertThat(testConclusion.getConclusionContent()).isEqualTo(DEFAULT_CONCLUSION_CONTENT);
        assertThat(testConclusion.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testConclusion.getModificationDate()).isEqualTo(DEFAULT_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void createConclusionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conclusionRepository.findAll().size();

        // Create the Conclusion with an existing ID
        conclusion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConclusionMockMvc.perform(post("/api/conclusions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conclusion)))
            .andExpect(status().isBadRequest());

        // Validate the Conclusion in the database
        List<Conclusion> conclusionList = conclusionRepository.findAll();
        assertThat(conclusionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkConclusionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = conclusionRepository.findAll().size();
        // set the field null
        conclusion.setConclusionName(null);

        // Create the Conclusion, which fails.

        restConclusionMockMvc.perform(post("/api/conclusions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conclusion)))
            .andExpect(status().isBadRequest());

        List<Conclusion> conclusionList = conclusionRepository.findAll();
        assertThat(conclusionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConclusionContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = conclusionRepository.findAll().size();
        // set the field null
        conclusion.setConclusionContent(null);

        // Create the Conclusion, which fails.

        restConclusionMockMvc.perform(post("/api/conclusions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conclusion)))
            .andExpect(status().isBadRequest());

        List<Conclusion> conclusionList = conclusionRepository.findAll();
        assertThat(conclusionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConclusions() throws Exception {
        // Initialize the database
        conclusionRepository.saveAndFlush(conclusion);

        // Get all the conclusionList
        restConclusionMockMvc.perform(get("/api/conclusions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conclusion.getId().intValue())))
            .andExpect(jsonPath("$.[*].conclusionName").value(hasItem(DEFAULT_CONCLUSION_NAME)))
            .andExpect(jsonPath("$.[*].conclusionContent").value(hasItem(DEFAULT_CONCLUSION_CONTENT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getConclusion() throws Exception {
        // Initialize the database
        conclusionRepository.saveAndFlush(conclusion);

        // Get the conclusion
        restConclusionMockMvc.perform(get("/api/conclusions/{id}", conclusion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conclusion.getId().intValue()))
            .andExpect(jsonPath("$.conclusionName").value(DEFAULT_CONCLUSION_NAME))
            .andExpect(jsonPath("$.conclusionContent").value(DEFAULT_CONCLUSION_CONTENT))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConclusion() throws Exception {
        // Get the conclusion
        restConclusionMockMvc.perform(get("/api/conclusions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConclusion() throws Exception {
        // Initialize the database
        conclusionService.save(conclusion);

        int databaseSizeBeforeUpdate = conclusionRepository.findAll().size();

        // Update the conclusion
        Conclusion updatedConclusion = conclusionRepository.findById(conclusion.getId()).get();
        // Disconnect from session so that the updates on updatedConclusion are not directly saved in db
        em.detach(updatedConclusion);
        updatedConclusion
            .conclusionName(UPDATED_CONCLUSION_NAME)
            .conclusionContent(UPDATED_CONCLUSION_CONTENT)
            .creationDate(UPDATED_CREATION_DATE)
            .modificationDate(UPDATED_MODIFICATION_DATE);

        restConclusionMockMvc.perform(put("/api/conclusions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConclusion)))
            .andExpect(status().isOk());

        // Validate the Conclusion in the database
        List<Conclusion> conclusionList = conclusionRepository.findAll();
        assertThat(conclusionList).hasSize(databaseSizeBeforeUpdate);
        Conclusion testConclusion = conclusionList.get(conclusionList.size() - 1);
        assertThat(testConclusion.getConclusionName()).isEqualTo(UPDATED_CONCLUSION_NAME);
        assertThat(testConclusion.getConclusionContent()).isEqualTo(UPDATED_CONCLUSION_CONTENT);
        assertThat(testConclusion.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testConclusion.getModificationDate()).isEqualTo(UPDATED_MODIFICATION_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingConclusion() throws Exception {
        int databaseSizeBeforeUpdate = conclusionRepository.findAll().size();

        // Create the Conclusion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConclusionMockMvc.perform(put("/api/conclusions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conclusion)))
            .andExpect(status().isBadRequest());

        // Validate the Conclusion in the database
        List<Conclusion> conclusionList = conclusionRepository.findAll();
        assertThat(conclusionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConclusion() throws Exception {
        // Initialize the database
        conclusionService.save(conclusion);

        int databaseSizeBeforeDelete = conclusionRepository.findAll().size();

        // Delete the conclusion
        restConclusionMockMvc.perform(delete("/api/conclusions/{id}", conclusion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conclusion> conclusionList = conclusionRepository.findAll();
        assertThat(conclusionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
