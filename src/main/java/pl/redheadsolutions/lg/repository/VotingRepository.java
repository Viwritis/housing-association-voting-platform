package pl.redheadsolutions.lg.repository;

import pl.redheadsolutions.lg.domain.Voting;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Voting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {
}
