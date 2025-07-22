package com.web.prj.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String permissionId;

    @Column(nullable = false)
    private String name;

    private String module;
    private String action;

    private boolean active = true;

    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL)
    private List<GrantedPermission> grantedPermissions = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @LastModifiedDate
    protected LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionId='" + permissionId + '\'' +
                ", name='" + name + '\'' +
                ", module='" + module + '\'' +
                ", action='" + action + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
