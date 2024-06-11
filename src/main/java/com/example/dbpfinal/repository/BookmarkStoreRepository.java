package com.example.dbpfinal.repository;

import com.example.dbpfinal.entity.Bookmark;
import com.example.dbpfinal.entity.BookmarkStore;
import com.example.dbpfinal.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookmarkStoreRepository extends JpaRepository<BookmarkStore, Long> {
    Optional<BookmarkStore> findByBookmarkAndStore(Bookmark bookmark, Store store);

    void deleteByBookmark_BookmarkId(Long bookmarkId);
}
