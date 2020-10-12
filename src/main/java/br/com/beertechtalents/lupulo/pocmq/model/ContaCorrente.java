package br.com.beertechtalents.lupulo.pocmq.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(updatable = false, unique = true, nullable = false)
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(32)", updatable = false, unique = true, nullable = false)
    private UUID idContaCorrente;

    private String donoContaCorrente;

    @Column(precision=16, scale=2, nullable = false)
    private BigDecimal saldo;

    @PrePersist
    public void autofill() {
        this.setIdContaCorrente(UUID.randomUUID());
    }

}
