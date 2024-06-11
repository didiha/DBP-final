package com.example.dbpfinal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column
    private String businessType;

    @Column
    private String storeName;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    @Column
    private String dbDate;

    @OneToMany(mappedBy = "store")
    private List<BookmarkStore> bookmarkStores = new ArrayList<>();
}
