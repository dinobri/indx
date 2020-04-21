package br.com.dinobri.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JsonIgnoreProperties("tags")
    private GrupoTag grupoTag;

    @ManyToMany(mappedBy = "tags")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Materia> materias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Tag nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GrupoTag getGrupoTag() {
        return grupoTag;
    }

    public Tag grupoTag(GrupoTag grupoTag) {
        this.grupoTag = grupoTag;
        return this;
    }

    public void setGrupoTag(GrupoTag grupoTag) {
        this.grupoTag = grupoTag;
    }

    public Set<Materia> getMaterias() {
        return materias;
    }

    public Tag materias(Set<Materia> materias) {
        this.materias = materias;
        return this;
    }

    public Tag addMateria(Materia materia) {
        this.materias.add(materia);
        materia.getTags().add(this);
        return this;
    }

    public Tag removeMateria(Materia materia) {
        this.materias.remove(materia);
        materia.getTags().remove(this);
        return this;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        return id != null && id.equals(((Tag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
