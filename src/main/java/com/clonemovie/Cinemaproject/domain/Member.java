package com.clonemovie.Cinemaproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id @GeneratedValue
    private long id;

    @Column(unique = true)
    private String user_id;

    @Setter
    private String name;
    private String password;

    @Setter
    private String phone_number;
    @Setter
    private String email;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Member(String userid, String password, String name) {
        this.user_id = userid;
        this.setPassword(password);
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = passwordEncoding(password);
    }

    public String passwordEncoding (String password) {
        return passwordEncoder.encode(password);
    }

    public boolean checkPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.password);
    }
}
