package br.com.carlos.JobBoard_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_logo")
public class CompanyLogoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "logo_id")
    private UUID logoId;

    @Column(name = "image_name")
    private String logoName;

    @Column(name = "image_url")
    private String url;

    public static Object builder() {
        return null;
    }

    public UUID getLogoId() {
        return logoId;
    }

    public void setLogoId(UUID logoId) {
        this.logoId = logoId;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
