package com.example.dbpfinal.repository;

import com.example.dbpfinal.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(Long id);

    List<Store> findByStoreNameContaining(String storeName);

    List<Store> findByAddressContaining(String address);

    List<Store> findByStoreNameContainingOrAddressContaining(String storeName, String address);
}
