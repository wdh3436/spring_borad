package com.daehan.board.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.daehan.board.domain.entity.AccountEntity;

public class AccountContext extends User {

    private final AccountEntity accountEntity;

    public AccountContext(AccountEntity accountEntity, Collection<? extends GrantedAuthority> authorities) {
        super(accountEntity.getUsername(), accountEntity.getPassword(), authorities);
        this.accountEntity = accountEntity;
    }

    public AccountEntity getAccount() {
        return accountEntity;
    }
}