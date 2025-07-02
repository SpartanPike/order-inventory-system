package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Item;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ItemCriteriaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Item> findItemsByPriceGreaterThan(double price) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> item = cq.from(Item.class);

        cq.select(item).where(cb.greaterThan(item.get("price"), price));

        TypedQuery<Item> query = em.createQuery(cq);
        return query.getResultList();
    }
}
