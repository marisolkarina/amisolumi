package com.example.examenFinal.insfraestructure.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, updatable = false)
    private String nombres;
    @Column(nullable = false, updatable = false)
    private String apellidos;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String celular;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false, updatable = false)
    private String dni;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private RolEntity rol;
}
