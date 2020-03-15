package pl.redheadsolutions.lg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A HousingAssociation.
 */
@Entity
@Table(name = "housing_association")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HousingAssociation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "housing_association_name", nullable = false)
    private String housingAssociationName;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @OneToMany(mappedBy = "housingAssociation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inhabitant> inhabitants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHousingAssociationName() {
        return housingAssociationName;
    }

    public HousingAssociation housingAssociationName(String housingAssociationName) {
        this.housingAssociationName = housingAssociationName;
        return this;
    }

    public void setHousingAssociationName(String housingAssociationName) {
        this.housingAssociationName = housingAssociationName;
    }

    public Location getLocation() {
        return location;
    }

    public HousingAssociation location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public HousingAssociation inhabitants(Set<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
        return this;
    }

    public HousingAssociation addInhabitant(Inhabitant inhabitant) {
        this.inhabitants.add(inhabitant);
        inhabitant.setHousingAssociation(this);
        return this;
    }

    public HousingAssociation removeInhabitant(Inhabitant inhabitant) {
        this.inhabitants.remove(inhabitant);
        inhabitant.setHousingAssociation(null);
        return this;
    }

    public void setInhabitants(Set<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HousingAssociation)) {
            return false;
        }
        return id != null && id.equals(((HousingAssociation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HousingAssociation{" +
            "id=" + getId() +
            ", housingAssociationName='" + getHousingAssociationName() + "'" +
            "}";
    }
}
