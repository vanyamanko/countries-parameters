package com.manko.phonecode.dao;

import com.manko.phonecode.model.PhoneCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PhoneCodeDAOImpl implements PhoneCodeDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PhoneCode> findByCountryOrId(String country, String id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PhoneCode> query = criteriaBuilder.createQuery(PhoneCode.class);
        Root<PhoneCode> root = query.from(PhoneCode.class);

        Predicate countryPredicate = criteriaBuilder.equal(criteriaBuilder.upper(root.get("country")), country.toUpperCase());
        Predicate idPredicate = criteriaBuilder.equal(criteriaBuilder.upper(root.get("id")), id.toUpperCase());
        Predicate orPredicate = criteriaBuilder.or(countryPredicate, idPredicate);

        query.select(root).where (orPredicate);

        TypedQuery<PhoneCode> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultStream()
                .findFirst();
    }

    @Override
    public Optional<List<PhoneCode>> findByCode(Integer code) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PhoneCode> query = criteriaBuilder.createQuery(PhoneCode.class);
        Root<PhoneCode> root = query.from(PhoneCode.class);

        query.select(root).where(criteriaBuilder.equal(root.get("code"), code));

        TypedQuery<PhoneCode> typedQuery = entityManager.createQuery(query);
        return Optional.ofNullable(typedQuery.getResultList());
    }
}
