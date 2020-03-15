package pl.redheadsolutions.lg.web.rest;

import pl.redheadsolutions.lg.domain.HousingAssociation;
import pl.redheadsolutions.lg.service.HousingAssociationService;
import pl.redheadsolutions.lg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.redheadsolutions.lg.domain.HousingAssociation}.
 */
@RestController
@RequestMapping("/api")
public class HousingAssociationResource {

    private final Logger log = LoggerFactory.getLogger(HousingAssociationResource.class);

    private static final String ENTITY_NAME = "housingAssociation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HousingAssociationService housingAssociationService;

    public HousingAssociationResource(HousingAssociationService housingAssociationService) {
        this.housingAssociationService = housingAssociationService;
    }

    /**
     * {@code POST  /housing-associations} : Create a new housingAssociation.
     *
     * @param housingAssociation the housingAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new housingAssociation, or with status {@code 400 (Bad Request)} if the housingAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/housing-associations")
    public ResponseEntity<HousingAssociation> createHousingAssociation(@Valid @RequestBody HousingAssociation housingAssociation) throws URISyntaxException {
        log.debug("REST request to save HousingAssociation : {}", housingAssociation);
        if (housingAssociation.getId() != null) {
            throw new BadRequestAlertException("A new housingAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HousingAssociation result = housingAssociationService.save(housingAssociation);
        return ResponseEntity.created(new URI("/api/housing-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /housing-associations} : Updates an existing housingAssociation.
     *
     * @param housingAssociation the housingAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingAssociation,
     * or with status {@code 400 (Bad Request)} if the housingAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the housingAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/housing-associations")
    public ResponseEntity<HousingAssociation> updateHousingAssociation(@Valid @RequestBody HousingAssociation housingAssociation) throws URISyntaxException {
        log.debug("REST request to update HousingAssociation : {}", housingAssociation);
        if (housingAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HousingAssociation result = housingAssociationService.save(housingAssociation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingAssociation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /housing-associations} : get all the housingAssociations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of housingAssociations in body.
     */
    @GetMapping("/housing-associations")
    public ResponseEntity<List<HousingAssociation>> getAllHousingAssociations(Pageable pageable) {
        log.debug("REST request to get a page of HousingAssociations");
        Page<HousingAssociation> page = housingAssociationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /housing-associations/:id} : get the "id" housingAssociation.
     *
     * @param id the id of the housingAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the housingAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/housing-associations/{id}")
    public ResponseEntity<HousingAssociation> getHousingAssociation(@PathVariable Long id) {
        log.debug("REST request to get HousingAssociation : {}", id);
        Optional<HousingAssociation> housingAssociation = housingAssociationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(housingAssociation);
    }

    /**
     * {@code DELETE  /housing-associations/:id} : delete the "id" housingAssociation.
     *
     * @param id the id of the housingAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/housing-associations/{id}")
    public ResponseEntity<Void> deleteHousingAssociation(@PathVariable Long id) {
        log.debug("REST request to delete HousingAssociation : {}", id);
        housingAssociationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
