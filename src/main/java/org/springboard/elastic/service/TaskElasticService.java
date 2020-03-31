package org.springboard.elastic.service;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springboard.elastic.index.TaskElasticIndex;
import org.springboard.elastic.repository.TaskElasticRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskElasticService {

    private final TaskElasticRepository taskElasticRepository;

    public TaskElasticIndex putTaskIndex(TaskElasticIndex taskElasticIndex) {
        return taskElasticRepository.save(taskElasticIndex);
    }

    public Long totalSize() {
        return taskElasticRepository.count();
    }

    public List<TaskElasticIndex> findByTitle(String title) {
        MatchQueryBuilder query = new MatchQueryBuilder("title", title);
        Iterable<TaskElasticIndex> result = taskElasticRepository.search(query);
        return Lists.newArrayList(result);
    }

    public Page<TaskElasticIndex> findByTitle(String title, Pageable pageable) {
        SearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("title", title))
                .withPageable(pageable)
                .build();
        return taskElasticRepository.search(query);
    }

    public void deleteTaskIndex(TaskElasticIndex taskElasticIndex) {
        taskElasticRepository.delete(taskElasticIndex);
    }

    public void deleteAllTaskIndices() {
        taskElasticRepository.deleteAll();
    }
}
