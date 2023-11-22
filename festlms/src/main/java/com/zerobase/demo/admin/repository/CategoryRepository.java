package com.zerobase.demo.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.demo.admin.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	
}
