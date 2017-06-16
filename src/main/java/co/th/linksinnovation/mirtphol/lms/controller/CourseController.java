/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.controller;

import co.th.linksinnovation.mirtphol.lms.model.Course;
import co.th.linksinnovation.mirtphol.lms.repository.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {
    
    @Autowired
    private CourseRepository courseRepository;
    
    @GetMapping
    public List<Course> get(){
        return courseRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Course get(@PathVariable Long id){
        return courseRepository.findOne(id);
    }
    
    @PostMapping
    public Course post(@RequestBody Course course){
        return courseRepository.save(course);
    }
}
