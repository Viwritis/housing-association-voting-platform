package pl.redheadsolutions.lg.repository;

import pl.redheadsolutions.lg.domain.Inhabitant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Inhabitant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InhabitantRepository extends JpaRepository<Inhabitant, Long> {
}
