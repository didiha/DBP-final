package com.example.dbpfinal.controller;

import com.example.dbpfinal.entity.Bookmark;
import com.example.dbpfinal.entity.Member;
import com.example.dbpfinal.form.BookmarkForm;
import com.example.dbpfinal.repository.BookmarkRepository;
import com.example.dbpfinal.service.BookmarkService;
import com.example.dbpfinal.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final MemberService memberService;
    private final BookmarkRepository bookmarkRepository;

    @PostMapping("/create/{memberId}")
    public String createBookmark(@PathVariable String memberId, @Valid @RequestBody BookmarkForm bookmarkForm) {
        // 현재 인증된 사용자의 이메일 정보를 사용하여 Member 객체를 검색
        Optional<Member> memberOptional = memberService.findMemberByMemberId(memberId);

        if (!memberOptional.isPresent()) {
            throw new NoSuchElementException("Member not found with ID: " + memberId);
        }

        Member member = memberOptional.get();
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkName(bookmarkForm.getBookmarkName());
        bookmark.setMember(member);
        Bookmark createdBookmark = bookmarkService.createBookmark(bookmark);
        return "redirect:/bookmark/list";
    }


    //read
    @GetMapping("/read/{memberId}")
    public List<Bookmark> getBookmarksByMemberId(@PathVariable String memberId) {
        return bookmarkService.getBookmarksByMemberId(memberId);
    }

    // Update
    @PutMapping("/update/{bookmarkId}")
    public ResponseEntity<?> updateBookmark(
            @PathVariable Long bookmarkId,
            @RequestParam String bookmarkName) {
        bookmarkService.updateBookmark(bookmarkId, bookmarkName);
        return ResponseEntity.ok().build();
    }

    // 삭제
    @CrossOrigin(origins = "http://localhost:8080/bookmark/delete/{bookmarkId}")
    @DeleteMapping("/delete/{bookmarkId}")
    public ResponseEntity<?> deleteBookmarkAndStores(@PathVariable Long bookmarkId) {
        bookmarkService.deleteBookmarkAndRelatedStores(bookmarkId);
        return ResponseEntity.ok().build();
    }

    // 즐겨찾기에 상점 추가
    @PostMapping("/{bookmarkId}/addStore/{storeId}")
    public ResponseEntity<String> addStoreToBookmark(@PathVariable Long bookmarkId, @PathVariable Long storeId) {
        bookmarkService.addStoreToBookmark(bookmarkId, storeId);
        String redirectUrl = String.format("/bookmark/detail/%d", bookmarkId);
        return ResponseEntity.ok(redirectUrl);
    }


    // 즐겨찾기에 상점 삭제
    @DeleteMapping("/{bookmarkId}/deleteStore/{storeId}")
    public ResponseEntity<Void> removeStoreFromBookmark(@PathVariable Long bookmarkId, @PathVariable Long storeId) {
        bookmarkService.removeStoreFromBookmark(bookmarkId, storeId);
        return ResponseEntity.noContent().build();
    }

    // 프론트용 컨트롤러
    @GetMapping("/list")
    public String list(Model model) {
        List<Bookmark> bookmarkList = this.bookmarkService.getBookmarksByMasterMember();
        model.addAttribute("bookmarkList", bookmarkList);
        return "bookmark_list";
    }

    @GetMapping(value = "/detail/{bookmarkId}")
    public String bookDetail(Model model, @PathVariable("bookmarkId") Long bookmarkId) {
        Optional<Bookmark> bookmarkOptional = this.bookmarkService.findBookmarkById(bookmarkId);
        if (bookmarkOptional.isPresent()) {
            Bookmark bookmark = bookmarkOptional.get();
            model.addAttribute("bookmark", bookmark);
            return "bookmark_detail";
        } else {
            // bookmark가 존재하지 않을 경우 처리
            return "error/404"; // 예시: 404 페이지로 리다이렉트
        }
    }


    @GetMapping("/{bookmarkId}/addStore")
    public String showAddStorePage(@PathVariable Long bookmarkId, Model model) {
        model.addAttribute("bookmarkId", bookmarkId);
        return "add_store"; // add_store.html 템플릿을 반환
    }

    @GetMapping("/createBookmark")
    public String showCreateBookmarkPage(Model model) {
        model.addAttribute("bookmarkForm", new BookmarkForm());
        return "create_bookmark";
    }

}
