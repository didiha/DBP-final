package com.example.dbpfinal.repository;

import com.example.dbpfinal.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByMemberId(String memberId);
}
