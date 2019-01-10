package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Income;
import com.mycompany.myapp.repository.IncomeRepository;
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
 * REST controller for managing Income.
 */
@RestController
@RequestMapping("/api")
public class IncomeResource {

    private final Logger log = LoggerFactory.getLogger(IncomeResource.class);

    private static final String ENTITY_NAME = "income";

    private final IncomeRepository incomeRepository;

    public IncomeResource(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    /**
     * POST  /incomes : Create a new income.
     *
     * @param income the income to create
     * @return the ResponseEntity with status 201 (Created) and with body the new income, or with status 400 (Bad Request) if the income has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/incomes")
    @Timed
    public ResponseEntity<Income> createIncome(@Valid @RequestBody Income income) throws URISyntaxException {
        log.debug("REST request to save Income : {}", income);
        if (income.getId() != null) {
            throw new BadRequestAlertException("A new income cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Income result = incomeRepository.save(income);
        return ResponseEntity.created(new URI("/api/incomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incomes : Updates an existing income.
     *
     * @param income the income to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated income,
     * or with status 400 (Bad Request) if the income is not valid,
     * or with status 500 (Internal Server Error) if the income couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/incomes")
    @Timed
    public ResponseEntity<Income> updateIncome(@Valid @RequestBody Income income) throws URISyntaxException {
        log.debug("REST request to update Income : {}", income);
        if (income.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Income result = incomeRepository.save(income);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, income.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incomes : get all the incomes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of incomes in body
     */
    @GetMapping("/incomes")
    @Timed
    public List<Income> getAllIncomes() {
        log.debug("REST request to get all Incomes");
        return incomeRepository.findAll();
    }

    /**
     * GET  /incomes/:id : get the "id" income.
     *
     * @param id the id of the income to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the income, or with status 404 (Not Found)
     */
    @GetMapping("/incomes/{id}")
    @Timed
    public ResponseEntity<Income> getIncome(@PathVariable String id) {
        log.debug("REST request to get Income : {}", id);
        Optional<Income> income = incomeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(income);
    }

    /**
     * DELETE  /incomes/:id : delete the "id" income.
     *
     * @param id the id of the income to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/incomes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIncome(@PathVariable String id) {
        log.debug("REST request to delete Income : {}", id);

        incomeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
