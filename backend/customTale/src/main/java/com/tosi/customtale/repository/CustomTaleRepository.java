package com.tosi.customtale.repository;

import com.tosi.customtale.entity.CustomTale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTaleRepository extends JpaRepository<CustomTale, Long>, CustomTaleRepositoryCustom {


//    // userId로 CustomTale 조회
//    List<CustomTale> findByUserId(Integer userId);
//
//    // opened가 true인 CustomTale 조회
//    List<CustomTale> findByOpened(boolean opened);
//
//    void deleteByUserId(int userId);
}