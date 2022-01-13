package es.add.ae6;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class Principal {

	public static void main(String[] args) {
		
		try {
			
			//Realizamos conexión con MongoDB y obtemos la colección de documentos de la BD.
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase database = mongoClient.getDatabase("Biblioteca");
			MongoCollection<Document> coleccion = database.getCollection("Libros");

			//Ejecución del modelo vista controlador (el controlador recibe la coleccion por parámetro).
			Vista vista = new Vista();
			Biblioteca biblioteca = new Biblioteca();
			Controlador controlador = new Controlador(vista, biblioteca, coleccion);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
