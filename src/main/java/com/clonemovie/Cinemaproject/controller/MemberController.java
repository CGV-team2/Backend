package com.clonemovie.Cinemaproject.controller;

import com.clonemovie.Cinemaproject.DTO.MemberDTO.*;
import com.clonemovie.Cinemaproject.DTO.SeatDTO;
import com.clonemovie.Cinemaproject.domain.Member;
import com.clonemovie.Cinemaproject.domain.Seat;
import com.clonemovie.Cinemaproject.service.MemberService;
import com.clonemovie.Cinemaproject.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final SeatService seatService;

    @PostMapping("/Member/signup")
    public String signUp(@RequestBody MemberCreateRequest request){
        Member member = memberService.signUp(
                request.getUser_id(),
                request.getPassword(),
                request.getName());
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

    @GetMapping("/Member/seats")
    public ResponseEntity<List<SeatDTO.ResponseSeat>> getMemberReservations(
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader;

        // 토큰을 통해 회원 정보를 가져옴
        Member member = memberService.tokenToMember(token);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }

        // 해당 회원의 예약 내역 조회
        List<Seat> reservations = seatService.getSeatByMember(member);

        // DTO로 변환하여 응답
        List<SeatDTO.ResponseSeat> response = reservations.stream()
                .map(SeatDTO.ResponseSeat::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
    }
}