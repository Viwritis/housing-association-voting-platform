package pl.redheadsolutions.lg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.redheadsolutions.lg.web.rest.TestUtil;

public class InhabitantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inhabitant.class);
        Inhabitant inhabitant1 = new Inhabitant();
        inhabitant1.setId(1L);
        Inhabitant inhabitant2 = new Inhabitant();
        inhabitant2.setId(inhabitant1.getId());
        assertThat(inhabitant1).isEqualTo(inhabitant2);
        inhabitant2.setId(2L);
        assertThat(inhabitant1).isNotEqualTo(inhabitant2);
        inhabitant1.setId(null);
        assertThat(inhabitant1).isNotEqualTo(inhabitant2);
    }
}
