package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Income;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Income entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeRepository extends MongoRepository<Income, String> {

}
