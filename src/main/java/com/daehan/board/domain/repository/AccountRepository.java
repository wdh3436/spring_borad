package com.daehan.board.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.daehan.board.domain.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
	AccountEntity findByUsername(String username);
}