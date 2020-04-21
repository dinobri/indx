package br.com.dinobri.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dinobri.web.rest.TestUtil;

public class RevistaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Revista.class);
        Revista revista1 = new Revista();
        revista1.setId(1L);
        Revista revista2 = new Revista();
        revista2.setId(revista1.getId());
        assertThat(revista1).isEqualTo(revista2);
        revista2.setId(2L);
        assertThat(revista1).isNotEqualTo(revista2);
        revista1.setId(null);
        assertThat(revista1).isNotEqualTo(revista2);
    }
}
