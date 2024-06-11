package com.example.dbpfinal.repository;

import com.example.dbpfinal.entity.Bookmark;
import com.example.dbpfinal.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findById(Long bookmarkId);

    void deleteById(Long bookmarkId);

    List<Bookmark> findByMember_MemberId(String memberId);}
