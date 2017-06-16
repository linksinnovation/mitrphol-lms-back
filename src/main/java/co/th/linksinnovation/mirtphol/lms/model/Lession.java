/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import lombok.Data;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Data
@Entity
public class Lession {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JsonBackReference
    private Course course;
    @OneToMany(mappedBy = "lession",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("id ASC")
    private List<Lecture> lectures;
}
