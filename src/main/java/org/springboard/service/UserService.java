package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.User;
import org.springboard.mapper.UserMapper;
import org.springboard.repository.UserRepository;
import org.springboard.vo.CreateUserVo;
import org.springboard.vo.UpdateUserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUuid(UUID uuid) {
        User user = userRepository.getByUuid(uuid);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        return user;
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(CreateUserVo vo) {
        User user = userMapper.createUser(vo);
        user.setPasswordDigest(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user, UpdateUserVo vo) {
        userMapper.mergeUser(user, vo);
        return userRepository.save(user);
    }

    public void destroyUser(Long id) {
        userRepository.deleteById(id);
    }
}