package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.entity.User;
import org.springboard.repository.UserRepository;
import org.springboard.vo.CreateUserVo;
import org.springboard.vo.UpdateUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class UserServiceTest extends ServiceTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private List<User> cases = new ArrayList<>();
    private User aCase;

    @BeforeEach
    void setUp() {
        IntStream.range(0, CASE_COUNT).forEach(i -> cases.add(userRepository.save(buildUser())));
        aCase = sample(cases);
    }

    @Test
    void getUserByIdTest() throws Exception {
        User user = userService.getUserById(aCase.getId());
        assertEquals(aCase.getName(), user.getName());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserById(Long.MAX_VALUE).getName();
        });
    }

    @Test
    void getUserByUuidTest() throws Exception {
        User user = userService.getUserByUuid(aCase.getUuid());
        assertEquals(aCase.getName(), user.getName());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserByUuid(faker.name().fullName());
        });
    }

    @Test
    void getUserByEmailTest() throws Exception {
        User user = userService.getUserByEmail(aCase.getEmail());
        assertEquals(aCase.getName(), user.getName());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserByUuid(faker.internet().emailAddress());
        });
    }

    @Test
    void findUserByIdTest() throws Exception {
        Optional<User> userOptional = userService.findUserById(aCase.getId());
        assertTrue(userOptional.isPresent());
        assertEquals(aCase.getName(), userOptional.get().getName());

        assertTrue(userService.findUserById(Long.MAX_VALUE).isEmpty());
    }

    @Test
    void findAllUsersTest() throws Exception {
        int page = 0;
        int size = 1;
        // fetch last one by id
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<User> userPage = userService.findAllUsers(pageable);
        assertEquals(size, userPage.getSize());
        assertEquals(cases.get(cases.size() - 1).getUuid(), userPage.getContent().get(0).getUuid());
    }

    @Nested
    class CreateUserTest {
        private CreateUserVo vo;
        private Long originCount;

        @BeforeEach
        void setUp() {
            vo = buildCreateUserVo();
            originCount = userRepository.count();
        }

        @Test
        void createUserTest() {
            User user = userService.createUser(vo);
            assertEquals(vo.getEmail(), user.getEmail());
            assertFalse(user.getPasswordDigest().isEmpty());
            assertEquals(originCount + 1, userRepository.count());
        }

        @Test
        void withUniqueEmailTest() {
            vo.setEmail(aCase.getEmail());

            assertThrows(DataIntegrityViolationException.class, () -> {
                userService.createUser(vo);
            });
        }
    }

    @Nested
    class UpdateUserTest {
        @Test
        void updateUserTest() throws Exception {
            UpdateUserVo vo = buildUpdateUserVo();
            userService.updateUser(aCase, vo);
            assertEquals(vo.getEmail(), aCase.getEmail());
            assertEquals(vo.getName(), aCase.getName());
        }

        @Test
        void onlyPatchAFieldTest() throws Exception {
            UpdateUserVo vo = UpdateUserVo.builder().build();
            vo.setName(faker.name().fullName());
            userService.updateUser(aCase, vo);
            assertEquals(vo.getName(), aCase.getName());
            assertNotNull(aCase.getEmail());
        }
    }

    @Test
    void destroyUserTest() throws Exception {
        long originCount = userRepository.count();
        userService.destroyUser(aCase.getId());
        assertEquals(originCount - 1, userRepository.count());
    }
}
