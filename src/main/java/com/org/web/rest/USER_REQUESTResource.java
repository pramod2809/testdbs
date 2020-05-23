package com.org.web.rest;

import com.org.domain.USER_REQUEST;
import com.org.repository.USER_REQUESTRepository;
import com.org.repository.search.USER_REQUESTSearchRepository;
import com.org.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.org.domain.USER_REQUEST}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class USER_REQUESTResource {

    private final Logger log = LoggerFactory.getLogger(USER_REQUESTResource.class);

    private static final String ENTITY_NAME = "uSER_REQUEST";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final USER_REQUESTRepository uSER_REQUESTRepository;

    private final USER_REQUESTSearchRepository uSER_REQUESTSearchRepository;

    public USER_REQUESTResource(USER_REQUESTRepository uSER_REQUESTRepository, USER_REQUESTSearchRepository uSER_REQUESTSearchRepository) {
        this.uSER_REQUESTRepository = uSER_REQUESTRepository;
        this.uSER_REQUESTSearchRepository = uSER_REQUESTSearchRepository;
    }

    /**
     * {@code POST  /user-requests} : Create a new uSER_REQUEST.
     *
     * @param uSER_REQUEST the uSER_REQUEST to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uSER_REQUEST, or with status {@code 400 (Bad Request)} if the uSER_REQUEST has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-requests")
    public ResponseEntity<USER_REQUEST> createUSER_REQUEST(@RequestBody USER_REQUEST uSER_REQUEST) throws URISyntaxException {
        log.debug("REST request to save USER_REQUEST : {}", uSER_REQUEST);
        if (uSER_REQUEST.getId() != null) {
            throw new BadRequestAlertException("A new uSER_REQUEST cannot already have an ID", ENTITY_NAME, "idexists");
        }
        USER_REQUEST result = uSER_REQUESTRepository.save(uSER_REQUEST);
        uSER_REQUESTSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-requests} : Updates an existing uSER_REQUEST.
     *
     * @param uSER_REQUEST the uSER_REQUEST to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uSER_REQUEST,
     * or with status {@code 400 (Bad Request)} if the uSER_REQUEST is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uSER_REQUEST couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-requests")
    public ResponseEntity<USER_REQUEST> updateUSER_REQUEST(@RequestBody USER_REQUEST uSER_REQUEST) throws URISyntaxException {
        log.debug("REST request to update USER_REQUEST : {}", uSER_REQUEST);
        if (uSER_REQUEST.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        USER_REQUEST result = uSER_REQUESTRepository.save(uSER_REQUEST);
        uSER_REQUESTSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uSER_REQUEST.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-requests} : get all the uSER_REQUESTS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uSER_REQUESTS in body.
     */
    @GetMapping("/user-requests")
    public ResponseEntity<List<USER_REQUEST>> getAllUSER_REQUESTS(Pageable pageable) {
        log.debug("REST request to get a page of USER_REQUESTS");
        Page<USER_REQUEST> page = uSER_REQUESTRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-requests/:id} : get the "id" uSER_REQUEST.
     *
     * @param id the id of the uSER_REQUEST to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uSER_REQUEST, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-requests/{id}")
    public ResponseEntity<USER_REQUEST> getUSER_REQUEST(@PathVariable Long id) {
        log.debug("REST request to get USER_REQUEST : {}", id);
        Optional<USER_REQUEST> uSER_REQUEST = uSER_REQUESTRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(uSER_REQUEST);
    }

    /**
     * {@code DELETE  /user-requests/:id} : delete the "id" uSER_REQUEST.
     *
     * @param id the id of the uSER_REQUEST to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-requests/{id}")
    public ResponseEntity<Void> deleteUSER_REQUEST(@PathVariable Long id) {
        log.debug("REST request to delete USER_REQUEST : {}", id);
        uSER_REQUESTRepository.deleteById(id);
        uSER_REQUESTSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/user-requests?query=:query} : search for the uSER_REQUEST corresponding
     * to the query.
     *
     * @param query the query of the uSER_REQUEST search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-requests")
    public ResponseEntity<List<USER_REQUEST>> searchUSER_REQUESTS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of USER_REQUESTS for query {}", query);
        Page<USER_REQUEST> page = uSER_REQUESTSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
