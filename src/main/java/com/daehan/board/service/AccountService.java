package com.daehan.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.daehan.board.domain.entity.AccountEntity;
import com.daehan.board.domain.repository.AccountRepository;
import com.daehan.board.dto.AccountDto;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
	private final AccountRepository accountRepository;

    @Transactional
    public Long createUser(AccountDto Dto) {
        AccountEntity accountEntity = Dto.toEntity();
        accountRepository.save(accountEntity);
        return accountEntity.getId();
    }
}
