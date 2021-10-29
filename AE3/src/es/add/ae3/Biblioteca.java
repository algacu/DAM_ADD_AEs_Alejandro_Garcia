package es.add.ae3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.w3c.dom.NodeList;

public class Biblioteca {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		System.out.println("AE3 ADD - Alejandro Garc�a Cuesta" 
				+ "\nBienvenido a la BIBLIOTECA VIRTUAL");
		
		//Al arrancar el programa, la funci�n recuperarTodos() lee el archivo XML y carga los libros en ArrayList de objetos de tipo Libro.
		ArrayList<Libro> listaLibros = recuperarTodos();
		
		Scanner teclado = new Scanner(System.in);
		int numero = 0;
		boolean continuar = true;
		int idLibro;

		do {
			
			try {
				while (numero < 1 || numero > 6) {
				System.out.println("\nOperaciones:"
					+ "\n\t1. Mostrar todos los t�tulos de la biblioteca"
					+ "\n\t2. Mostrar informaci�n detallada de un libro"
					+ "\n\t3. Crear nuevo libro"
					+ "\n\t4. Actualizar libro"
					+ "\n\t5. Borrar libro"
					+ "\n\t6. Cerrar la biblioteca");
				System.out.print("Elige una opci�n: ");
				numero = teclado.nextInt();
				}
			} catch (InputMismatchException ime) {
				System.out.println("\nError: el dato introducido no es un n�mero.");
				System.exit(0);	
			}
			
			try {
				switch(numero) {
					case 1:
						System.out.println("\n--LISTA DE LIBROS-- ");
						for (Libro libro : listaLibros) {
							System.out.println("\nID: " + libro.getId());
							System.out.println("T�tulo: " + libro.getTitulo());
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
						System.out.print("\nBiblioteca cerrada �Vuelve pronto!");
						System.exit(0);	
				}
			} catch (InputMismatchException ime) {
				System.out.println("\nError: el dato introducido no es un n�mero.");
				break;
			}
			
			numero = 0;
			
			System.out.print("\n�Deseas realizar otra operaci�n? (si / no): ");
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
		
		System.out.print("\nBiblioteca cerrada �Vuelve pronto!");
		teclado.close();
	}
	
	
	//M�todo: crearLibro
		//MODIFICADO CON RESPECTO AL ENUNCIADO (recibe lista de objetos Libro por par�metro).
		// Descripci�n: m�todo que lee el fichero XML para obtener el n�mero (id) de nodos (libros) y crear un objeto de tipo libro con la siguiente id (n�mero de nodo) disponble.
		//	+Despu�s de crear el objeto de tipo Libro, instancia el m�todo auxiliar 'guardarXML' para registrar los cambios en el fichero XML.
		// Par�metros de entrada: ArrayList de objetos tipo Libro.
		// Par�metros de salida: id (n�mero de nodo en el fichero XML) del objeto de tipo Libro creado.
	static int crearLibro(ArrayList<Libro> lista) throws ParserConfigurationException, SAXException, IOException {
		
		//Primero averiguamos la primera ID disponible en el XML
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("libros.xml"));
		NodeList nodeList = document.getElementsByTagName("libro");
		int numNodos = nodeList.getLength();
		int id = numNodos + 1;
		String idString = String.valueOf(id);
	
		//Pedimos datos al usuario
		System.out.println("\nA�ade un nuevo libro completando los siguientes datos:");
		Scanner teclado = new Scanner(System.in);
		System.out.print("T�tulo: "); 
		String titulo = teclado.nextLine();
		System.out.print("Autor: ");
		String autor = teclado.nextLine();
		System.out.print("A�o de publicaci�n: ");
		String anyo = teclado.nextLine();
		System.out.print("Editorial: ");
		String editorial = teclado.nextLine();
		System.out.print("N� de p�ginas: ");
		String pags = teclado.nextLine();
		
		Libro nuevoLibro = new Libro(idString, titulo, autor, anyo, editorial, pags);
		
		lista.add(nuevoLibro);
		
		try {
			guardarXML(lista);
		} catch (IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print("\n--Libro a�adido a la colecci�n--\n");
		
		return id;
	}
	
	
	//M�todo: recuperarLibro
			// Descripci�n: m�todo que recibe un valor y busca en el fichero XML el nodo/elemento con el valor seleccionado (el identificador es el atributo id del elemento)
			// Par�metros de entrada: entero.
			// Par�metros de salida: objeto de tipo libro, cuya informaci�n se extrae del fichero XML tras seleccionar el elemento correcto seg�n el identificador.
	static Libro recuperarLibro(int identificador) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("libros.xml"));
		NodeList nodeList = document.getElementsByTagName("libro");
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
	
	
	//M�todo: mostrarLibro
	// Descripci�n: m�todo que recibe objeto de tipo Libro e imprime por pantalla sus atributos.
	// Par�metros de entrada: objeto de tipo Libro.
	// Par�metros de salida: void (imprime por pantalla).
	static void mostrarLibro(Libro libro) {
		System.out.println("\nID: " + libro.getId()
				+ "\nT�tulo: " + libro.getTitulo()
				+ "\nAutor: " + libro.getAutor()
				+ "\nAnyo:" + libro.getAnyo()
				+ "\nEditorial:" + libro.getEditorial()
				+ "\nN� de p�ginas:" + libro.getPags());
	}
	
	
	//M�todo: borrarLibro
		//MODIFICADO CON RESPECTO AL ENUNCIADO (recibe lista de objetos Libro por par�metro, adem�s del identificador).
		// Descripci�n: m�todo que recibe un valor y una lista de objetos tipo Libro por par�metro.
		//	+busca en la lista de libros el libro con el valor (identificador) seleccionado y lo borra.
		//  +Despu�s de borar el libro seleccionado, recorre de nuevo la lista de libros reasignado valores del atributo ID de
		//	+cada objeto tipo libro, para evitar incoherencias en la correlaci�n de IDs (n�mero de nodos en el fichero XML).
		//	+Finalmente, los cambios se registran en el fichero XML instanciando el m�todo auxiliar 'guardarXML', pasando por par�metro la lista de libros actualizada.
		// Par�metros de entrada: entero, ArrayList de objetos tipo Libro.
		// Par�metros de salida: void.
	static void borrarLibro(int identificador, ArrayList<Libro> lista) throws ParserConfigurationException, SAXException, IOException {
				
		for (Libro libro : lista) {
			if (libro.getId().equals(String.valueOf(identificador))) {
				System.out.println("\nLibro seleccionado para borrar: " + libro.toString());
				lista.remove(libro);
				System.out.println("Libro borrado correctamente.");
				break;
			}
		}
		
		for (int i = 0; i < lista.size(); i++) {
			lista.get(i).setId(String.valueOf(i+1));
		}
		
		try {
			guardarXML(lista);
		} catch (IOException | TransformerException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	
	//M�todo: actualizarLibro
			//MODIFICADO CON RESPECTO AL ENUNCIADO (recibe lista de objetos Libro por par�metro, adem�s del identificador).
			// Descripci�n: m�todo que recibe un valor y una lista de objetos tipo Libro por par�metro.
			//	+busca en la lista de libros el libro con el valor (identificador) seleccionado y cambia sus atributos al recibir nuevos datos por teclado.
			//	+A continuaci�n, los cambios se registran en el fichero XML instanciando el m�todo auxiliar 'guardarXML', pasando por par�metro la lista de libros actualizada.
			// Par�metros de entrada: entero, ArrayList de objetos tipo Libro.
			// Par�metros de salida: void.
	static void actualizarLibro(int identificador, ArrayList<Libro> lista) throws ParserConfigurationException, SAXException, IOException {

		Scanner teclado = new Scanner(System.in);
		
		for (Libro libro : lista) {
			
			if (libro.getId().equals(String.valueOf(identificador))) {
				
				System.out.print("\nLibro seleccionado para modificar: " + libro.toString());
	
				System.out.print("\nNuevo t�tulo: "); 
				String titulo = teclado.nextLine();
				libro.setTitulo(titulo);
				
				System.out.print("Nuevo autor: ");
				String autor = teclado.nextLine();
				libro.setAutor(autor);
				
				System.out.print("Nuevo a�o de publicaci�n: ");
				String anyo = teclado.nextLine();
				libro.setAnyo(anyo);
				
				System.out.print("Nueva editorial: ");
				String editorial = teclado.nextLine();
				libro.setEditorial(editorial);
				
				System.out.print("Nuevo n� de p�ginas: ");
				String pags = teclado.nextLine();
				libro.setPags(pags);
			}
		}
		
		System.out.println("\nLibro modificado correctamente.");
		
		try {
			guardarXML(lista);
		} catch (IOException | TransformerException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//M�todo: recuperarTodos
	// Descripci�n: m�todo que lee el contenido de un fichero XML y crea una lista de objetos de tipo Libro a partir de los datos del fichero.
	// Par�metros de entrada: -
	// Par�metros de salida: ArrayList de objetos tipo Libro.
	static ArrayList<Libro> recuperarTodos() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(new File("libros.xml"));
		NodeList nodeList = document.getElementsByTagName("libro");
		
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
	
	
	//M�todo: guardarXML
		// Descripci�n: m�todo que recibe una lista de objetos tipo Libro y los convierte en elementos de un fichero XML, escribi�ndolo.
		// Par�metros de entrada: ArrayList de objetos tipo Libro.
		// Par�metros de salida: void.
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
