package com.example.dbpfinal.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Getter
@Setter
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @Column
    @NonNull
    private String bookmarkName;

;   @OneToMany(mappedBy = "bookmark")
    private List<BookmarkStore> bookmarkStores = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public String getBookmarkName() {
        return bookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public void updateBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }
}
