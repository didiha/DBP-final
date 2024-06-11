package com.example.dbpfinal.controller;

import com.example.dbpfinal.entity.Store;
import com.example.dbpfinal.repository.StoreRepository;
import com.example.dbpfinal.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequestMapping("/store")
@Controller
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreService storeService;
    @Autowired
    public StoreController(StoreRepository storeRepository, StoreService storeService) {
        this.storeRepository = storeRepository;
        this.storeService = storeService;
    }

    //read
    @GetMapping("/read/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId) {
        Store store = storeService.findStoreById(storeId)
                .orElseThrow(() -> new RuntimeException("Playlist not found with store Id " + storeId));
        return ResponseEntity.ok(store);
    }

    // 프론트용 컨트롤러

    @GetMapping("/list")
    public String list(@RequestParam(required = false) String kw, Model model) {
        List<Store> storeList = storeService.searchStores(kw);
        model.addAttribute("storeList", storeList);
        model.addAttribute("kw", kw);
        return "store_list";
    }

    @GetMapping(value = "/detail/{storeId}")
    public String storeDetail(Model model, @PathVariable("storeId") Long storeId) {
        Optional<Store> storeOptional = this.storeService.findStoreById(storeId);
        if (storeOptional.isPresent()) {
            Store store = storeOptional.get();
            model.addAttribute("store", store);
            return "store_detail";
        } else {
            // Store가 존재하지 않을 경우 처리
            return "error/404"; // 예시: 404 페이지로 리다이렉트
        }
    }
}
