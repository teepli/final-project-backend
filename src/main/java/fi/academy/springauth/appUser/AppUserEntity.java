package fi.academy.springauth.appUser;

import com.fasterxml.jackson.annotation.*;
import fi.academy.springauth.appUser.friend.FriendEntity;
import fi.academy.springauth.content.ContentEntity;
import fi.academy.springauth.photoShoot.PhotoshootPlanEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class AppUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
    @JsonIgnoreProperties("creator")
    private List<PhotoshootPlanEntity> plans;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "creator")
//    @JsonIgnoreProperties("creator")
//    private List<ContentEntity> content;
//
//    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("friend")
//    private List<FriendEntity> friends;

    public AppUserEntity() {
    }

    public AppUserEntity(String name, String username, String password, List<PhotoshootPlanEntity> plans) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.plans = plans;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhotoshootPlanEntity> getPlans() {
        return plans;
    }

    public void setPlans(List<PhotoshootPlanEntity> plans) {
        this.plans = plans;
    }
}
