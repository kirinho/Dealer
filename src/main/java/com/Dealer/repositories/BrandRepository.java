package com.Dealer.repositories;

import com.Dealer.entities.Brand;
import com.Dealer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByUser(User user);
}
