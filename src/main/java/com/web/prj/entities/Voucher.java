package com.web.prj.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vouchers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String vouId;

    @Column(nullable = false)
    private String name;

    private String conditionApply;

    private Double limitPrice;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(mappedBy = "voucher")
    private List<Order> orders;

    @OneToMany(mappedBy = "voucher")
    private List<Product> products;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;
    private boolean isDeleted;

}
