package fi.academy.springauth.photoShoot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.images.ImageEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String participants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "photoshoot")
    @JsonIgnoreProperties("photoshoot")
    private List<ImageEntity> referencePictures = new ArrayList<>();

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

    public String getParticipants() {
        return participants; }

    public void setParticipants(String participants) {
        this.participants = participants; }

    public List<ImageEntity> getReferencePictures() {
        return referencePictures; }

    public void setReferencePictures(List<ImageEntity> referencePicture) {
        this.referencePictures = referencePicture; }
}
