package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.MemberDTO.*;
import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/Member/signup")
    public String signUp(@RequestBody MemberCreateRequest request){
        Member member = memberService.signUp(
                request.getUser_id(),
                request.getPassword(),
                request.getName(),
                request.getDate_of_birth());
        if(member == null) return "이미 존재";
        return memberService.login(request.getUser_id(), request.getPassword());
    }

    @PostMapping("/Member/signin")
    public String login(@RequestBody MemberLoginRequest request) {
        return memberService.login(request.getUser_id(),request.getPassword());
    }

    @GetMapping("/Member/{userId}")
    public ResponseMember getMember (@PathVariable("userId") String userId) {
        Member Member = memberService.findByUserId(userId);
        if(Member == null) return null;
        return new ResponseMember(Member);
    }

    @PutMapping("/Member/info")
    public ResponseMember changeMemberInfo(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody MemberUpdateRequest request) {
        String token = authorizationHeader;
        Member findMember  = memberService.changeInfo(
                token,
                request.getName(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getEmail());
        return new ResponseMember(findMember);
    }

    @DeleteMapping("/Member")
    public boolean deleteMember(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader;
        return memberService.deleteParents(token);
    }
}