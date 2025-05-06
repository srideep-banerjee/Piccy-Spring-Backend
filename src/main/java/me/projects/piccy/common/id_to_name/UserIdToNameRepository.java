package me.projects.piccy.common.id_to_name;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdToNameRepository extends CrudRepository<UserIdToName, Long> {
}
