package es.add.ae4;

public class Libro {
	
String titulo, autor, anyo_nac, anyo_pub, editorial, pags;
	
	Libro(String titulo, String autor, String anyo_nac, String anyo_pub, String editorial, String pags){
		this.titulo = titulo;
		this.autor = autor;
		this.anyo_nac = anyo_nac;
		this.anyo_pub = anyo_pub;
		this.editorial = editorial;
		this.pags = pags;
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
		return anyo_nac;
	}
	
	public void setAnyoNac(String anyoNac) {
		anyo_nac = anyoNac;
	}
	
	public String getAnyoPub() {
		return anyo_pub;
	}
	
	public void setAnyoPub(String anyoPub) {
		anyo_pub = anyoPub;
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
		String info = "Título: " + this.titulo + " - Autor: " + this.autor + " - Año nacimiento: " + this.anyo_nac 
				+ " - Año publicación: " + this.anyo_pub + " - Editorial: " + this.editorial + "- Nº páginas: " + this.pags;
		return info;
	}

}
