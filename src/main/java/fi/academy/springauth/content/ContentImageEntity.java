package fi.academy.springauth.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.*;

@Entity
public class ContentImageEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String url;

    @OneToOne
    @JsonIgnoreProperties("image")
    private ContentEntity content;

    public ContentImageEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentEntity getContent() {
        return content;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }
}
