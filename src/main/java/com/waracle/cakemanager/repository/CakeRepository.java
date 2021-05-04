package com.waracle.cakemanager.repository;

import com.waracle.cakemanager.domain.entity.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Integer> {
}
