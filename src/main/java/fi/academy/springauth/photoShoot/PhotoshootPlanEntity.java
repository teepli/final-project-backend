package fi.academy.springauth.photoShoot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.images.ImageEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PhotoshootPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String header;
    private Date date;
    private String location;
    private String description;
    private String notes;
    private String referencepictures;

    @ManyToOne
    @JoinColumn(name = "app_user_entity_id")
    @JsonIgnoreProperties("plans")
    private AppUserEntity creator;

    public PhotoshootPlanEntity() {
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReferencepictures() {
        return referencepictures;
    }

    public void setReferencepictures(String referencepictures) {
        this.referencepictures = referencepictures;
    }
}
