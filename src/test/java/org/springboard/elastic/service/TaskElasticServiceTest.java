package org.springboard.elastic.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.elastic.index.TaskElasticIndex;
import org.springboard.elastic.mapper.TaskElasticMapper;
import org.springboard.elastic.repository.TaskElasticRepository;
import org.springboard.entity.Listing;
import org.springboard.entity.Product;
import org.springboard.entity.Task;
import org.springboard.entity.User;
import org.springboard.repository.ListingRepository;
import org.springboard.repository.ProductRepository;
import org.springboard.repository.TaskRepository;
import org.springboard.repository.UserRepository;
import org.springboard.service.ServiceTestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class TaskElasticServiceTest extends ServiceTestBase {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskElasticRepository taskElasticRepository;

    @Autowired
    private TaskElasticService taskElasticService;

    @Autowired
    private TaskElasticMapper taskElasticMapper;


    private User creator;
    private Product product;
    private Listing listing;
    private List<Task> cases = new ArrayList<>();
    private Task aCase;

    @BeforeEach
    void setUp() {
        taskElasticService.deleteAllTaskIndices();

        creator = userRepository.save(buildUser());
        product = productRepository.save(buildProduct(creator));
        listing = listingRepository.save(buildListing(product, creator));
        IntStream.range(0, ServiceTestBase.CASE_COUNT).forEach(
                i -> {
                    Task t = buildTask(listing, null, creator);
                    taskRepository.save(t);
                    cases.add(t);
                    TaskElasticIndex taskElasticIndex = taskElasticMapper.toTaskIndex(t);
                    taskElasticService.putTaskIndex(taskElasticIndex);
                });
        aCase = sample(cases);
    }

    @Test
    void putTaskIndexAndDeleteTest() {
        Long originCount = taskElasticService.totalSize();
        Task newTask = taskRepository.save(buildTask(listing, null, creator));
        TaskElasticIndex taskElasticIndex = taskElasticMapper.toTaskIndex(newTask);

        taskElasticService.putTaskIndex(taskElasticIndex);
        assertEquals(originCount + 1, taskElasticService.totalSize());

        taskElasticService.deleteTaskIndex(taskElasticIndex);
        assertEquals(originCount + 1 - 1, taskElasticService.totalSize());
    }


    @Nested
    class FindByTitle {
        private static final String INDEX_TITLE = "a an the to all";
        private static final long INDEX_ID = 12345L;

        @BeforeEach
        void setup() {
            TaskElasticIndex taskElasticIndex =
                    taskElasticMapper.toTaskIndex(buildTask(listing, null, creator));
            taskElasticIndex.setTitle(INDEX_TITLE);
            taskElasticIndex.setId(INDEX_ID);
            taskElasticService.putTaskIndex(taskElasticIndex);
        }

        @Test
        void findByTitleTest() {
            List<TaskElasticIndex> taskElasticIndices = taskElasticService.findByTitle("the");
            assertFalse(taskElasticIndices.isEmpty());
        }

        @Test
        void testFindByTitleTest() {
            int page = 0;
            int size = 1;
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
            Page<TaskElasticIndex> taskElasticIndexPage = taskElasticService.findByTitle("the", pageable);
            assertFalse(taskElasticIndexPage.getContent().isEmpty());
        }
    }
}
