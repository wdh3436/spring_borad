package com.daehan.board.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.daehan.board.domain.entity.AccountEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {
	private Long id;
    private String username;
    private String password;
    private String email;
    private String age;
    private String role;

    @Builder
    public AccountDto(Long id, String username, String password, String email, String age, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.role = role;
    }

    public AccountEntity toEntity(){
        return AccountEntity.builder()
                .id(id)
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .email(email)
                .age(age)
                .role(role)
                .build();
    }
}
