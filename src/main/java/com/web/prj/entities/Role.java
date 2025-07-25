package com.web.prj.entities;

import com.web.prj.enums.ROLE;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roleId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<GrantedPermission> grantedPermissions = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;
    private boolean active = true;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleId='" + roleId + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", active=" + active +
                '}';
    }
}
