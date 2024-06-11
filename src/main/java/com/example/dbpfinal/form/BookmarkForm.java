package com.example.dbpfinal.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class BookmarkForm {
    @NotBlank(message = "제목을 작성해주세요.")
    @Size(max=200)
    private String bookmarkName;

    public BookmarkForm() {}

    @JsonCreator // Jackson 라이브러리가 이 생성자를 사용하여 객체를 생성하도록 지시
    public BookmarkForm(@JsonProperty("bookmarkName") String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }
}
