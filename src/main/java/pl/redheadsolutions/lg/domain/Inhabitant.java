package pl.redheadsolutions.lg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Inhabitant.
 */
@Entity
@Table(name = "inhabitant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inhabitant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "inhabitant")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Conclusion> conclusions = new HashSet<>();

    @OneToMany(mappedBy = "inhabitant")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<News> news = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("inhabitants")
    private HousingAssociation housingAssociation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Inhabitant phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Conclusion> getConclusions() {
        return conclusions;
    }

    public Inhabitant conclusions(Set<Conclusion> conclusions) {
        this.conclusions = conclusions;
        return this;
    }

    public Inhabitant addConclusion(Conclusion conclusion) {
        this.conclusions.add(conclusion);
        conclusion.setInhabitant(this);
        return this;
    }

    public Inhabitant removeConclusion(Conclusion conclusion) {
        this.conclusions.remove(conclusion);
        conclusion.setInhabitant(null);
        return this;
    }

    public void setConclusions(Set<Conclusion> conclusions) {
        this.conclusions = conclusions;
    }

    public Set<News> getNews() {
        return news;
    }

    public Inhabitant news(Set<News> news) {
        this.news = news;
        return this;
    }

    public Inhabitant addNews(News news) {
        this.news.add(news);
        news.setInhabitant(this);
        return this;
    }

    public Inhabitant removeNews(News news) {
        this.news.remove(news);
        news.setInhabitant(null);
        return this;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    public HousingAssociation getHousingAssociation() {
        return housingAssociation;
    }

    public Inhabitant housingAssociation(HousingAssociation housingAssociation) {
        this.housingAssociation = housingAssociation;
        return this;
    }

    public void setHousingAssociation(HousingAssociation housingAssociation) {
        this.housingAssociation = housingAssociation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inhabitant)) {
            return false;
        }
        return id != null && id.equals(((Inhabitant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inhabitant{" +
            "id=" + getId() +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
