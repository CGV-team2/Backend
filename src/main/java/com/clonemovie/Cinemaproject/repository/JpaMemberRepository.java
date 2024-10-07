package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class JpaMemberRepository implements MemberRepository {
    private final EntityManager em;

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public Member findByUserId(String userId) {
        try {
            return em.createQuery("select m from Member m where m.user_id = :user_id", Member.class)
                    .setParameter("user_id", userId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    @Override
    public void deleteParents(Member member) {
        em.remove(member);
    }

    @Override
    public List<Member> findByName(String name) {
        return em.createQuery("select p from Member p where p.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
