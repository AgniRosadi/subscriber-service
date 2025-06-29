package com.example.subscriber_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "biller_prefix")
public class BillerPrefix implements Serializable {
    @Serial
    private static final long serialVersionUID = 3638056263086120763L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "prefix")
    private String prefix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    private BillerCategory billerCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_biller", referencedColumnName = "id")
    private BillerProduct billerProduct;
}
