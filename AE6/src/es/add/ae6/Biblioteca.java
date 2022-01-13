package es.add.ae6;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;

public class Biblioteca {
	
	static MongoCursor<Document> cursor;
	static Bson query;
	
	// Método: mostrarBD
	// Descripción: recibe las colección de documentos de la BD en MongoDB y los recorre, convirtiendo la información escogida a string.
	// Parámetros de entrada: coleccion de documentos de MongoDB (creada a través de MongoCollection en la clase principal).
	// Parámetros de salida: string con los datos (libros) de la BD.
	public static String mostrarBD(MongoCollection<Document> coleccion) {

		String linea = "";

		try {

			cursor = coleccion.find().iterator();
			
			while(cursor.hasNext()) {
				JSONObject obj = new JSONObject(cursor.next().toJson());
				linea += "\n" + obj.getString("Id") + " - " + obj.getString("Titol");
			}

		} catch (Exception e) {
			linea = "\nERROR en la consulta.";
		}

		return linea;
	}

	// Método: consultarLibro
	// Descripción: recupera un documento de la BD a partir de una id.
	// Parámetros de entrada: coleccion de documentos de MongoDB (creada a través de MongoCollection en la clase principal) e id de tipo string.
	// Parámetros de salida: string con los datos del dato (libro) consultado.
	public static String consultarLibro(MongoCollection<Document> coleccion, String id) {

		String linea = "";
		
		try {
			query = eq("Id", id);
			cursor = coleccion.find(query).iterator();
			JSONObject obj = new JSONObject(cursor.next().toJson());
			linea += "\nID: " + obj.getString("Id") + " - Título: " + obj.getString("Titol") + " - Autor: " + obj.getString("Autor") + " - Año de nacimiento: " + 
					obj.getString("Any_naixement") + " - Año de publicación: " + obj.getString("Any_publicacio") + " - Editorial: " + obj.getString("Editorial") + " - Nº páginas: " + obj.getString("Nombre_pagines");
		} catch (Exception e) {
			linea = "\nError en la consulta.";
		}

		return linea;
	}

	// Método: anyadirLibro
	// Descripción: añade un nuevo documento a la coleccion de MongoDB pasada por parámetro.
	// Parámetros de entrada: coleccion de documentos de MongoDB (creada a través de MongoCollection en la clase principal) y strings.
	// Parámetros de salida: string que confirma el añadido, mostrando los datos (libro) introducidos.
	public static String anyadirLibro(MongoCollection<Document> coleccion, String titulo, String autor, String anyoNac, String anyoPub,
			String editorial, String pags) {

		String linea = "";
		
		@SuppressWarnings("deprecation")
		int nuevoID = (int) coleccion.count() + 1 ;
		String nuevoIDString = Integer.toString(nuevoID);

		try {
			
			if (titulo.isBlank() || autor.isBlank() || anyoNac.isBlank() || anyoPub.isBlank() || editorial.isBlank() || pags.isBlank()) {
				linea = "\n Alguno(s) de los campos está(n) vacío(s). Si es intencional, introduce '-' en vez de espacios.";
			} else {
				Document doc = new Document();
				doc.append("Id", nuevoIDString);
				doc.append("Titol", titulo);
				doc.append("Autor", autor);
				doc.append("Any_naixement", anyoNac);
				doc.append("Any_publicacio", anyoPub);
				doc.append("Editorial", editorial);
				doc.append("Nombre_pagines", pags);
				coleccion.insertOne(doc);
				linea += consultarLibro(coleccion, nuevoIDString);
			}

		} catch (Exception e) {
			linea = "\nERROR en la inserción.";
		}

		return linea;

	}

	// Método: modificarLibro
	// Descripción: recupera un documento de la colección de MongoDB a partir de una id y modifica sus parámetros.
	// Parámetros de entrada: coleccion de documentos de MongoDB (creada a través de MongoCollection en la clase principal) y strings.
	// Parámetros de salida: String que confirma la modificación, mostrando los datos (libro) modificados.
	public static String modificarLibro(MongoCollection<Document> coleccion, String id, String titulo, String autor, String anyoNac,
			String anyoPub, String editorial, String pags) {

		String linea = "";

		try {
			
			if (id.isBlank() || titulo.isBlank() || autor.isBlank() || anyoNac.isBlank() || anyoPub.isBlank() || editorial.isBlank() || pags.isBlank()) {
				linea = "\n Alguno(s) de los campos está(n) vacío(s). Si es intencional, introduce '-' en vez de espacios.";
			} else {
				coleccion.updateOne(eq("Id", id), Updates.set("Titol", titulo));
				coleccion.updateOne(eq("Id", id), Updates.set("Autor", autor));
				coleccion.updateOne(eq("Id", id), Updates.set("Any_naixement", anyoNac));
				coleccion.updateOne(eq("Id", id), Updates.set("Any_publicacio", anyoPub));
				coleccion.updateOne(eq("Id", id), Updates.set("Editorial", editorial));
				coleccion.updateOne(eq("Id", id), Updates.set("Nombre_pagines", pags));
				linea += consultarLibro(coleccion, id);
			}
			
		} catch (Exception e) {
			linea = "\nERROR en la modificación de datos.";
		}

		return linea;
	}

	
	// Método: borrarLibro
		// Descripción: recupera un documento de la colección de MongoDB a partir de una id y lo borra.
		// Parámetros de entrada: coleccion de documentos de MongoDB (creada a través de MongoCollection en la clase principal) y strings.
		// Parámetros de salida: string que confirma el borrado.
		public static String borrarLibro(MongoCollection<Document> coleccion, String id) {
			String linea = "";
			String libro = "";

			try {
				
				libro = consultarLibro(coleccion, id);

				coleccion.deleteOne(eq("Id", id));

				linea += libro;

			} catch (Exception e) {
				linea = "\nERROR en el borrado.";
			}

			return linea;
		}

}
