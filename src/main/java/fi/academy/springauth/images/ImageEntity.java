package fi.academy.springauth.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fi.academy.springauth.content.ContentEntity;

import javax.persistence.*;

@Entity
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String url;
//    @OneToOne(mappedBy = "image")
//    @JsonIgnoreProperties("image")
//    private ContentEntity content;

    public ImageEntity(String url, ContentEntity content) {
        this.url = url;

    }

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
}
