package es.add.ae6;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class Principal {

	public static void main(String[] args) {
		
		try {
			
			//Realizamos conexi�n con MongoDB y obtemos la colecci�n de documentos de la BD.
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase database = mongoClient.getDatabase("Biblioteca");
			MongoCollection<Document> coleccion = database.getCollection("Libros");

			//Ejecuci�n del modelo vista controlador (el controlador recibe la coleccion por par�metro).
			Vista vista = new Vista();
			Biblioteca biblioteca = new Biblioteca();
			Controlador controlador = new Controlador(vista, biblioteca, coleccion);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
