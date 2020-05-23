package com.org.repository.search;

import com.org.domain.USER_REQUEST;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link USER_REQUEST} entity.
 */
public interface USER_REQUESTSearchRepository extends ElasticsearchRepository<USER_REQUEST, Long> {
}
