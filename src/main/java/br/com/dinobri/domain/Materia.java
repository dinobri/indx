package br.com.dinobri.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Materia.
 */
@Entity
@Table(name = "materia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "pagina_inicial")
    private Integer paginaInicial;

    @Column(name = "pagina_final")
    private Integer paginaFinal;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "materia_tag",
               joinColumns = @JoinColumn(name = "materia_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "materia_autor",
               joinColumns = @JoinColumn(name = "materia_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id"))
    private Set<Autor> autors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("materias")
    private Secao secao;

    @ManyToOne
    @JsonIgnoreProperties("materias")
    private Edicao edicao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Materia titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Materia descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPaginaInicial() {
        return paginaInicial;
    }

    public Materia paginaInicial(Integer paginaInicial) {
        this.paginaInicial = paginaInicial;
        return this;
    }

    public void setPaginaInicial(Integer paginaInicial) {
        this.paginaInicial = paginaInicial;
    }

    public Integer getPaginaFinal() {
        return paginaFinal;
    }

    public Materia paginaFinal(Integer paginaFinal) {
        this.paginaFinal = paginaFinal;
        return this;
    }

    public void setPaginaFinal(Integer paginaFinal) {
        this.paginaFinal = paginaFinal;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Materia tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Materia addTag(Tag tag) {
        this.tags.add(tag);
        tag.getMaterias().add(this);
        return this;
    }

    public Materia removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getMaterias().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public Materia autors(Set<Autor> autors) {
        this.autors = autors;
        return this;
    }

    public Materia addAutor(Autor autor) {
        this.autors.add(autor);
        autor.getMaterias().add(this);
        return this;
    }

    public Materia removeAutor(Autor autor) {
        this.autors.remove(autor);
        autor.getMaterias().remove(this);
        return this;
    }

    public void setAutors(Set<Autor> autors) {
        this.autors = autors;
    }

    public Secao getSecao() {
        return secao;
    }

    public Materia secao(Secao secao) {
        this.secao = secao;
        return this;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public Edicao getEdicao() {
        return edicao;
    }

    public Materia edicao(Edicao edicao) {
        this.edicao = edicao;
        return this;
    }

    public void setEdicao(Edicao edicao) {
        this.edicao = edicao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materia)) {
            return false;
        }
        return id != null && id.equals(((Materia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Materia{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", paginaInicial=" + getPaginaInicial() +
            ", paginaFinal=" + getPaginaFinal() +
            "}";
    }
}
