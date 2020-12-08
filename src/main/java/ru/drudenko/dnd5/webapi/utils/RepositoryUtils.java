package ru.drudenko.dnd5.webapi.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;

public class RepositoryUtils<T> {
    private final EntityManager em;
    private final Class domainClass;

    public RepositoryUtils(EntityManager em, Class domainClass) {
        this.em = em;
        this.domainClass = domainClass;
    }

    public static long executeCountQuery(TypedQuery<Long> query) {

        Assert.notNull(query, "TypedQuery must not be null!");

        var totals = query.getResultList();
        long total = 0L;

        for (var element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    public Page<T> findAll(Specification<T> specification, Pageable pageRequest) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(domainClass);
        var root = query.from(domainClass);
        var predicate = specification.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        query.select(root);
        query.orderBy(QueryUtils.toOrders(pageRequest.getSort(), root, builder));
        em.createQuery(query);

        return PageableExecutionUtils.getPage(em.createQuery(query)
                        .setHint("org.hibernate.cacheable", true)
                        .setFirstResult((int) pageRequest.getOffset())
                        .setMaxResults(pageRequest.getPageSize())
                        .getResultList(), pageRequest,
                () -> executeCountQuery(getCountQuery(specification, domainClass)));
    }

    protected <S extends T> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = applySpecificationToCriteria(spec, domainClass, query);
        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }
        query.orderBy(Collections.emptyList());
        return em.createQuery(query);
    }

    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable Specification<U> spec, Class<U> domainClass, CriteriaQuery<S> query) {

        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        var root = query.from(domainClass);

        if (spec == null) {
            return root;
        }

        var builder = em.getCriteriaBuilder();
        var predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

}
