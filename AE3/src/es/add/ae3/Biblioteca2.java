package es.add.ae3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;

import org.w3c.dom.NodeList;

public class Biblioteca2 {
	
	
//	ArrayList<Libro> listaLibros;
//
//	Biblioteca (){
//		try {
//			listaLibros = recuperarTodos();
//		} catch (ParserConfigurationException | SAXException | IOException e) {
//			e.printStackTrace();
//		}
//	}
	

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		System.out.println("AE3 ADD - Alejandro García Cuesta" 
				+ "\nBienvenido a la BIBLIOTECA VIRTUAL");
		
		ArrayList<Libro> listaLibros = recuperarTodos();
		
		Scanner teclado = new Scanner(System.in);
		int numero = 0;
		boolean continuar = true;
		int idLibro;

		do {
			
			try {
				while (numero < 1 || numero > 6) {
				System.out.println("\nOperaciones:"
					+ "\n\t1. Mostrar todos los títulos de la biblioteca"
					+ "\n\t2. Mostrar información detallada de un libro"
					+ "\n\t3. Crear nuevo libro"
					+ "\n\t4. Actualizar libro"
					+ "\n\t5. Borrar libro"
					+ "\n\t6. Cerrar la biblioteca");
				System.out.print("Elige una opción: ");
				numero = teclado.nextInt();
				}
			} catch (Exception e) {
				System.out.println("\nError: el dato introducido no es un número.");
				System.exit(0);	
			}
			
			switch(numero) {
			case 1:
//				ArrayList<Libro> lista = recuperarTodos();
				System.out.println("\n--LISTA DE LIBROS-- ");
				for (Libro libro : listaLibros) {
					System.out.println("\nID: " + libro.getId());
					System.out.println("Título: " + libro.getTitulo());
				}
				break;
			case 2:
				System.out.print("Introduce la ID del libro: ");
				idLibro = teclado.nextInt();
				mostrarLibro(recuperarLibro(idLibro));
				break;
			case 3:
				mostrarLibro(recuperarLibro(crearLibro(listaLibros)));
				break;
			case 4:
				System.out.print("Introduce la ID del libro: ");
				idLibro = teclado.nextInt();
				actualizarLibro(idLibro, listaLibros);
				break;
			case 5:
				System.out.print("Introduce la ID del libro: ");
				idLibro = teclado.nextInt();
				borrarLibro(idLibro, listaLibros);
				break;
			case 6:
				System.out.print("\nBiblioteca cerrada ¡Vuelve pronto!");
				System.exit(0);	
				break;
			}
			
			numero = 0;
			
			System.out.print("\n¿Deseas realizar otra operación? (si / no): ");
			String respuesta = teclado.next();

			if (respuesta.equals("Si") || respuesta.equals("si") || respuesta.equals("SI")) {
				continuar = true;
			} else if (respuesta.equals("No") || respuesta.equals("no") || respuesta.equals("NO")) {
				continuar = false;
			} else {
				System.out.print("\nComando incorrecto.\n");
				continuar = false;
			}
			
			
		} while (continuar == true);
		
		System.out.print("\nBiblioteca cerrada ¡Vuelve pronto!.");
		teclado.close();
	}
	
	
	static int crearLibro(ArrayList<Libro> lista) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("libros.xml"));
		NodeList nodeList = document.getElementsByTagName("libro");
		
		int numNodos = nodeList.getLength();
		int id = numNodos + 1;
		String idString = String.valueOf(id);
	
		System.out.println("\nAñade un nuevo libro completando los siguientes datos:");
		
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("Título: "); 
		String titulo = teclado.nextLine();
		
		System.out.print("Autor: ");
		String autor = teclado.nextLine();
		
		System.out.print("Año de publicación: ");
		String anyo = teclado.nextLine();
		
		System.out.print("Editorial: ");
		String editorial = teclado.nextLine();
		
		System.out.print("Nº de páginas: ");
		String pags = teclado.nextLine();
			
		Libro nuevoLibro = new Libro(idString, titulo, autor, anyo, editorial, pags);
			
		lista.add(nuevoLibro);
		
		try {
			guardarXML(lista);
		} catch (IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print("\n--Libro añadido a la colección--\n");
		
		return id;
	}
	
	
	static Libro recuperarLibro(int identificador) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("libros.xml"));
		//Element raiz = document.getDocumentElement();
		//System.out.println("Contenido XML " + raiz.getNodeName()+": ");
		NodeList nodeList = document.getElementsByTagName("libro");
		//System.out.println("Número total de nodos (películas): " + nodeList.getLength());
		Node nodo = nodeList.item(identificador-1);
		Element elemento = (Element) nodo;
		String id = elemento.getAttribute("id");
		String titulo = elemento.getElementsByTagName("titulo").item(0).getTextContent();
		String autor = elemento.getElementsByTagName("autor").item(0).getTextContent();
		String anyo = elemento.getElementsByTagName("anyo").item(0).getTextContent();
		String editorial = elemento.getElementsByTagName("editorial").item(0).getTextContent();
		String pags = elemento.getElementsByTagName("pags").item(0).getTextContent();
		Libro libro = new Libro(id, titulo, autor, anyo, editorial, pags);
		return libro;
	}
	
	
	static void mostrarLibro(Libro libro) {
		System.out.println("\nID: " + libro.getId()
				+ "\nTítulo: " + libro.getTitulo()
				+ "\nAutor: " + libro.getAutor()
				+ "\nAnyo:" + libro.getAnyo()
				+ "\nEditorial:" + libro.getEditorial()
				+ "\nNº de páginas:" + libro.getPags());
	}
	
	
	static void borrarLibro(int identificador, ArrayList<Libro> lista) throws ParserConfigurationException, SAXException, IOException {
				
		for (Libro libro : lista) {
			if (libro.getId().equals(String.valueOf(identificador))) {
				lista.remove(libro);
				System.out.println("borrado");
				break;
			}
		}
		
		int numero = 1;
		
		for (Libro libro : lista) {
			libro.setId(String.valueOf(numero));
			numero++;
		}
		
		try {
			guardarXML(lista);
		} catch (IOException | TransformerException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	static void actualizarLibro(int identificador, ArrayList<Libro> lista) throws ParserConfigurationException, SAXException, IOException {
//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		Document document = dBuilder.parse(new File("libros.xml"));
//		NodeList nodeList = document.getElementsByTagName("libro");
//		Node nodo = nodeList.item(identificador);
		
		Scanner teclado = new Scanner(System.in);
		
		for (Libro libro : lista) {
			
			if (libro.getId().equals(String.valueOf(identificador))) {
				
				System.out.print("\nTítulo: "); 
				String titulo = teclado.nextLine();
				libro.setTitulo(titulo);
				
				System.out.print("Autor: ");
				String autor = teclado.nextLine();
				libro.setAutor(autor);
				
				System.out.print("Año de publicación: ");
				String anyo = teclado.nextLine();
				libro.setAnyo(anyo);
				
				System.out.print("Editorial: ");
				String editorial = teclado.nextLine();
				libro.setEditorial(editorial);
				
				System.out.print("Nº de páginas: ");
				String pags = teclado.nextLine();
				libro.setPags(pags);
			}
		}
		
		try {
			guardarXML(lista);
		} catch (IOException | TransformerException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	static ArrayList<Libro> recuperarTodos() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("libros.xml"));
		//Element raiz = document.getDocumentElement();
		//System.out.println("Contenido XML " + raiz.getNodeName()+": ");
		NodeList nodeList = document.getElementsByTagName("libro");
		//System.out.println("Número total de nodos (películas): " + nodeList.getLength());
		
		ArrayList<Libro> lista = new ArrayList<Libro>();
		
		for (int i=0; i < nodeList.getLength(); i++) {
			Node nodo = nodeList.item(i);
			Element elemento = (Element) nodo;
			String id = elemento.getAttribute("id");
			String titulo = elemento.getElementsByTagName("titulo").item(0).getTextContent();
			String autor = elemento.getElementsByTagName("autor").item(0).getTextContent();
			String anyo = elemento.getElementsByTagName("anyo").item(0).getTextContent();
			String editorial = elemento.getElementsByTagName("editorial").item(0).getTextContent();
			String pags = elemento.getElementsByTagName("pags").item(0).getTextContent();
			Libro libro = new Libro(id, titulo, autor, anyo, editorial, pags);
			lista.add(libro);
		}
		return lista;
	}
	
	
	static void guardarXML(ArrayList<Libro> lista) throws IOException, TransformerException, ParserConfigurationException {
		
		//PREPARAMOS EL DOM
		DocumentBuilderFactory dobuFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder build = dobuFactory.newDocumentBuilder();
		Document doc = build.newDocument();
		Element nuevaRaiz = doc.createElement("libros");
		doc.appendChild(nuevaRaiz);
		
		for (Libro libro : lista) {
			
			Element elementoLibro = doc.createElement("libro");
			String id = String.valueOf(libro.getId());
			elementoLibro.setAttribute("id", id);
			nuevaRaiz.appendChild(elementoLibro);
			
			Element titulo = doc.createElement("titulo");
			titulo.appendChild(doc.createTextNode(String.valueOf(libro.getTitulo())));
			elementoLibro.appendChild(titulo);
			
			Element autor = doc.createElement("autor");
			autor.appendChild(doc.createTextNode(String.valueOf(libro.getAutor())));
			elementoLibro.appendChild(autor);
			
			Element anyo = doc.createElement("anyo");
			anyo.appendChild(doc.createTextNode(String.valueOf(libro.getAnyo())));
			elementoLibro.appendChild(anyo);
			
			Element editorial = doc.createElement("editorial");
			editorial.appendChild(doc.createTextNode(String.valueOf(libro.getEditorial())));
			elementoLibro.appendChild(editorial);
			
			Element pags = doc.createElement("pags");
			pags.appendChild(doc.createTextNode(String.valueOf(libro.getPags())));
			elementoLibro.appendChild(pags);
		}
		
		//GUARDAR EL DOM EN DISCO
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer aTransformer = transFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");
		aTransformer.setOutputProperty(OutputKeys.INDENT,"yes");
		
		DOMSource source = new DOMSource(doc);
		
		FileWriter fw = new FileWriter("libros.xml");
		StreamResult result = new StreamResult(fw);
		aTransformer.transform(source, result);
		fw.close();
	}
	
	
	
	
	
	

}
