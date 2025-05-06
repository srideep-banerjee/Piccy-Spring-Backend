package me.projects.piccy.profile;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ProfileRepository extends CrudRepository<Profile, Long> {
}
