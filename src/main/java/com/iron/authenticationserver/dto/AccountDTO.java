package com.iron.authenticationserver.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDTO {

	@NotBlank(message = "ID는 필수 입력입니다.")
	@Size(max = 10, message = "ID 길이는 10까지입니다.")
	private String accountId;

	@NotBlank(message = "비밀번호는 필수 입력입니다.")
	private String accountPw;

	private String token;
}
