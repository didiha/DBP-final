package com.example.dbpfinal.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {
    @Id
    private String memberId;

    @Column
    private String memberPw;

    @Column
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Bookmark> bookmarks = new ArrayList<>();

    public void updateEmailAndPassword(String email, String memberPw) {
        this.email = email;
        this.memberPw = memberPw;
    }
}
