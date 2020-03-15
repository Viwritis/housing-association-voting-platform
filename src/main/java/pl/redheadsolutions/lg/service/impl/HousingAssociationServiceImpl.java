package pl.redheadsolutions.lg.service.impl;

import pl.redheadsolutions.lg.service.HousingAssociationService;
import pl.redheadsolutions.lg.domain.HousingAssociation;
import pl.redheadsolutions.lg.repository.HousingAssociationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link HousingAssociation}.
 */
@Service
@Transactional
public class HousingAssociationServiceImpl implements HousingAssociationService {

    private final Logger log = LoggerFactory.getLogger(HousingAssociationServiceImpl.class);

    private final HousingAssociationRepository housingAssociationRepository;

    public HousingAssociationServiceImpl(HousingAssociationRepository housingAssociationRepository) {
        this.housingAssociationRepository = housingAssociationRepository;
    }

    /**
     * Save a housingAssociation.
     *
     * @param housingAssociation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HousingAssociation save(HousingAssociation housingAssociation) {
        log.debug("Request to save HousingAssociation : {}", housingAssociation);
        return housingAssociationRepository.save(housingAssociation);
    }

    /**
     * Get all the housingAssociations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HousingAssociation> findAll(Pageable pageable) {
        log.debug("Request to get all HousingAssociations");
        return housingAssociationRepository.findAll(pageable);
    }

    /**
     * Get one housingAssociation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HousingAssociation> findOne(Long id) {
        log.debug("Request to get HousingAssociation : {}", id);
        return housingAssociationRepository.findById(id);
    }

    /**
     * Delete the housingAssociation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete HousingAssociation : {}", id);
        housingAssociationRepository.deleteById(id);
    }
}
