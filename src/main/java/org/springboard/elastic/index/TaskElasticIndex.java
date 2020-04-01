package org.springboard.elastic.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "task")
public class TaskElasticIndex {

    @Id
    private Long id;

    private String title;

    private String description;
}
