package com.web.prj.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)

public class GrantedPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Permission permission;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @Override
    public String toString() {
        return "GrantedPermission{" +
                "id=" + id +
                ", role=" + role +
                ", permission=" + permission +
                ", createdAt=" + createdAt +
                '}';
    }

}
