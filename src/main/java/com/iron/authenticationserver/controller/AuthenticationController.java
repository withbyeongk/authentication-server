package com.iron.authenticationserver.controller;

import com.iron.authenticationserver.domain.Account;
import com.iron.authenticationserver.dto.AccountDTO;
import com.iron.authenticationserver.dto.ResponseDTO;
import com.iron.authenticationserver.service.AccountService;
import com.iron.authenticationserver.util.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "account")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AccountService accountService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> add(@Valid @RequestBody AccountDTO accountDTO) throws Exception {
		ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();
		Account account = accountService.findAccount(accountDTO);

		if (account != null) {
			responseBuilder.code("100").message("already add user.");
		} else {
			accountService.addAccount(accountDTO, null);
			responseBuilder.code("200").message("success");
		}

		log.debug("add.account.id= {}", accountDTO.getAccountId());

		return ResponseEntity.ok(responseBuilder.build());
	}

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> token(@Valid @RequestBody AccountDTO accountDTO) throws Exception {
		ResponseDTO.ResponseDTOBuilder responseBuilder = ResponseDTO.builder();
		Account account = accountService.findAccount(accountDTO);

		if (account == null) {
			responseBuilder.code("101").message("Unkown user.");
		} else {
			String token = getToken(accountDTO);
			accountService.addAccount(accountDTO, token);
			responseBuilder.code("200").message("success");
			responseBuilder.token(token);
		}

		log.debug("token.account.id = {}", accountDTO.getAccountId());

		return ResponseEntity.ok(responseBuilder.build());
	}

	private String getToken(AccountDTO accountDTO) {
		return JWTUtil.generate(accountDTO);
	}
}
