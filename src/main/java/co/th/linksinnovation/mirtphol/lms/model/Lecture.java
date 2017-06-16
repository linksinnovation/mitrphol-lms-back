/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mirtphol.lms.model;

import co.th.linksinnovation.mirtphol.lms.model.enumuration.ContentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author Jirawong Wongdokpuang <jirawong@linksinnovation.com>
 */
@Data
@Entity
public class Lecture {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String content;
    private ContentType contentType;
    private Long duration;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date updateDate;
    private String uuid;
    @ManyToOne
    @JsonBackReference
    private Lession lession;
}
