package pl.redheadsolutions.lg.repository;

import pl.redheadsolutions.lg.domain.HousingAssociation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the HousingAssociation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HousingAssociationRepository extends JpaRepository<HousingAssociation, Long> {
}
