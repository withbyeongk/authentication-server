package com.iron.authenticationserver.service;

import com.iron.authenticationserver.domain.Account;
import com.iron.authenticationserver.dto.AccountDTO;
import com.iron.authenticationserver.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public Account findAccount(AccountDTO accountDto) {
		Optional<Account> optional = accountRepository.findById(accountDto.getAccountId());

		if (optional.isPresent()) {
			Account account = optional.get();

			if (account.getAccountPw().equals(accountDto.getAccountPw())) {
				return account;
			}
		}
		return null;
	}

	public void addAccount(AccountDTO accountDTO, String token) {
		accountRepository.save(Account.builder()
				.accountId(accountDTO.getAccountId())
				.accountPw(accountDTO.getAccountPw())
				.token(token)
				.build());
	}
}
