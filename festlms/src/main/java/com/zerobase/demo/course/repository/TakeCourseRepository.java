package com.zerobase.demo.course.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zerobase.demo.course.entity.TakeCourse;

@Repository
public interface TakeCourseRepository extends JpaRepository<TakeCourse, Long>{
 
	
	long countByCourseIdAndUserIdAndStatusIn(long courseId, String userId, Collection<String> statusList);
	
}
