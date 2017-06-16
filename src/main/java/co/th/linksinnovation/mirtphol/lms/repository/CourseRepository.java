/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.repository;

import co.th.linksinnovation.mirtphol.lms.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
public interface CourseRepository extends JpaRepository<Course, Long>{
    
}
