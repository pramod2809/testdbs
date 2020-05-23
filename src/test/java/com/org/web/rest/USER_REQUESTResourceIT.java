package com.org.web.rest;

import com.org.ManagementsystemApp;
import com.org.domain.USER_REQUEST;
import com.org.repository.USER_REQUESTRepository;
import com.org.repository.search.USER_REQUESTSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link USER_REQUESTResource} REST controller.
 */
@SpringBootTest(classes = ManagementsystemApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class USER_REQUESTResourceIT {

    private static final Long DEFAULT_REQUEST_ID = 1L;
    private static final Long UPDATED_REQUEST_ID = 2L;

    private static final String DEFAULT_SUBMITTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_SUBMITTED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SUBMITTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBMITTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_APPROVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_APPROVED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_APPROVAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ACCOUNT_NUMER = 1;
    private static final Integer UPDATED_ACCOUNT_NUMER = 2;

    @Autowired
    private USER_REQUESTRepository uSER_REQUESTRepository;

    /**
     * This repository is mocked in the com.org.repository.search test package.
     *
     * @see com.org.repository.search.USER_REQUESTSearchRepositoryMockConfiguration
     */
    @Autowired
    private USER_REQUESTSearchRepository mockUSER_REQUESTSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUSER_REQUESTMockMvc;

    private USER_REQUEST uSER_REQUEST;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static USER_REQUEST createEntity(EntityManager em) {
        USER_REQUEST uSER_REQUEST = new USER_REQUEST()
            .request_id(DEFAULT_REQUEST_ID)
            .submitted_by(DEFAULT_SUBMITTED_BY)
            .submitted_date(DEFAULT_SUBMITTED_DATE)
            .approved_by(DEFAULT_APPROVED_BY)
            .approval_date(DEFAULT_APPROVAL_DATE)
            .status(DEFAULT_STATUS)
            .account_numer(DEFAULT_ACCOUNT_NUMER);
        return uSER_REQUEST;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static USER_REQUEST createUpdatedEntity(EntityManager em) {
        USER_REQUEST uSER_REQUEST = new USER_REQUEST()
            .request_id(UPDATED_REQUEST_ID)
            .submitted_by(UPDATED_SUBMITTED_BY)
            .submitted_date(UPDATED_SUBMITTED_DATE)
            .approved_by(UPDATED_APPROVED_BY)
            .approval_date(UPDATED_APPROVAL_DATE)
            .status(UPDATED_STATUS)
            .account_numer(UPDATED_ACCOUNT_NUMER);
        return uSER_REQUEST;
    }

    @BeforeEach
    public void initTest() {
        uSER_REQUEST = createEntity(em);
    }

    @Test
    @Transactional
    public void createUSER_REQUEST() throws Exception {
        int databaseSizeBeforeCreate = uSER_REQUESTRepository.findAll().size();

        // Create the USER_REQUEST
        restUSER_REQUESTMockMvc.perform(post("/api/user-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uSER_REQUEST)))
            .andExpect(status().isCreated());

        // Validate the USER_REQUEST in the database
        List<USER_REQUEST> uSER_REQUESTList = uSER_REQUESTRepository.findAll();
        assertThat(uSER_REQUESTList).hasSize(databaseSizeBeforeCreate + 1);
        USER_REQUEST testUSER_REQUEST = uSER_REQUESTList.get(uSER_REQUESTList.size() - 1);
        assertThat(testUSER_REQUEST.getRequest_id()).isEqualTo(DEFAULT_REQUEST_ID);
        assertThat(testUSER_REQUEST.getSubmitted_by()).isEqualTo(DEFAULT_SUBMITTED_BY);
        assertThat(testUSER_REQUEST.getSubmitted_date()).isEqualTo(DEFAULT_SUBMITTED_DATE);
        assertThat(testUSER_REQUEST.getApproved_by()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testUSER_REQUEST.getApproval_date()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testUSER_REQUEST.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUSER_REQUEST.getAccount_numer()).isEqualTo(DEFAULT_ACCOUNT_NUMER);

        // Validate the USER_REQUEST in Elasticsearch
        verify(mockUSER_REQUESTSearchRepository, times(1)).save(testUSER_REQUEST);
    }

    @Test
    @Transactional
    public void createUSER_REQUESTWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uSER_REQUESTRepository.findAll().size();

        // Create the USER_REQUEST with an existing ID
        uSER_REQUEST.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUSER_REQUESTMockMvc.perform(post("/api/user-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uSER_REQUEST)))
            .andExpect(status().isBadRequest());

        // Validate the USER_REQUEST in the database
        List<USER_REQUEST> uSER_REQUESTList = uSER_REQUESTRepository.findAll();
        assertThat(uSER_REQUESTList).hasSize(databaseSizeBeforeCreate);

        // Validate the USER_REQUEST in Elasticsearch
        verify(mockUSER_REQUESTSearchRepository, times(0)).save(uSER_REQUEST);
    }


    @Test
    @Transactional
    public void getAllUSER_REQUESTS() throws Exception {
        // Initialize the database
        uSER_REQUESTRepository.saveAndFlush(uSER_REQUEST);

        // Get all the uSER_REQUESTList
        restUSER_REQUESTMockMvc.perform(get("/api/user-requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uSER_REQUEST.getId().intValue())))
            .andExpect(jsonPath("$.[*].request_id").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].submitted_by").value(hasItem(DEFAULT_SUBMITTED_BY)))
            .andExpect(jsonPath("$.[*].submitted_date").value(hasItem(DEFAULT_SUBMITTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].approved_by").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approval_date").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].account_numer").value(hasItem(DEFAULT_ACCOUNT_NUMER)));
    }
    
    @Test
    @Transactional
    public void getUSER_REQUEST() throws Exception {
        // Initialize the database
        uSER_REQUESTRepository.saveAndFlush(uSER_REQUEST);

        // Get the uSER_REQUEST
        restUSER_REQUESTMockMvc.perform(get("/api/user-requests/{id}", uSER_REQUEST.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uSER_REQUEST.getId().intValue()))
            .andExpect(jsonPath("$.request_id").value(DEFAULT_REQUEST_ID.intValue()))
            .andExpect(jsonPath("$.submitted_by").value(DEFAULT_SUBMITTED_BY))
            .andExpect(jsonPath("$.submitted_date").value(DEFAULT_SUBMITTED_DATE.toString()))
            .andExpect(jsonPath("$.approved_by").value(DEFAULT_APPROVED_BY))
            .andExpect(jsonPath("$.approval_date").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.account_numer").value(DEFAULT_ACCOUNT_NUMER));
    }

    @Test
    @Transactional
    public void getNonExistingUSER_REQUEST() throws Exception {
        // Get the uSER_REQUEST
        restUSER_REQUESTMockMvc.perform(get("/api/user-requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUSER_REQUEST() throws Exception {
        // Initialize the database
        uSER_REQUESTRepository.saveAndFlush(uSER_REQUEST);

        int databaseSizeBeforeUpdate = uSER_REQUESTRepository.findAll().size();

        // Update the uSER_REQUEST
        USER_REQUEST updatedUSER_REQUEST = uSER_REQUESTRepository.findById(uSER_REQUEST.getId()).get();
        // Disconnect from session so that the updates on updatedUSER_REQUEST are not directly saved in db
        em.detach(updatedUSER_REQUEST);
        updatedUSER_REQUEST
            .request_id(UPDATED_REQUEST_ID)
            .submitted_by(UPDATED_SUBMITTED_BY)
            .submitted_date(UPDATED_SUBMITTED_DATE)
            .approved_by(UPDATED_APPROVED_BY)
            .approval_date(UPDATED_APPROVAL_DATE)
            .status(UPDATED_STATUS)
            .account_numer(UPDATED_ACCOUNT_NUMER);

        restUSER_REQUESTMockMvc.perform(put("/api/user-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUSER_REQUEST)))
            .andExpect(status().isOk());

        // Validate the USER_REQUEST in the database
        List<USER_REQUEST> uSER_REQUESTList = uSER_REQUESTRepository.findAll();
        assertThat(uSER_REQUESTList).hasSize(databaseSizeBeforeUpdate);
        USER_REQUEST testUSER_REQUEST = uSER_REQUESTList.get(uSER_REQUESTList.size() - 1);
        assertThat(testUSER_REQUEST.getRequest_id()).isEqualTo(UPDATED_REQUEST_ID);
        assertThat(testUSER_REQUEST.getSubmitted_by()).isEqualTo(UPDATED_SUBMITTED_BY);
        assertThat(testUSER_REQUEST.getSubmitted_date()).isEqualTo(UPDATED_SUBMITTED_DATE);
        assertThat(testUSER_REQUEST.getApproved_by()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testUSER_REQUEST.getApproval_date()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testUSER_REQUEST.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUSER_REQUEST.getAccount_numer()).isEqualTo(UPDATED_ACCOUNT_NUMER);

        // Validate the USER_REQUEST in Elasticsearch
        verify(mockUSER_REQUESTSearchRepository, times(1)).save(testUSER_REQUEST);
    }

    @Test
    @Transactional
    public void updateNonExistingUSER_REQUEST() throws Exception {
        int databaseSizeBeforeUpdate = uSER_REQUESTRepository.findAll().size();

        // Create the USER_REQUEST

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUSER_REQUESTMockMvc.perform(put("/api/user-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uSER_REQUEST)))
            .andExpect(status().isBadRequest());

        // Validate the USER_REQUEST in the database
        List<USER_REQUEST> uSER_REQUESTList = uSER_REQUESTRepository.findAll();
        assertThat(uSER_REQUESTList).hasSize(databaseSizeBeforeUpdate);

        // Validate the USER_REQUEST in Elasticsearch
        verify(mockUSER_REQUESTSearchRepository, times(0)).save(uSER_REQUEST);
    }

    @Test
    @Transactional
    public void deleteUSER_REQUEST() throws Exception {
        // Initialize the database
        uSER_REQUESTRepository.saveAndFlush(uSER_REQUEST);

        int databaseSizeBeforeDelete = uSER_REQUESTRepository.findAll().size();

        // Delete the uSER_REQUEST
        restUSER_REQUESTMockMvc.perform(delete("/api/user-requests/{id}", uSER_REQUEST.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<USER_REQUEST> uSER_REQUESTList = uSER_REQUESTRepository.findAll();
        assertThat(uSER_REQUESTList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the USER_REQUEST in Elasticsearch
        verify(mockUSER_REQUESTSearchRepository, times(1)).deleteById(uSER_REQUEST.getId());
    }

    @Test
    @Transactional
    public void searchUSER_REQUEST() throws Exception {
        // Initialize the database
        uSER_REQUESTRepository.saveAndFlush(uSER_REQUEST);
        when(mockUSER_REQUESTSearchRepository.search(queryStringQuery("id:" + uSER_REQUEST.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uSER_REQUEST), PageRequest.of(0, 1), 1));
        // Search the uSER_REQUEST
        restUSER_REQUESTMockMvc.perform(get("/api/_search/user-requests?query=id:" + uSER_REQUEST.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uSER_REQUEST.getId().intValue())))
            .andExpect(jsonPath("$.[*].request_id").value(hasItem(DEFAULT_REQUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].submitted_by").value(hasItem(DEFAULT_SUBMITTED_BY)))
            .andExpect(jsonPath("$.[*].submitted_date").value(hasItem(DEFAULT_SUBMITTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].approved_by").value(hasItem(DEFAULT_APPROVED_BY)))
            .andExpect(jsonPath("$.[*].approval_date").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].account_numer").value(hasItem(DEFAULT_ACCOUNT_NUMER)));
    }
}
