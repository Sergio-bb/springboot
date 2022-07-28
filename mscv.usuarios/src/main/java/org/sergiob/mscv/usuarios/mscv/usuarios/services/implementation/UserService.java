package org.sergiob.mscv.usuarios.mscv.usuarios.services.implementation;

import org.sergiob.mscv.usuarios.mscv.usuarios.models.entities.User;
import org.sergiob.mscv.usuarios.mscv.usuarios.repositories.UserRepository;
import org.sergiob.mscv.usuarios.mscv.usuarios.services.contract.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository _repository;

    @Override
    @Transactional()
    public List<User> getAll() {
        return (List<User>) _repository.findAll();
    }

    @Override
    @Transactional
    public Optional<User> getById(Long id) {
        return _repository.findById(id);
    }

    @Override
    @Transactional
    public User add(User user) {
        return _repository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        _repository.deleteById(id);
    }
}
