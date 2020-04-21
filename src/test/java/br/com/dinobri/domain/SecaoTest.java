package br.com.dinobri.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dinobri.web.rest.TestUtil;

public class SecaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Secao.class);
        Secao secao1 = new Secao();
        secao1.setId(1L);
        Secao secao2 = new Secao();
        secao2.setId(secao1.getId());
        assertThat(secao1).isEqualTo(secao2);
        secao2.setId(2L);
        assertThat(secao1).isNotEqualTo(secao2);
        secao1.setId(null);
        assertThat(secao1).isNotEqualTo(secao2);
    }
}
