package com.codelab.rest.webservices.trainingtool.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Identifiable {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer id;

     @CreationTimestamp
     @Temporal(TemporalType.TIMESTAMP)
     @Column(name = "create_date")
     private Date createDate;

     @UpdateTimestamp
     @Temporal(TemporalType.TIMESTAMP)
     @Column(name = "modify_date")
     private Date modifyDate;
}
