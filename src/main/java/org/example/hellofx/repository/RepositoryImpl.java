package org.example.hellofx.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.example.hellofx.model.enums.GenderType;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositoryImpl<T>{
    @PersistenceContext
    private EntityManager entityManager;

    public List<T> executeRawSql(String sqlQuery, Class<T> type) {
        return entityManager.createNativeQuery(sqlQuery, type).getResultList();
    }

}
