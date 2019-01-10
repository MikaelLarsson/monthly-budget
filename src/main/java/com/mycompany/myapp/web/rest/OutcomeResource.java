package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Outcome;
import com.mycompany.myapp.repository.OutcomeRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Outcome.
 */
@RestController
@RequestMapping("/api")
public class OutcomeResource {

    private final Logger log = LoggerFactory.getLogger(OutcomeResource.class);

    private static final String ENTITY_NAME = "outcome";

    private final OutcomeRepository outcomeRepository;

    public OutcomeResource(OutcomeRepository outcomeRepository) {
        this.outcomeRepository = outcomeRepository;
    }

    /**
     * POST  /outcomes : Create a new outcome.
     *
     * @param outcome the outcome to create
     * @return the ResponseEntity with status 201 (Created) and with body the new outcome, or with status 400 (Bad Request) if the outcome has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/outcomes")
    @Timed
    public ResponseEntity<Outcome> createOutcome(@Valid @RequestBody Outcome outcome) throws URISyntaxException {
        log.debug("REST request to save Outcome : {}", outcome);
        if (outcome.getId() != null) {
            throw new BadRequestAlertException("A new outcome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Outcome result = outcomeRepository.save(outcome);
        return ResponseEntity.created(new URI("/api/outcomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /outcomes : Updates an existing outcome.
     *
     * @param outcome the outcome to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated outcome,
     * or with status 400 (Bad Request) if the outcome is not valid,
     * or with status 500 (Internal Server Error) if the outcome couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/outcomes")
    @Timed
    public ResponseEntity<Outcome> updateOutcome(@Valid @RequestBody Outcome outcome) throws URISyntaxException {
        log.debug("REST request to update Outcome : {}", outcome);
        if (outcome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Outcome result = outcomeRepository.save(outcome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, outcome.getId().toString()))
            .body(result);
    }

    /**
     * GET  /outcomes : get all the outcomes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of outcomes in body
     */
    @GetMapping("/outcomes")
    @Timed
    public List<Outcome> getAllOutcomes() {
        log.debug("REST request to get all Outcomes");
        return outcomeRepository.findAll();
    }

    /**
     * GET  /outcomes/:id : get the "id" outcome.
     *
     * @param id the id of the outcome to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the outcome, or with status 404 (Not Found)
     */
    @GetMapping("/outcomes/{id}")
    @Timed
    public ResponseEntity<Outcome> getOutcome(@PathVariable String id) {
        log.debug("REST request to get Outcome : {}", id);
        Optional<Outcome> outcome = outcomeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(outcome);
    }

    /**
     * DELETE  /outcomes/:id : delete the "id" outcome.
     *
     * @param id the id of the outcome to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/outcomes/{id}")
    @Timed
    public ResponseEntity<Void> deleteOutcome(@PathVariable String id) {
        log.debug("REST request to delete Outcome : {}", id);

        outcomeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
