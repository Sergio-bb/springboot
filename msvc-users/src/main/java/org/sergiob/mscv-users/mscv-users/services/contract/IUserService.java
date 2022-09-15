package org.sergiob.mscv.usuarios.mscv.usuarios.services.contract;

import org.sergiob.mscv.usuarios.mscv.usuarios.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAll();
    Optional<User> getById(Long id);
    User add(User user);
    void delete(Long id);
    Optional<User> findByEmail(String email);
    List<User> findByIds(List<Long> ids);
}
