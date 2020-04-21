package br.com.dinobri.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Autor.
 */
@Entity
@Table(name = "autor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nome_real")
    private String nomeReal;

    @Column(name = "alcunha")
    private String alcunha;

    @ManyToMany(mappedBy = "autors")
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

    public Autor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeReal() {
        return nomeReal;
    }

    public Autor nomeReal(String nomeReal) {
        this.nomeReal = nomeReal;
        return this;
    }

    public void setNomeReal(String nomeReal) {
        this.nomeReal = nomeReal;
    }

    public String getAlcunha() {
        return alcunha;
    }

    public Autor alcunha(String alcunha) {
        this.alcunha = alcunha;
        return this;
    }

    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    public Set<Materia> getMaterias() {
        return materias;
    }

    public Autor materias(Set<Materia> materias) {
        this.materias = materias;
        return this;
    }

    public Autor addMateria(Materia materia) {
        this.materias.add(materia);
        materia.getAutors().add(this);
        return this;
    }

    public Autor removeMateria(Materia materia) {
        this.materias.remove(materia);
        materia.getAutors().remove(this);
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
        if (!(o instanceof Autor)) {
            return false;
        }
        return id != null && id.equals(((Autor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Autor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", nomeReal='" + getNomeReal() + "'" +
            ", alcunha='" + getAlcunha() + "'" +
            "}";
    }
}
