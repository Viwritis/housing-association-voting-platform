package pl.redheadsolutions.lg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Conclusion.
 */
@Entity
@Table(name = "conclusion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conclusion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "conclusion_name", nullable = false)
    private String conclusionName;

    @NotNull
    @Column(name = "conclusion_content", nullable = false)
    private String conclusionContent;

    @OneToOne
    @JoinColumn(unique = true)
    private Voting voting;

    @ManyToOne
    @JsonIgnoreProperties("conclusions")
    private Inhabitant inhabitant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConclusionName() {
        return conclusionName;
    }

    public Conclusion conclusionName(String conclusionName) {
        this.conclusionName = conclusionName;
        return this;
    }

    public void setConclusionName(String conclusionName) {
        this.conclusionName = conclusionName;
    }

    public String getConclusionContent() {
        return conclusionContent;
    }

    public Conclusion conclusionContent(String conclusionContent) {
        this.conclusionContent = conclusionContent;
        return this;
    }

    public void setConclusionContent(String conclusionContent) {
        this.conclusionContent = conclusionContent;
    }

    public Voting getVoting() {
        return voting;
    }

    public Conclusion voting(Voting voting) {
        this.voting = voting;
        return this;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }

    public Inhabitant getInhabitant() {
        return inhabitant;
    }

    public Conclusion inhabitant(Inhabitant inhabitant) {
        this.inhabitant = inhabitant;
        return this;
    }

    public void setInhabitant(Inhabitant inhabitant) {
        this.inhabitant = inhabitant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conclusion)) {
            return false;
        }
        return id != null && id.equals(((Conclusion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Conclusion{" +
            "id=" + getId() +
            ", conclusionName='" + getConclusionName() + "'" +
            ", conclusionContent='" + getConclusionContent() + "'" +
            "}";
    }
}
