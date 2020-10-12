package br.com.beertechtalents.lupulo.pocmq.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Column(precision=16, scale=2, nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private UUID contaCorrenteOrigem;

    @Column(nullable = false)
    private UUID contaCorrenteDestino;

    @CreatedDate
    private Timestamp datahora;

}
