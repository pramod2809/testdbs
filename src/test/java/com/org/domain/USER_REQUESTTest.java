package com.org.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.org.web.rest.TestUtil;

public class USER_REQUESTTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(USER_REQUEST.class);
        USER_REQUEST uSER_REQUEST1 = new USER_REQUEST();
        uSER_REQUEST1.setId(1L);
        USER_REQUEST uSER_REQUEST2 = new USER_REQUEST();
        uSER_REQUEST2.setId(uSER_REQUEST1.getId());
        assertThat(uSER_REQUEST1).isEqualTo(uSER_REQUEST2);
        uSER_REQUEST2.setId(2L);
        assertThat(uSER_REQUEST1).isNotEqualTo(uSER_REQUEST2);
        uSER_REQUEST1.setId(null);
        assertThat(uSER_REQUEST1).isNotEqualTo(uSER_REQUEST2);
    }
}
