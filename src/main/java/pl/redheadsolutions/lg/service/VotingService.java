package pl.redheadsolutions.lg.service;

import pl.redheadsolutions.lg.domain.Voting;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Voting}.
 */
public interface VotingService {

    /**
     * Save a voting.
     *
     * @param voting the entity to save.
     * @return the persisted entity.
     */
    Voting save(Voting voting);

    /**
     * Get all the votings.
     *
     * @return the list of entities.
     */
    List<Voting> findAll();

    /**
     * Get the "id" voting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Voting> findOne(Long id);

    /**
     * Delete the "id" voting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
