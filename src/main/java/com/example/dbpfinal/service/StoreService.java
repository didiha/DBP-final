package com.example.dbpfinal.service;

import com.example.dbpfinal.entity.Bookmark;
import com.example.dbpfinal.entity.Store;
import com.example.dbpfinal.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    // 상점 읽어오기
    public Optional<Store> findStoreById(Long storeId) {
        return storeRepository.findById(storeId);
    }

    public List<Store> findStoresByIds(List<Long> storeIds) {
        return storeRepository.findAllById(storeIds);
    }

    public List<Store> searchStoresByStoreName(String storeName) {
        return storeRepository.findByStoreNameContaining(storeName);
    }

    public List<Store> searchStoresByAddress(String address) {
        return storeRepository.findByAddressContaining(address);
    }

    public List<Store> searchStores(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return storeRepository.findAll();
        }
        return storeRepository.findByStoreNameContainingOrAddressContaining(keyword, keyword);
    }

    public List<Store> findAll() {
        return storeRepository.findAll();
    }
}
