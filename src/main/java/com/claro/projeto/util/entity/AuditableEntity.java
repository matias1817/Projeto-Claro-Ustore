package com.claro.projeto.util.entity;

import java.time.LocalDate;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity  extends BasicEntity {
  
   @JsonIgnore
   @Version
   private Long version;

   @JsonIgnore
   @CreatedDate
   private LocalDate creationDate;

   @JsonIgnore
   @LastModifiedDate
   private LocalDate lastModificationDate;

   @JsonIgnore
   @Column
   private Long createdBy; // Id do usuário que o criou

   @JsonIgnore
   @Column
   private Long lastChangeBy; // Id do usuário que fez a última alteração

}