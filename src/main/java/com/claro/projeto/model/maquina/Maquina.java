package com.claro.projeto.model.maquina;

import com.claro.projeto.util.entity.EntidadeAuditavel;
import com.claro.projeto.util.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Maquina")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Maquina extends EntidadeAuditavel {

    @Column(nullable = false, unique = true)
    private String displayName;

    @Column(nullable = false)
    private Integer cpu;

    @Column(nullable = false)
    private Integer memory;

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Status status;   
}