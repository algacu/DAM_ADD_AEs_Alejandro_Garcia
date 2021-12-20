package es.add.ae5;

public class Libro {
	
String titulo, autor, anyoNac, anyoPub, editorial, pags;
int id;
	
	Libro(String titulo, String autor, String anyoNac, String anyoPub, String editorial, String pags){
		this.titulo = titulo;
		this.autor = autor;
		this.anyoNac = anyoNac;
		this.anyoPub = anyoPub;
		this.editorial = editorial;
		this.pags = pags;
	}
	
	Libro(int id, String titulo, String autor, String anyoNac, String anyoPub, String editorial, String pags){
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.anyoNac = anyoNac;
		this.anyoPub = anyoPub;
		this.editorial = editorial;
		this.pags = pags;
	}
	
	Libro(){

	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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

	public String getAnyoNac() {
		return anyoNac;
	}
	
	public void setAnyoNac(String anyoNac) {
		this.anyoNac = anyoNac;
	}
	
	public String getAnyoPub() {
		return anyoPub;
	}
	
	public void setAnyoPub(String anyoPub) {
		this.anyoPub = anyoPub;
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
		String info = "Título: " + this.titulo + " - Autor: " + this.autor + " - Año nacimiento: " + this.anyoNac 
				+ " - Año publicación: " + this.anyoPub + " - Editorial: " + this.editorial + "- Nº páginas: " + this.pags;
		return info;
	}

}
