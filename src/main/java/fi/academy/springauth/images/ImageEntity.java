package fi.academy.springauth.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.springauth.content.ContentEntity;
import fi.academy.springauth.photoShoot.PhotoshootPlanEntity;

import javax.persistence.*;

@Entity
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;
    @Column(name = "REFERENCE")
    private boolean reference;

    @ManyToOne
    @JoinColumn(name="photoshoot_plan_entity_id")
    @JsonIgnoreProperties("referencePictures")
    private PhotoshootPlanEntity photoshoot;

    public ImageEntity(String url) {
        this.url = url;

    }

    public ImageEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PhotoshootPlanEntity getPhotoshoot() {
        return photoshoot;
    }

    public void setPhotoshoot(PhotoshootPlanEntity photoshoot) {
        this.photoshoot = photoshoot;
    }
}
