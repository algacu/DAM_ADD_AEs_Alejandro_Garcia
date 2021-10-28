package es.add.ae3;

public class Libro {

	String id, titulo, autor, anyo, editorial, pags;

	Libro (String id, String titulo, String autor, String anyo, String editorial, String pags) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.anyo = anyo;
		this.editorial = editorial;
		this.pags = pags;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getAnyo() {
		return anyo;
	}
	
	public void setAnyo(String anyo) {
		this.anyo = anyo;
	}
	
	public String getEditorial() {
		return editorial;
	}
	
	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	
	public String getPags() {
		return pags;
	}
	
	public void setPags(String pags) {
		this.pags = pags;
	}
	
	public String toString() {
		String info = "Id: " + this.id + " - Título: " + this.titulo + " - Autor: " + this.autor + " - Año publicación: " + this.anyo 
				+ " - Editorial: " + this.editorial + " - Páginas: " + this.pags;
		return info;
	}
}
