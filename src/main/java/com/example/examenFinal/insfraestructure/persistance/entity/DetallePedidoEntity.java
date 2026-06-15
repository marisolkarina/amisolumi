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
@Table(name = "detalle_pedidos")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class DetallePedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tejido_id_fk", nullable = false)
    private TejidoEntity tejido;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id_fk", nullable = false)
    private PedidoEntity pedido;
}
