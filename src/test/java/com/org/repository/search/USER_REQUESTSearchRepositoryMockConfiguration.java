package com.org.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link USER_REQUESTSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class USER_REQUESTSearchRepositoryMockConfiguration {

    @MockBean
    private USER_REQUESTSearchRepository mockUSER_REQUESTSearchRepository;

}
