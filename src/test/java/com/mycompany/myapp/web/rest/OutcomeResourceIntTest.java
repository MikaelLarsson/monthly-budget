package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MonthlyBudgetApp;

import com.mycompany.myapp.domain.Outcome;
import com.mycompany.myapp.repository.OutcomeRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OutcomeResource REST controller.
 *
 * @see OutcomeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MonthlyBudgetApp.class)
public class OutcomeResourceIntTest {

    private static final Float DEFAULT_AMOUNT = 1F;
    private static final Float UPDATED_AMOUNT = 2F;

    @Autowired
    private OutcomeRepository outcomeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restOutcomeMockMvc;

    private Outcome outcome;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OutcomeResource outcomeResource = new OutcomeResource(outcomeRepository);
        this.restOutcomeMockMvc = MockMvcBuilders.standaloneSetup(outcomeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outcome createEntity() {
        Outcome outcome = new Outcome()
            .amount(DEFAULT_AMOUNT);
        return outcome;
    }

    @Before
    public void initTest() {
        outcomeRepository.deleteAll();
        outcome = createEntity();
    }

    @Test
    public void createOutcome() throws Exception {
        int databaseSizeBeforeCreate = outcomeRepository.findAll().size();

        // Create the Outcome
        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outcome)))
            .andExpect(status().isCreated());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeCreate + 1);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    public void createOutcomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outcomeRepository.findAll().size();

        // Create the Outcome with an existing ID
        outcome.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outcome)))
            .andExpect(status().isBadRequest());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = outcomeRepository.findAll().size();
        // set the field null
        outcome.setAmount(null);

        // Create the Outcome, which fails.

        restOutcomeMockMvc.perform(post("/api/outcomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outcome)))
            .andExpect(status().isBadRequest());

        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllOutcomes() throws Exception {
        // Initialize the database
        outcomeRepository.save(outcome);

        // Get all the outcomeList
        restOutcomeMockMvc.perform(get("/api/outcomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outcome.getId())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }
    
    @Test
    public void getOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.save(outcome);

        // Get the outcome
        restOutcomeMockMvc.perform(get("/api/outcomes/{id}", outcome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(outcome.getId()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    public void getNonExistingOutcome() throws Exception {
        // Get the outcome
        restOutcomeMockMvc.perform(get("/api/outcomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.save(outcome);

        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Update the outcome
        Outcome updatedOutcome = outcomeRepository.findById(outcome.getId()).get();
        updatedOutcome
            .amount(UPDATED_AMOUNT);

        restOutcomeMockMvc.perform(put("/api/outcomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutcome)))
            .andExpect(status().isOk());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
        Outcome testOutcome = outcomeList.get(outcomeList.size() - 1);
        assertThat(testOutcome.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    public void updateNonExistingOutcome() throws Exception {
        int databaseSizeBeforeUpdate = outcomeRepository.findAll().size();

        // Create the Outcome

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutcomeMockMvc.perform(put("/api/outcomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outcome)))
            .andExpect(status().isBadRequest());

        // Validate the Outcome in the database
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteOutcome() throws Exception {
        // Initialize the database
        outcomeRepository.save(outcome);

        int databaseSizeBeforeDelete = outcomeRepository.findAll().size();

        // Get the outcome
        restOutcomeMockMvc.perform(delete("/api/outcomes/{id}", outcome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Outcome> outcomeList = outcomeRepository.findAll();
        assertThat(outcomeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Outcome.class);
        Outcome outcome1 = new Outcome();
        outcome1.setId("id1");
        Outcome outcome2 = new Outcome();
        outcome2.setId(outcome1.getId());
        assertThat(outcome1).isEqualTo(outcome2);
        outcome2.setId("id2");
        assertThat(outcome1).isNotEqualTo(outcome2);
        outcome1.setId(null);
        assertThat(outcome1).isNotEqualTo(outcome2);
    }
}
