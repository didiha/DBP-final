package com.example.dbpfinal.service;

import com.example.dbpfinal.entity.Bookmark;
import com.example.dbpfinal.entity.BookmarkStore;
import com.example.dbpfinal.entity.Member;
import com.example.dbpfinal.entity.Store;
import com.example.dbpfinal.repository.BookmarkRepository;
import com.example.dbpfinal.repository.BookmarkStoreRepository;

import java.util.*;


import com.example.dbpfinal.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final StoreRepository storeRepository;
    private final BookmarkStoreRepository bookmarkStoreRepository;

    // 생성자
    @Autowired
    public BookmarkService(BookmarkRepository bookmarkRepository,
                           StoreRepository storeRepository, BookmarkStoreRepository bookmarkStoreRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.storeRepository = storeRepository;
        this.bookmarkStoreRepository = bookmarkStoreRepository;

    }

    // 즐겨찾기 생성
    public Bookmark createBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    // 즐겨찾기 읽어오기

    public List<Bookmark> getBookmarksByMemberId(String memberId) {
        return bookmarkRepository.findByMember_MemberId(memberId);
    }

    public Optional<Bookmark> findBookmarkById(Long bookmarkId) {
        return bookmarkRepository.findById(bookmarkId);
    }

    public List<Bookmark> getBookmarksByMasterMember() {
        return getBookmarksByMemberId("root");
    }

    // 즐겨찾기 이름 수정
    public void updateBookmark(Long bookmarkId, String bookmarkName) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new IllegalArgumentException("해당 즐겨찾기가 없습니다. bookmarkId=" + bookmarkId));

        bookmark.updateBookmarkName(bookmarkName);
        bookmarkRepository.save(bookmark);
    }

    // 즐겨찾기 삭제
    @Transactional
    public void deleteBookmarkAndRelatedStores(Long bookmarkId) {
        // 먼저 BookmarkStore 인스턴스 삭제
        bookmarkStoreRepository.deleteByBookmark_BookmarkId(bookmarkId);
        // 그 다음 Bookmark 인스턴스 삭제
        bookmarkRepository.deleteById(bookmarkId);
    }

    // 즐겨찾기에 상점 추가
    @Transactional
    public void addStoreToBookmark(Long bookmarkId, Long storeId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NoSuchElementException("Bookmark not found"));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("Store not found"));

        Optional<BookmarkStore> bookmarkStoreOptional = bookmarkStoreRepository.findByBookmarkAndStore(bookmark, store);
        if (bookmarkStoreOptional.isPresent()) {
            throw new IllegalArgumentException("Store already added to this bookmark");
        }

        BookmarkStore bookmarkStore = new BookmarkStore();
        bookmarkStore.setBookmark(bookmark);
        bookmarkStore.setStore(store);

        bookmarkStoreRepository.save(bookmarkStore);
    }


    // 즐겨찾기에서 상점 삭제
    @Transactional
    public void removeStoreFromBookmark(Long bookmarkId, Long storeId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NoSuchElementException("Bookmark not found"));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NoSuchElementException("Store not found"));

        BookmarkStore bookmarkStore = bookmarkStoreRepository.findByBookmarkAndStore(bookmark, store)
                .orElseThrow(() -> new NoSuchElementException("Store not found in this bookmark"));

        bookmarkStoreRepository.delete(bookmarkStore);
    }

}
