package pl.redheadsolutions.lg.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pl.redheadsolutions.lg.web.rest.TestUtil;

public class HousingAssociationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HousingAssociation.class);
        HousingAssociation housingAssociation1 = new HousingAssociation();
        housingAssociation1.setId(1L);
        HousingAssociation housingAssociation2 = new HousingAssociation();
        housingAssociation2.setId(housingAssociation1.getId());
        assertThat(housingAssociation1).isEqualTo(housingAssociation2);
        housingAssociation2.setId(2L);
        assertThat(housingAssociation1).isNotEqualTo(housingAssociation2);
        housingAssociation1.setId(null);
        assertThat(housingAssociation1).isNotEqualTo(housingAssociation2);
    }
}
