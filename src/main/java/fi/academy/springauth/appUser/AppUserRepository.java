package fi.academy.springauth.appUser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUserEntity, Long> {
    AppUserEntity findByUsername(String user);
}
