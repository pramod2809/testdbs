package com.org.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A USER_REQUEST.
 */
@Entity
@Table(name = "user_request")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "user_request")
public class USER_REQUEST implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id")
    private Long request_id;

    @Column(name = "submitted_by")
    private String submitted_by;

    @Column(name = "submitted_date")
    private LocalDate submitted_date;

    @Column(name = "approved_by")
    private String approved_by;

    @Column(name = "approval_date")
    private LocalDate approval_date;

    @Column(name = "status")
    private String status;

    @Column(name = "account_numer")
    private Integer account_numer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRequest_id() {
        return request_id;
    }

    public USER_REQUEST request_id(Long request_id) {
        this.request_id = request_id;
        return this;
    }

    public void setRequest_id(Long request_id) {
        this.request_id = request_id;
    }

    public String getSubmitted_by() {
        return submitted_by;
    }

    public USER_REQUEST submitted_by(String submitted_by) {
        this.submitted_by = submitted_by;
        return this;
    }

    public void setSubmitted_by(String submitted_by) {
        this.submitted_by = submitted_by;
    }

    public LocalDate getSubmitted_date() {
        return submitted_date;
    }

    public USER_REQUEST submitted_date(LocalDate submitted_date) {
        this.submitted_date = submitted_date;
        return this;
    }

    public void setSubmitted_date(LocalDate submitted_date) {
        this.submitted_date = submitted_date;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public USER_REQUEST approved_by(String approved_by) {
        this.approved_by = approved_by;
        return this;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public LocalDate getApproval_date() {
        return approval_date;
    }

    public USER_REQUEST approval_date(LocalDate approval_date) {
        this.approval_date = approval_date;
        return this;
    }

    public void setApproval_date(LocalDate approval_date) {
        this.approval_date = approval_date;
    }

    public String getStatus() {
        return status;
    }

    public USER_REQUEST status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAccount_numer() {
        return account_numer;
    }

    public USER_REQUEST account_numer(Integer account_numer) {
        this.account_numer = account_numer;
        return this;
    }

    public void setAccount_numer(Integer account_numer) {
        this.account_numer = account_numer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof USER_REQUEST)) {
            return false;
        }
        return id != null && id.equals(((USER_REQUEST) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "USER_REQUEST{" +
            "id=" + getId() +
            ", request_id=" + getRequest_id() +
            ", submitted_by='" + getSubmitted_by() + "'" +
            ", submitted_date='" + getSubmitted_date() + "'" +
            ", approved_by='" + getApproved_by() + "'" +
            ", approval_date='" + getApproval_date() + "'" +
            ", status='" + getStatus() + "'" +
            ", account_numer=" + getAccount_numer() +
            "}";
    }
}
