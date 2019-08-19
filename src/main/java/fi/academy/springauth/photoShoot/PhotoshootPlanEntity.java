package fi.academy.springauth.photoShoot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.images.ImageEntity;

import javax.persistence.*;

@Entity
public class PhotoshootPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "app_user_entity_id")
    @JsonIgnoreProperties("plans")
    private AppUserEntity creator;

    private String plan;

    public PhotoshootPlanEntity() {
    }

    public PhotoshootPlanEntity(AppUserEntity creator, String plan) {
        this.creator = creator;
        this.plan = plan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUserEntity getCreator() {
        return creator;
    }

    public void setCreator(AppUserEntity creator) {
        this.creator = creator;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
