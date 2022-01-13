package es.add.ae6;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Biblioteca {
	

	// M�todo: mostrarBD
	// Descripci�n: recibe las session, instancia un objeto modelo de tipo libro y va recuper objetos 
	//	del mismo tipo de la BD. Los guarda en una lista y, a continuaci�n, los encadena en un string 
	//	para mostrarlos posteriormente por pantalla.
	// Par�metros de entrada: session (creada a trav�s de session factory en principal).
	// Par�metros de salida: string con los datos (libros) de la BD.
	public static String mostrarBD(MongoCollection<Document> coleccion) {

		String linea = "";

		try {

			MongoCursor<Document> cursor = coleccion.find().iterator();
			
			while(cursor.hasNext()) {
				JSONObject obj = new JSONObject(cursor.next().toJson());
				linea += obj.getString("Id") + " - " + obj.getString("Titol") + "\n";
			}

		} catch (Exception e) {
			linea = "ERROR en la conexi�n con la BD.";
		}

		return linea;
	}

	// M�todo: consultarLibro
	// Descripci�n: recupera un objeto de tipo Libro de la BD a partir de una id.
	// Par�metros de entrada: session (creada a trav�s de session factory en principal) e id de tipo int.
	// Par�metros de salida: string con los datos del dato (libro) consultado.
	public static String consultarLibro(MongoCollection<Document> coleccion, int id) {

		String linea = "";

		Bson query = eq("Id", id);
		MongoCursor<Document> cursor = coleccion.find(query).iterator();
		while(cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			linea += obj.getString("Id") + " - " + obj.getString("Titol") + "\n";
		}

		return linea;
	}

	// M�todo: anyadirLibro
	// Descripci�n: crea un objeto de tipo libro y lo guarda en la BD con una nueva id.
	// Par�metros de entrada: session (creada a trav�s de session factory en principal) y strings.
	// Par�metros de salida: string que confirma el a�adido, mostrando los datos (libro) introducidos.
	public static String anyadirLibro(MongoCollection<Document> coleccion, String titulo, String autor, String anyoNac, String anyoPub,
			String editorial, String pags) {

		String linea = "";

		try {
			
			Document doc = new Document();
			doc.append("Titol", titulo);
			doc.append("Autor", autor);
			doc.append("Any_naixement", anyoNac);
			doc.append("Any_publicacio", anyoPub);
			doc.append("Editorial", editorial);
			doc.append("Nombre_pagines", pags);

			coleccion.insertOne(doc);
			
			linea += "Libro a�adido correctamente.";

		} catch (Exception e) {
			linea = "\nERROR en la inserci�n.";
		}

		return linea;

	}

	// M�todo: modificarLibro
	// Descripci�n: recupera un objeto de tipo Libro de la BD a partir de una id y modifica sus par�metros.
	// Par�metros de entrada: session (creada a trav�s de session factory en principal) y strings.
	// Par�metros de salida: String que confirma la modificaci�n, mostrando los datos (libro) modificados.
	public static String modificarLibro(MongoCollection<Document> coleccion, int id, String titulo, String autor, String anyoNac,
			String anyoPub, String editorial, String pags) {

		String linea = "";

		try {
			
			coleccion.updateOne(eq("Id", id), new Document("$set", new Document("formato", "OGG")));
			
			coleccion.replaceOne(eq("Id", id), new Document("Titol", titulo),
			         new Document()
			         .append("Autor", autor)
			         .append("Anyo_naixement", anyoNac)
			         .append("Anyo_publicacio", anyoPub)
			         .append("Editorial", editorial)
			         .append("Nombre_pagines", pags));

			linea += "Libro modificado correctamente";

		} catch (Exception e) {
			linea = "\nERROR en la modificaci�n de datos.";
		}

		return linea;
	}

	
	// M�todo: borrarLibro
		// Descripci�n: recupera un objeto de tipo Libro de la BD a partir de una id y lo borra.
		// Par�metros de entrada: session (creada a trav�s de session factory en principal) e id de tipo int.
		// Par�metros de salida: string que confirma el borrado.
		public static String borrarLibro(MongoCollection<Document> coleccion, int id) {
			String linea = "";

			try {

				coleccion.deleteOne(eq("Id", id));

				linea += "\nLibro borrado correctamente. Id: " + id;

			} catch (Exception e) {
				linea = "\nERROR en el borrado.";
			}

			return linea;
		}
	
	
	
	
//	// M�todo: guardarBD
//	// Descripci�n: hace commit de la sesi�n actual con la BD remota y cierra la sesi�n.
//	// Par�metros de entrada: session (creada a trav�s de session factory en principal).
//	// Par�metros de salida: string.
//	public static String guardarBD(Session session) {
//
//		String linea = "";
//
//		try {
//			session.getTransaction().commit();
//			session.close();
//
//		} catch (Exception e) {
//			linea = "\nERROR en el guardado de la BD.";
//		}
//
//		return linea;
//	}

}
