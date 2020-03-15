package pl.redheadsolutions.lg.service.impl;

import pl.redheadsolutions.lg.service.VotingService;
import pl.redheadsolutions.lg.domain.Voting;
import pl.redheadsolutions.lg.repository.VotingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Voting}.
 */
@Service
@Transactional
public class VotingServiceImpl implements VotingService {

    private final Logger log = LoggerFactory.getLogger(VotingServiceImpl.class);

    private final VotingRepository votingRepository;

    public VotingServiceImpl(VotingRepository votingRepository) {
        this.votingRepository = votingRepository;
    }

    /**
     * Save a voting.
     *
     * @param voting the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Voting save(Voting voting) {
        log.debug("Request to save Voting : {}", voting);
        return votingRepository.save(voting);
    }

    /**
     * Get all the votings.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Voting> findAll() {
        log.debug("Request to get all Votings");
        return votingRepository.findAll();
    }

    /**
     * Get one voting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Voting> findOne(Long id) {
        log.debug("Request to get Voting : {}", id);
        return votingRepository.findById(id);
    }

    /**
     * Delete the voting by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Voting : {}", id);
        votingRepository.deleteById(id);
    }
}
