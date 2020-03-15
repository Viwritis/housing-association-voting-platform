package pl.redheadsolutions.lg.web.rest;

import pl.redheadsolutions.lg.domain.Inhabitant;
import pl.redheadsolutions.lg.service.InhabitantService;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pl.redheadsolutions.lg.domain.Inhabitant}.
 */
@RestController
@RequestMapping("/api")
public class InhabitantResource {

    private final Logger log = LoggerFactory.getLogger(InhabitantResource.class);

    private static final String ENTITY_NAME = "inhabitant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InhabitantService inhabitantService;

    public InhabitantResource(InhabitantService inhabitantService) {
        this.inhabitantService = inhabitantService;
    }

    /**
     * {@code POST  /inhabitants} : Create a new inhabitant.
     *
     * @param inhabitant the inhabitant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inhabitant, or with status {@code 400 (Bad Request)} if the inhabitant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inhabitants")
    public ResponseEntity<Inhabitant> createInhabitant(@RequestBody Inhabitant inhabitant) throws URISyntaxException {
        log.debug("REST request to save Inhabitant : {}", inhabitant);
        if (inhabitant.getId() != null) {
            throw new BadRequestAlertException("A new inhabitant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inhabitant result = inhabitantService.save(inhabitant);
        return ResponseEntity.created(new URI("/api/inhabitants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inhabitants} : Updates an existing inhabitant.
     *
     * @param inhabitant the inhabitant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inhabitant,
     * or with status {@code 400 (Bad Request)} if the inhabitant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inhabitant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inhabitants")
    public ResponseEntity<Inhabitant> updateInhabitant(@RequestBody Inhabitant inhabitant) throws URISyntaxException {
        log.debug("REST request to update Inhabitant : {}", inhabitant);
        if (inhabitant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Inhabitant result = inhabitantService.save(inhabitant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inhabitant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /inhabitants} : get all the inhabitants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inhabitants in body.
     */
    @GetMapping("/inhabitants")
    public ResponseEntity<List<Inhabitant>> getAllInhabitants(Pageable pageable) {
        log.debug("REST request to get a page of Inhabitants");
        Page<Inhabitant> page = inhabitantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inhabitants/:id} : get the "id" inhabitant.
     *
     * @param id the id of the inhabitant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inhabitant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inhabitants/{id}")
    public ResponseEntity<Inhabitant> getInhabitant(@PathVariable Long id) {
        log.debug("REST request to get Inhabitant : {}", id);
        Optional<Inhabitant> inhabitant = inhabitantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inhabitant);
    }

    /**
     * {@code DELETE  /inhabitants/:id} : delete the "id" inhabitant.
     *
     * @param id the id of the inhabitant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inhabitants/{id}")
    public ResponseEntity<Void> deleteInhabitant(@PathVariable Long id) {
        log.debug("REST request to delete Inhabitant : {}", id);
        inhabitantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
