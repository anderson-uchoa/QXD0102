package quixada.ufc.br.kisan.model;


import android.media.Image;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by andersonuchoa on 08/12/15.
 */
public class Livro implements Serializable {

    private Long id;

    private byte[] foto;

    private String titulo;

    private String sinopse;

    private String numeroPaginas;

    private Usuario usuario;

    private List<Autor> autores;

    private String genero;

    public Livro() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(String numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", foto=" + Arrays.toString(foto) +
                ", titulo='" + titulo + '\'' +
                ", sinopse='" + sinopse + '\'' +
                ", numeroPaginas='" + numeroPaginas + '\'' +
                ", usuario=" + usuario +
                ", autores=" + autores +
                ", genero='" + genero + '\'' +
                '}';
    }
}


