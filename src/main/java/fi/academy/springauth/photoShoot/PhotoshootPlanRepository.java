package fi.academy.springauth.photoShoot;

import fi.academy.springauth.appUser.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoshootPlanRepository extends JpaRepository<PhotoshootPlanEntity, Long> {

    PhotoshootPlanEntity findByCreator(AppUserEntity user);
    PhotoshootPlanEntity findByCreator_Username(String username);
}
