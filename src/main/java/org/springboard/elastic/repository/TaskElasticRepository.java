package org.springboard.elastic.repository;

import org.springboard.elastic.index.TaskElasticIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TaskElasticRepository extends ElasticsearchRepository<TaskElasticIndex, Long> {
}
