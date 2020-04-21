package br.com.dinobri.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.dinobri.web.rest.TestUtil;

public class GrupoTagTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoTag.class);
        GrupoTag grupoTag1 = new GrupoTag();
        grupoTag1.setId(1L);
        GrupoTag grupoTag2 = new GrupoTag();
        grupoTag2.setId(grupoTag1.getId());
        assertThat(grupoTag1).isEqualTo(grupoTag2);
        grupoTag2.setId(2L);
        assertThat(grupoTag1).isNotEqualTo(grupoTag2);
        grupoTag1.setId(null);
        assertThat(grupoTag1).isNotEqualTo(grupoTag2);
    }
}
