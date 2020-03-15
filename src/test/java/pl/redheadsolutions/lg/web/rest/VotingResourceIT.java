package pl.redheadsolutions.lg.web.rest;

import pl.redheadsolutions.lg.HousingAssociationVotingPlatformApp;
import pl.redheadsolutions.lg.domain.Voting;
import pl.redheadsolutions.lg.repository.VotingRepository;
import pl.redheadsolutions.lg.service.VotingService;

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
 * Integration tests for the {@link VotingResource} REST controller.
 */
@SpringBootTest(classes = HousingAssociationVotingPlatformApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class VotingResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private VotingService votingService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVotingMockMvc;

    private Voting voting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voting createEntity(EntityManager em) {
        Voting voting = new Voting()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return voting;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Voting createUpdatedEntity(EntityManager em) {
        Voting voting = new Voting()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return voting;
    }

    @BeforeEach
    public void initTest() {
        voting = createEntity(em);
    }

    @Test
    @Transactional
    public void createVoting() throws Exception {
        int databaseSizeBeforeCreate = votingRepository.findAll().size();

        // Create the Voting
        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voting)))
            .andExpect(status().isCreated());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeCreate + 1);
        Voting testVoting = votingList.get(votingList.size() - 1);
        assertThat(testVoting.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testVoting.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createVotingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = votingRepository.findAll().size();

        // Create the Voting with an existing ID
        voting.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voting)))
            .andExpect(status().isBadRequest());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = votingRepository.findAll().size();
        // set the field null
        voting.setStartDate(null);

        // Create the Voting, which fails.

        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voting)))
            .andExpect(status().isBadRequest());

        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = votingRepository.findAll().size();
        // set the field null
        voting.setEndDate(null);

        // Create the Voting, which fails.

        restVotingMockMvc.perform(post("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voting)))
            .andExpect(status().isBadRequest());

        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVotings() throws Exception {
        // Initialize the database
        votingRepository.saveAndFlush(voting);

        // Get all the votingList
        restVotingMockMvc.perform(get("/api/votings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voting.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getVoting() throws Exception {
        // Initialize the database
        votingRepository.saveAndFlush(voting);

        // Get the voting
        restVotingMockMvc.perform(get("/api/votings/{id}", voting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voting.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoting() throws Exception {
        // Get the voting
        restVotingMockMvc.perform(get("/api/votings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoting() throws Exception {
        // Initialize the database
        votingService.save(voting);

        int databaseSizeBeforeUpdate = votingRepository.findAll().size();

        // Update the voting
        Voting updatedVoting = votingRepository.findById(voting.getId()).get();
        // Disconnect from session so that the updates on updatedVoting are not directly saved in db
        em.detach(updatedVoting);
        updatedVoting
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restVotingMockMvc.perform(put("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVoting)))
            .andExpect(status().isOk());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeUpdate);
        Voting testVoting = votingList.get(votingList.size() - 1);
        assertThat(testVoting.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testVoting.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVoting() throws Exception {
        int databaseSizeBeforeUpdate = votingRepository.findAll().size();

        // Create the Voting

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVotingMockMvc.perform(put("/api/votings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(voting)))
            .andExpect(status().isBadRequest());

        // Validate the Voting in the database
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVoting() throws Exception {
        // Initialize the database
        votingService.save(voting);

        int databaseSizeBeforeDelete = votingRepository.findAll().size();

        // Delete the voting
        restVotingMockMvc.perform(delete("/api/votings/{id}", voting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Voting> votingList = votingRepository.findAll();
        assertThat(votingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
