package pl.redheadsolutions.lg.service;

import pl.redheadsolutions.lg.domain.Inhabitant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Inhabitant}.
 */
public interface InhabitantService {

    /**
     * Save a inhabitant.
     *
     * @param inhabitant the entity to save.
     * @return the persisted entity.
     */
    Inhabitant save(Inhabitant inhabitant);

    /**
     * Get all the inhabitants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Inhabitant> findAll(Pageable pageable);

    /**
     * Get the "id" inhabitant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Inhabitant> findOne(Long id);

    /**
     * Delete the "id" inhabitant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
