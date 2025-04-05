package com.claro.projeto.model.machine;

import com.claro.projeto.model.user.User;
import com.claro.projeto.util.entity.AuditableEntity;
import com.claro.projeto.util.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VirtualMachine")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VirtualMachine extends AuditableEntity {

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false)
    private Integer cpu;

    @Column(nullable = false)
    private Integer memory;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
}