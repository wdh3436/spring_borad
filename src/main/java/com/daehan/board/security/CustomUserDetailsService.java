package com.daehan.board.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daehan.board.domain.entity.AccountEntity;
import com.daehan.board.domain.repository.AccountRepository;

import lombok.AllArgsConstructor;


//DB에 연동되게끔 하는 곳
@AllArgsConstructor
@Service("userDetailsService")  // 빈 등록하기
public class CustomUserDetailsService implements UserDetailsService {
	
	private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountEntity accountEntity = accountRepository.findByUsername(username);

        if(accountEntity == null){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(accountEntity.getRole()));

        AccountContext accountContext = new AccountContext(accountEntity,roles);

        return accountContext;
    }

}
