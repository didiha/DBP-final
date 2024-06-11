package com.example.dbpfinal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class BookmarkStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkStoreId;

    @ManyToOne
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
