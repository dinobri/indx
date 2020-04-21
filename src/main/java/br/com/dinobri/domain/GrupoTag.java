package br.com.dinobri.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A GrupoTag.
 */
@Entity
@Table(name = "grupo_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GrupoTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cor")
    private String cor;

    @OneToMany(mappedBy = "grupoTag")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tag> tags = new HashSet<>();

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

    public GrupoTag nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public GrupoTag cor(String cor) {
        this.cor = cor;
        return this;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public GrupoTag tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public GrupoTag addTag(Tag tag) {
        this.tags.add(tag);
        tag.setGrupoTag(this);
        return this;
    }

    public GrupoTag removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.setGrupoTag(null);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoTag)) {
            return false;
        }
        return id != null && id.equals(((GrupoTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GrupoTag{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cor='" + getCor() + "'" +
            "}";
    }
}
