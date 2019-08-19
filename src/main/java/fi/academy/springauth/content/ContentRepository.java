package fi.academy.springauth.content;

import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<ContentEntity, Long> {

//    ContentEntity findByCreator(String creator);
}
