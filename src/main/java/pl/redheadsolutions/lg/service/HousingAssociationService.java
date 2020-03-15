package pl.redheadsolutions.lg.service;

import pl.redheadsolutions.lg.domain.HousingAssociation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link HousingAssociation}.
 */
public interface HousingAssociationService {

    /**
     * Save a housingAssociation.
     *
     * @param housingAssociation the entity to save.
     * @return the persisted entity.
     */
    HousingAssociation save(HousingAssociation housingAssociation);

    /**
     * Get all the housingAssociations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HousingAssociation> findAll(Pageable pageable);

    /**
     * Get the "id" housingAssociation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HousingAssociation> findOne(Long id);

    /**
     * Delete the "id" housingAssociation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
