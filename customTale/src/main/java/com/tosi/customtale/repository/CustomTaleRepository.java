package com.tosi.customtale.repository;

import com.tosi.customtale.entity.CustomTale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTaleRepository extends JpaRepository<CustomTale, Long>, CustomTaleRepositoryCustom {

    @Modifying
    @Query("UPDATE CustomTale c SET c.isPublic = CASE WHEN c.isPublic = true THEN false ELSE true END WHERE c.customTaleId = :customTaleId")
    int modifyCustomTalePublicStatus(Long customTaleId);

}