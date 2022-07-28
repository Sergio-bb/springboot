package org.sergiob.mscv.usuarios.mscv.usuarios.repositories;

import org.sergiob.mscv.usuarios.mscv.usuarios.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
