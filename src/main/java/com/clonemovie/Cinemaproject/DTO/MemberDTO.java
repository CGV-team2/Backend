package com.clonemovie.Cinemaproject.DTO;
import com.clonemovie.Cinemaproject.domain.Member;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

public class MemberDTO {

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MemberCreateRequest{
        private String name;
        private String user_id;
        private String password;
    }

    @Data
    public static class MemberLoginRequest {
        private String user_id;
        private String password;
    }

    @Data
    public static class MemberUpdateRequest {
        private String name;
        private String password;
        private String phoneNumber;
        private String email;
    }

    @Data
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ResponseMember{
        private String userId;
        private String name;
        private String phoneNumber;
        private String email;

        public ResponseMember(Member member) {
            this.userId = member.getUser_id();
            this.name = member.getName();
            this.phoneNumber = member.getPhone_number();
            this.email = member.getEmail();
        }
    }
}
