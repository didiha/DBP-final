package com.example.dbpfinal.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class MemberForm {
    @NotBlank(message = "id를 입력해주세요.")
    private String memberId;

    @NotBlank(message = "pw를 입력해주세요.")
    private String memberPw;

    @Email
    @NotBlank(message = "email을 입력해주세요.")
    private String email;

    public MemberForm() {
    }

    @JsonCreator
    public MemberForm(
            @JsonProperty("memberId") String memberId,
            @JsonProperty("memberPw") String memberPw,
            @JsonProperty("email") String email) {
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.email = email;
    }
}
