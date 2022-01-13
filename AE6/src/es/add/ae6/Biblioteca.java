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
	
	// M�todo: mostrarBD
	// Descripci�n: recibe las colecci�n de documentos de la BD en MongoDB y los recorre, convirtiendo la informaci�n escogida a string.
	// Par�metros de entrada: coleccion de documentos de MongoDB (creada a trav�s de MongoCollection en la clase principal).
	// Par�metros de salida: string con los datos (libros) de la BD.
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

	// M�todo: consultarLibro
	// Descripci�n: recupera un documento de la BD a partir de una id.
	// Par�metros de entrada: coleccion de documentos de MongoDB (creada a trav�s de MongoCollection en la clase principal) e id de tipo string.
	// Par�metros de salida: string con los datos del dato (libro) consultado.
	public static String consultarLibro(MongoCollection<Document> coleccion, String id) {

		String linea = "";
		
		try {
			query = eq("Id", id);
			cursor = coleccion.find(query).iterator();
			JSONObject obj = new JSONObject(cursor.next().toJson());
			linea += "\nID: " + obj.getString("Id") + " - T�tulo: " + obj.getString("Titol") + " - Autor: " + obj.getString("Autor") + " - A�o de nacimiento: " + 
					obj.getString("Any_naixement") + " - A�o de publicaci�n: " + obj.getString("Any_publicacio") + " - Editorial: " + obj.getString("Editorial") + " - N� p�ginas: " + obj.getString("Nombre_pagines");
		} catch (Exception e) {
			linea = "\nError en la consulta.";
		}

		return linea;
	}

	// M�todo: anyadirLibro
	// Descripci�n: a�ade un nuevo documento a la coleccion de MongoDB pasada por par�metro.
	// Par�metros de entrada: coleccion de documentos de MongoDB (creada a trav�s de MongoCollection en la clase principal) y strings.
	// Par�metros de salida: string que confirma el a�adido, mostrando los datos (libro) introducidos.
	public static String anyadirLibro(MongoCollection<Document> coleccion, String titulo, String autor, String anyoNac, String anyoPub,
			String editorial, String pags) {

		String linea = "";
		
		@SuppressWarnings("deprecation")
		int nuevoID = (int) coleccion.count() + 1 ;
		String nuevoIDString = Integer.toString(nuevoID);

		try {
			
			if (titulo.isBlank() || autor.isBlank() || anyoNac.isBlank() || anyoPub.isBlank() || editorial.isBlank() || pags.isBlank()) {
				linea = "\n Alguno(s) de los campos est�(n) vac�o(s). Si es intencional, introduce '-' en vez de espacios.";
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
			linea = "\nERROR en la inserci�n.";
		}

		return linea;

	}

	// M�todo: modificarLibro
	// Descripci�n: recupera un documento de la colecci�n de MongoDB a partir de una id y modifica sus par�metros.
	// Par�metros de entrada: coleccion de documentos de MongoDB (creada a trav�s de MongoCollection en la clase principal) y strings.
	// Par�metros de salida: String que confirma la modificaci�n, mostrando los datos (libro) modificados.
	public static String modificarLibro(MongoCollection<Document> coleccion, String id, String titulo, String autor, String anyoNac,
			String anyoPub, String editorial, String pags) {

		String linea = "";

		try {
			
			if (id.isBlank() || titulo.isBlank() || autor.isBlank() || anyoNac.isBlank() || anyoPub.isBlank() || editorial.isBlank() || pags.isBlank()) {
				linea = "\n Alguno(s) de los campos est�(n) vac�o(s). Si es intencional, introduce '-' en vez de espacios.";
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
			linea = "\nERROR en la modificaci�n de datos.";
		}

		return linea;
	}

	
	// M�todo: borrarLibro
		// Descripci�n: recupera un documento de la colecci�n de MongoDB a partir de una id y lo borra.
		// Par�metros de entrada: coleccion de documentos de MongoDB (creada a trav�s de MongoCollection en la clase principal) y strings.
		// Par�metros de salida: string que confirma el borrado.
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
