package fi.academy.springauth.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.springauth.content.ContentEntity;
import fi.academy.springauth.images.metadata.MetadataEntity;
import fi.academy.springauth.photoShoot.PhotoshootPlanEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name="photoshoot_plan_entity_id")
    @JsonIgnoreProperties("referencePictures")
    private PhotoshootPlanEntity photoshoot;

    @OneToMany(mappedBy = "image")
    @JsonIgnoreProperties("image")
    @ElementCollection
    private List<MetadataEntity> metadatalist = new ArrayList<>();

    @ElementCollection
    private List<String> metadatatest = new ArrayList<>();

    public ImageEntity(String url) {
        this.url = url;

    }

    public ImageEntity() {
    }

    public List<String> getMetadatatest() {
        return metadatatest;
    }

    public void setMetadatatest(List<String> metadatatest) {
        this.metadatatest = metadatatest;
    }

    public List<MetadataEntity> getMetadatalist() {
        return metadatalist;
    }

    public void setMetadatalist(List<MetadataEntity> metadatalist) {
        this.metadatalist = metadatalist;
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
