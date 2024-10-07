package com.clonemovie.Cinemaproject.repository;

import com.clonemovie.Cinemaproject.domain.Member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);

    Member findById(Long id);

    Member findByUserId(String userid);

    List<Member> findAll();

    void deleteParents(Member member);

    List<Member> findByName(String name);
}
