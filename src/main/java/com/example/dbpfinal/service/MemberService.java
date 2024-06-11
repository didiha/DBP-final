package com.example.dbpfinal.service;

import com.example.dbpfinal.entity.Member;
import com.example.dbpfinal.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // Create
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // Read
    public Optional<Member> getMemberById(String memberId) {
        return memberRepository.findById(memberId);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Update
    public void updateMember(String memberId, String email, String password) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. memberId=" + memberId));

        member.updateEmailAndPassword(email, password);
        memberRepository.save(member);
    }

    // Delete
    public String deleteMember(String memberId, String memberPw) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) {
            return "Member ID does not exist.";
        }

        Member member = optionalMember.get();
        if (!member.getMemberPw().equals(memberPw)) {
            return "Incorrect password.";
        }

        memberRepository.deleteById(memberId);
        return "Member with ID " + memberId + " is deleted.";
    }

    // member id 중복 방지
    public boolean existsByMemberId(String memberId) {
        return memberRepository.existsById(memberId);
    }

    // email 중복 방지
    public boolean existsByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public Optional<Member> findMemberByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId);
    }

}
