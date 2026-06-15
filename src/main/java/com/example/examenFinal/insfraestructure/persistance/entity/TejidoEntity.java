package com.example.examenFinal.insfraestructure.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tejidos")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TejidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Double precio;
    @Column(nullable = false)
    private String imagen;
    @Column(name="tiempo_produccion", nullable = false)
    private Integer tiempoProduccion;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id_fk", nullable = false)
    private UsuarioEntity usuario;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "tejido_categoria",
            joinColumns = @JoinColumn(name = "tejido_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id_fk")
    )
    private List<CategoriaEntity> categorias;
}
