package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.entity.Listing;
import org.springboard.entity.Product;
import org.springboard.entity.Task;
import org.springboard.entity.User;
import org.springboard.repository.ListingRepository;
import org.springboard.repository.ProductRepository;
import org.springboard.repository.TaskRepository;
import org.springboard.repository.UserRepository;
import org.springboard.vo.CreateTaskVo;
import org.springboard.vo.UpdateTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class TaskServiceTest extends ServiceTestBase {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskService taskService;

    private User creator;
    private Product product;
    private Listing listing;
    private List<Task> cases = new ArrayList<>();
    private Task aCase;

    @BeforeEach
    void setUp() {
        creator = userRepository.save(buildUser());
        product = productRepository.save(buildProduct(creator));
        listing = listingRepository.save(buildListing(product, creator));
        IntStream.range(0, CASE_COUNT).forEach(
                i -> cases.add(taskRepository.save(buildTask(listing, null, creator))));
        aCase = sample(cases);
    }

    @Test
    void getTaskByIdTest() {
        Task task = taskService.getTaskById(aCase.getId());
        assertEquals(aCase.getTitle(), task.getTitle());

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.getTaskById(Long.MAX_VALUE).getTitle();
        });
    }

    @Test
    void getTaskByUuidTest() {
        Task task = taskService.getTaskByUuid(aCase.getUuid());
        assertEquals(aCase.getTitle(), task.getTitle());

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.getTaskByUuid("INVALID UUID").getTitle();
        });
    }

    @Test
    void findTaskByIdTest() {
        Optional<Task> taskOptional = taskService.findTaskById(aCase.getId());
        assertTrue(taskOptional.isPresent());
        assertEquals(aCase.getTitle(), taskOptional.get().getTitle());
    }

    @Test
    void destroyTaskTest() {
        long originCount = taskRepository.count();
        taskService.destroyTask(aCase.getId());
        assertEquals(originCount - 1, taskRepository.count());
    }

    @Nested
    class CreateAndUpdateTaskTest {

        private long originCount;
        private Listing newListing;
        private Task parentTask;
        private Task sonTask;

        @BeforeEach
        void setup() {
            newListing = listingRepository.save(buildListing(product, creator));
            parentTask = taskRepository.save(buildTask(newListing, null, creator));
            sonTask = taskRepository.save(buildTask(newListing, parentTask, creator));
            originCount = taskRepository.count();
        }

        @Test
        void createTaskByListingTest() {
            CreateTaskVo vo = buildCreateTaskVo(newListing.getUuid(), null);
            Task task = taskService.createTask(vo, creator);

            assertEquals(originCount + 1, taskRepository.count());
            assertEquals(task.getTitle(), vo.getTitle());
            assertEquals(task.getCreator().getUuid(), creator.getUuid());
            assertEquals(task.getListing().getUuid(), newListing.getUuid());
            assertNull(task.getParentTask());
            assertNull(task.getCompletedAt());
        }

        @Test
        void createTaskByParentTaskTest() {
            CreateTaskVo vo = buildCreateTaskVo(null, parentTask.getUuid());
            Task task = taskService.createTask(vo, creator);

            assertEquals(originCount + 1, taskRepository.count());
            assertEquals(task.getTitle(), vo.getTitle());
            assertEquals(task.getCreator().getUuid(), creator.getUuid());
            assertEquals(task.getListing().getUuid(), parentTask.getListing().getUuid());
            assertEquals(task.getParentTask().getUuid(), parentTask.getUuid());
            assertNull(task.getCompletedAt());
        }

        @Test
        void updateTaskByListingTest() {
            UpdateTaskVo vo = buildUpdateTaskVo(newListing.getUuid(), null, false);
            taskService.updateTask(aCase, vo);

            assertEquals(aCase.getTitle(), vo.getTitle());
            assertEquals(aCase.getListing().getUuid(), newListing.getUuid());
            assertNull(aCase.getParentTask());
            assertNull(aCase.getCompletedAt());
        }

        @Test
        void updateTaskByParentTaskTest() {
            UpdateTaskVo vo = buildUpdateTaskVo(null, parentTask.getUuid(), false);
            taskService.updateTask(aCase, vo);

            assertEquals(aCase.getTitle(), vo.getTitle());
            assertEquals(aCase.getListing().getUuid(), parentTask.getListing().getUuid());
            assertEquals(aCase.getParentTask().getUuid(), parentTask.getUuid());
            assertNull(aCase.getCompletedAt());
        }

        @Test
        void updateTaskByCompletedTest() {
            UpdateTaskVo vo = buildUpdateTaskVo(null, null, true);
            taskService.updateTask(aCase, vo);

            assertNotNull(aCase.getCompletedAt());
        }
    }
}
