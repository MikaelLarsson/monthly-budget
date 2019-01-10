package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Budget.
 */
@Document(collection = "budget")
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @DBRef
    @Field("income")
    private Set<Income> incomes = new HashSet<>();
    @DBRef
    @Field("outcome")
    private Set<Outcome> outcomes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Budget title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Budget description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Income> getIncomes() {
        return incomes;
    }

    public Budget incomes(Set<Income> incomes) {
        this.incomes = incomes;
        return this;
    }

    public Budget addIncome(Income income) {
        this.incomes.add(income);
        income.setBudget(this);
        return this;
    }

    public Budget removeIncome(Income income) {
        this.incomes.remove(income);
        income.setBudget(null);
        return this;
    }

    public void setIncomes(Set<Income> incomes) {
        this.incomes = incomes;
    }

    public Set<Outcome> getOutcomes() {
        return outcomes;
    }

    public Budget outcomes(Set<Outcome> outcomes) {
        this.outcomes = outcomes;
        return this;
    }

    public Budget addOutcome(Outcome outcome) {
        this.outcomes.add(outcome);
        outcome.setBudget(this);
        return this;
    }

    public Budget removeOutcome(Outcome outcome) {
        this.outcomes.remove(outcome);
        outcome.setBudget(null);
        return this;
    }

    public void setOutcomes(Set<Outcome> outcomes) {
        this.outcomes = outcomes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Budget budget = (Budget) o;
        if (budget.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), budget.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Budget{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
