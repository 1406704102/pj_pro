package com.example.sharding.config;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Repository
public class CriteriaNoCountDao {

    @PersistenceContext
    protected EntityManager em;

    public <T, I extends Serializable> Slice<T> findAll(final Specification<T> spec, final Pageable pageable,
                                                        final Class<T> domainClass) {
        final SimpleJpaNoCountRepository<T, I> noCountDao = new SimpleJpaNoCountRepository<> (domainClass, em);
        return noCountDao.findAll (spec, pageable);
    }

    /**
     * Custom repository type that disable count query.
     */
    public static class SimpleJpaNoCountRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> {

        public SimpleJpaNoCountRepository(Class<T> domainClass, EntityManager em) {
            super (domainClass, em);
        }

        @Override
        protected <S extends T> Page<S> readPage(TypedQuery<S> query, Class<S> domainClass, Pageable pageable, Specification<S> spec) {
            query.setFirstResult ((int) pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());
            final List<S> content = query.getResultList ();
            return new PageImpl<>(content, pageable, content.size());
        }
    }
}
