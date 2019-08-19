package fi.academy.springauth.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.springauth.appUser.AppUserEntity;
import fi.academy.springauth.images.ImageEntity;

import javax.persistence.*;

@Entity
public class ContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;

//    @OneToOne
//    @JsonIgnoreProperties("content")
//    private ImageEntity image;
//
//    @ManyToOne
//    @JoinColumn(name = "app_user_entity_id")
//    @JsonIgnoreProperties("content")
//    private AppUserEntity creator;
//
//    public ContentEntity(String content, ImageEntity image, AppUserEntity creator) {
//        this.content = content;
//        this.image = image;
//        this.creator = creator;
//    }
//
//    public ContentEntity(String content) {
//        this.content = content;
//    }
//
//    public ContentEntity() {
//    }
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public ImageEntity getImage() {
//        return image;
//    }
//
//    public void setImage(ImageEntity image) {
//        this.image = image;
//    }
//
//    public AppUserEntity getCreator() {
//        return creator;
//    }
//
//    public void setCreator(AppUserEntity creator) {
//        this.creator = creator;
//    }
}
