package com.clonemovie.Cinemaproject.service;

import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.exception.IdNotFoundException;
import com.clonemovie.Cinemaproject.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtility jwtUtility;

    public Member tokenToMember(String token){
        return memberRepository.findByUserId(jwtUtility.validateToken(token).getSubject());
    }

    @Transactional
    public Member changeInfo(String token, String name, String password, String phoneNumber, String email){
        Member member = tokenToMember(token);
        if(member==null) return null;
        member.setPassword(password);
        member.setName(name);
        member.setPhone_number(phoneNumber);
        member.setEmail(email);
        return member;
    }

    @Transactional
    public Member signUp(String userId, String password, String name, String date_of_birth){
        Member member = memberRepository.findByUserId(userId);
        if(member!=null) return null;
        return memberRepository.save(new Member(userId, password, name, date_of_birth));
    }

    public Member findById(Long id){
        return memberRepository.findById(id);
    }

    @Transactional
    public String login(String userId, String passwd){
        Member member = memberRepository.findByUserId(userId);
        if(member != null && member.checkPassword(passwd)) {
            return jwtUtility.generateToken(userId);
        }
        return null;
    }

    public Member findByUserId(String userId){
        Member member = memberRepository.findByUserId(userId);
        if(member==null) throw new IdNotFoundException();
        return member;
    }

    public List<Member> findByName(String name){
        return memberRepository.findByName(name);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public boolean deleteParents(String token){
        Member member = tokenToMember(token);
        if(member==null){
            return false;
        }
        memberRepository.deleteParents(member);
        return true;
    }
}