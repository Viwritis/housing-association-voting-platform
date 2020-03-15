package pl.redheadsolutions.lg.service.impl;

import pl.redheadsolutions.lg.service.InhabitantService;
import pl.redheadsolutions.lg.domain.Inhabitant;
import pl.redheadsolutions.lg.repository.InhabitantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Inhabitant}.
 */
@Service
@Transactional
public class InhabitantServiceImpl implements InhabitantService {

    private final Logger log = LoggerFactory.getLogger(InhabitantServiceImpl.class);

    private final InhabitantRepository inhabitantRepository;

    public InhabitantServiceImpl(InhabitantRepository inhabitantRepository) {
        this.inhabitantRepository = inhabitantRepository;
    }

    /**
     * Save a inhabitant.
     *
     * @param inhabitant the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Inhabitant save(Inhabitant inhabitant) {
        log.debug("Request to save Inhabitant : {}", inhabitant);
        return inhabitantRepository.save(inhabitant);
    }

    /**
     * Get all the inhabitants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Inhabitant> findAll(Pageable pageable) {
        log.debug("Request to get all Inhabitants");
        return inhabitantRepository.findAll(pageable);
    }

    /**
     * Get one inhabitant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Inhabitant> findOne(Long id) {
        log.debug("Request to get Inhabitant : {}", id);
        return inhabitantRepository.findById(id);
    }

    /**
     * Delete the inhabitant by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inhabitant : {}", id);
        inhabitantRepository.deleteById(id);
    }
}
