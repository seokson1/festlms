package com.zerobase.demo.course.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.demo.course.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
 
	Optional<List<Course>> findByCategoryId(long categoryId);
	
}
