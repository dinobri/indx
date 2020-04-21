package br.com.dinobri.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import br.com.dinobri.domain.enumeration.Periodicidade;

/**
 * A Revista.
 */
@Entity
@Table(name = "revista")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Revista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicidade")
    private Periodicidade periodicidade;

    @OneToMany(mappedBy = "revista")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Secao> secaos = new HashSet<>();

    @OneToMany(mappedBy = "revista")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Edicao> edicaos = new HashSet<>();

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

    public Revista nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Revista descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public Revista periodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
        return this;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Set<Secao> getSecaos() {
        return secaos;
    }

    public Revista secaos(Set<Secao> secaos) {
        this.secaos = secaos;
        return this;
    }

    public Revista addSecao(Secao secao) {
        this.secaos.add(secao);
        secao.setRevista(this);
        return this;
    }

    public Revista removeSecao(Secao secao) {
        this.secaos.remove(secao);
        secao.setRevista(null);
        return this;
    }

    public void setSecaos(Set<Secao> secaos) {
        this.secaos = secaos;
    }

    public Set<Edicao> getEdicaos() {
        return edicaos;
    }

    public Revista edicaos(Set<Edicao> edicaos) {
        this.edicaos = edicaos;
        return this;
    }

    public Revista addEdicao(Edicao edicao) {
        this.edicaos.add(edicao);
        edicao.setRevista(this);
        return this;
    }

    public Revista removeEdicao(Edicao edicao) {
        this.edicaos.remove(edicao);
        edicao.setRevista(null);
        return this;
    }

    public void setEdicaos(Set<Edicao> edicaos) {
        this.edicaos = edicaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Revista)) {
            return false;
        }
        return id != null && id.equals(((Revista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Revista{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", periodicidade='" + getPeriodicidade() + "'" +
            "}";
    }
}
