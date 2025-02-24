package br.com.carlos.JobBoard_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID cvId;

    private String cvUrl;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UUID getCvId() {
        return cvId;
    }

    public void setCvId(UUID cvId) {
        this.cvId = cvId;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }
}
