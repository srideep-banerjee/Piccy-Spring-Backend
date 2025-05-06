package me.projects.piccy.auth;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("select ue from UserEntity ue join fetch ue.idToName itn where itn.username = :username")
    Optional<UserEntity> findByUsername(String username);
}
