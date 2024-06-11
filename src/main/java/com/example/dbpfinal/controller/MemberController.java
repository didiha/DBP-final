package com.example.dbpfinal.controller;

import com.example.dbpfinal.entity.Member;
import com.example.dbpfinal.form.MemberForm;
import com.example.dbpfinal.service.MemberService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // Create
    @PostMapping("/create")
    public String createMember(@Valid @ModelAttribute("memberCreateForm") MemberForm memberForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // 폼 검증에 실패한 경우, 사용자에게 에러 메시지를 표시할 수 있습니다.
            // 여기서는 BindingResult를 사용해 에러를 처리합니다.
            return "signup"; // 폼을 포함하고 있는 뷰의 이름을 반환합니다.
        }

        Member member = new Member();
        member.setMemberId(memberForm.getMemberId());
        if (memberService.existsByMemberId(member.getMemberId())) {
            // 에러 메시지를 RedirectAttributes를 통해 전달할 수 있습니다.
            redirectAttributes.addFlashAttribute("errorMessage", "이미 있는 아이디 입니다");
            return "redirect:/member/signup"; // 사용자 등록 폼으로 리다이렉트합니다.
        }
        if (memberService.existsByEmail(member.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 있는 이메일 입니다");
            return "redirect:/member/signup"; // 사용자 등록 폼으로 리다이렉트합니다.
        }

        member.setMemberPw(memberForm.getMemberPw());
        member.setEmail(memberForm.getEmail());
        Member createdMember = memberService.createMember(member);

        // 회원가입에 성공한 경우, 원하는 페이지로 리다이렉션합니다.
        return "redirect:/store/list";
    }


    // Read
    @GetMapping("/read/{memberId}")
    public Member getMemberById(@PathVariable String memberId) {
        return memberService.getMemberById(memberId).orElse(null);
    }

    @GetMapping("read/allmembers")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    // Update
    @PutMapping("/update/{memberId}")
    public ResponseEntity<?> updateMember(
            @PathVariable String memberId,
            @RequestParam String email,
            @RequestParam String password) {
        memberService.updateMember(memberId, email, password);
        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/delete/{memberId}")
    public String deleteMember(@PathVariable String memberId, @RequestParam String memberPw) {
        return memberService.deleteMember(memberId, memberPw);
    }

    // 페이징 컨트롤러
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "signup";
    }
}
