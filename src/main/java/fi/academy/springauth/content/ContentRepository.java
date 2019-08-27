package fi.academy.springauth.content;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<ContentEntity, Long> {

//    @Query("select ")
}
