package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.vo.CreateUserVo;
import org.springboard.vo.UpdateUserVo;
import org.springboard.entity.User;
import org.springboard.mapper.UserMapper;
import org.springboard.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public User getUser(String uuid) {
        User user = userRepository.getByUuid(uuid);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(CreateUserVo vo) {
        User user = userMapper.createUser(vo);
        user.setPasswordDigest(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void updateUser(User user, UpdateUserVo vo) {
        userMapper.mergeUser(user, vo);
        userRepository.save(user);
    }

    public void destroyUser(Long id) {
        userRepository.deleteById(id);
    }
}