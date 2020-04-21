package br.com.dinobri.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Edicao.
 */
@Entity
@Table(name = "edicao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Edicao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "referencia")
    private String referencia;

    @OneToMany(mappedBy = "edicao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Materia> materias = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("edicaos")
    private Revista revista;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Edicao numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public Edicao dataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
        return this;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getReferencia() {
        return referencia;
    }

    public Edicao referencia(String referencia) {
        this.referencia = referencia;
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Set<Materia> getMaterias() {
        return materias;
    }

    public Edicao materias(Set<Materia> materias) {
        this.materias = materias;
        return this;
    }

    public Edicao addMateria(Materia materia) {
        this.materias.add(materia);
        materia.setEdicao(this);
        return this;
    }

    public Edicao removeMateria(Materia materia) {
        this.materias.remove(materia);
        materia.setEdicao(null);
        return this;
    }

    public void setMaterias(Set<Materia> materias) {
        this.materias = materias;
    }

    public Revista getRevista() {
        return revista;
    }

    public Edicao revista(Revista revista) {
        this.revista = revista;
        return this;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Edicao)) {
            return false;
        }
        return id != null && id.equals(((Edicao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Edicao{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", dataPublicacao='" + getDataPublicacao() + "'" +
            ", referencia='" + getReferencia() + "'" +
            "}";
    }
}
