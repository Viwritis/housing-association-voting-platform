package pl.redheadsolutions.lg.web.rest;

import pl.redheadsolutions.lg.domain.Voting;
import pl.redheadsolutions.lg.service.VotingService;
import pl.redheadsolutions.lg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.redheadsolutions.lg.domain.Voting}.
 */
@RestController
@RequestMapping("/api")
public class VotingResource {

    private final Logger log = LoggerFactory.getLogger(VotingResource.class);

    private static final String ENTITY_NAME = "voting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VotingService votingService;

    public VotingResource(VotingService votingService) {
        this.votingService = votingService;
    }

    /**
     * {@code POST  /votings} : Create a new voting.
     *
     * @param voting the voting to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new voting, or with status {@code 400 (Bad Request)} if the voting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/votings")
    public ResponseEntity<Voting> createVoting(@Valid @RequestBody Voting voting) throws URISyntaxException {
        log.debug("REST request to save Voting : {}", voting);
        if (voting.getId() != null) {
            throw new BadRequestAlertException("A new voting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Voting result = votingService.save(voting);
        return ResponseEntity.created(new URI("/api/votings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /votings} : Updates an existing voting.
     *
     * @param voting the voting to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated voting,
     * or with status {@code 400 (Bad Request)} if the voting is not valid,
     * or with status {@code 500 (Internal Server Error)} if the voting couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/votings")
    public ResponseEntity<Voting> updateVoting(@Valid @RequestBody Voting voting) throws URISyntaxException {
        log.debug("REST request to update Voting : {}", voting);
        if (voting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Voting result = votingService.save(voting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, voting.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /votings} : get all the votings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votings in body.
     */
    @GetMapping("/votings")
    public List<Voting> getAllVotings() {
        log.debug("REST request to get all Votings");
        return votingService.findAll();
    }

    /**
     * {@code GET  /votings/:id} : get the "id" voting.
     *
     * @param id the id of the voting to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the voting, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/votings/{id}")
    public ResponseEntity<Voting> getVoting(@PathVariable Long id) {
        log.debug("REST request to get Voting : {}", id);
        Optional<Voting> voting = votingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(voting);
    }

    /**
     * {@code DELETE  /votings/:id} : delete the "id" voting.
     *
     * @param id the id of the voting to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/votings/{id}")
    public ResponseEntity<Void> deleteVoting(@PathVariable Long id) {
        log.debug("REST request to delete Voting : {}", id);
        votingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
