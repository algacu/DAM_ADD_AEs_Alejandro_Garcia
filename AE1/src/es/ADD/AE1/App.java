package es.ADD.AE1;

import java.io.File;
import java.sql.Date;
import java.util.Scanner;

public class App {

	
	public static void main(String[] args) {
		
		File f = new File(args[0]);
			
		if (f.exists()) {
			
			StartCli(f);
			
		} else {
			
			System.out.println("El directorio o fichero pasado por parámetro no existe.");
		
		}
	}
	
	
	
	public static void StartCli(File file) {
		
		Scanner teclado = new Scanner(System.in);
		int numero = 0;
		String continuar = "";
		
		System.out.println("Bienvenid@ a la App de gestión de directorios y ficheros.");
		
		do {
			
			System.out.println("\n¿Qué deseas hacer?\n"
					+ "\t1-Obtener información de un directorio o fichero\n"
					+ "\t2-Crear una carpeta en el directorio local\n"
					+ "\t3-Crear una fichero\n"
					+ "\t4-Eliminar un fichero o carpeta\n"
					+ "\t5-Renombrar un fichero o carpeta\n");
			
			while (numero < 1 || numero > 5) {
				System.out.print("Introduce una opción (1-5): ");
				numero = teclado.nextInt();
			}
			
			switch (numero) {
				case 1:
					try {
						GetInformacion(file);
					} catch (Exception e) {
						e.getMessage();
					}
					break;
		
				case 2:
					CreaCarpeta(file);
					break;
			}
			
			numero = 0;
			System.out.print("\n¿Deseas realizar otra operación? (si / no): ");
			continuar = teclado.next();
			
		} while (continuar.equals("si"));
		
		System.out.print("\nPrograma finalizado.");
	
		teclado.close();
		
	}
	
	
	
	public static void GetInformacion(File f) throws Exception {
			
		if (f.isDirectory()) {
			
			System.out.println("\n_INFORMACIÓN DEL DIRECTORIO_");
				
			System.out.println("Nombre: " + f.getName());
			System.out.println("Es directorio: " + f.isDirectory());
			System.out.println("Ruta completa: " + f.getAbsolutePath());

			long ultimaModificacion = f.lastModified();
			Date fecha = new Date(ultimaModificacion);
			System.out.println("Última modificación: " + fecha);

			System.out.println("¿Está oculto? " + f.isHidden());

			int espacioTotal = (int) (f.getTotalSpace()/1024/1024/1024);
			int espacioLibre = (int) (f.getFreeSpace()/1024/1024/1024);
			int espacioOcupado = espacioTotal - espacioLibre;
			System.out.println("Espacio total: " + espacioTotal + " GB");
			System.out.println("Espacio ocupado: " + espacioOcupado + " GB");
			System.out.println("Espacio libre: " + espacioLibre + " GB");

			String[] listaArchivos = f.list();
			System.out.println("Contiene: ");
			for (String elemento: listaArchivos) {
				System.out.println(elemento);
			}
				
		} else if (f.isFile()) {

			System.out.println("\n_INFORMACIÓN DEL FICHERO_");
			
			System.out.println("Nombre: " + f.getName());
			System.out.println("Es fichero: " + f.isFile());
			System.out.println("Ruta completa: " + f.getAbsolutePath());

			long ultimaModificacion = f.lastModified();
			Date fecha = new Date(ultimaModificacion);
			System.out.println("Última modificación: " + fecha);

			System.out.println("¿Está oculto? " + f.isHidden());

			System.out.println("Tamaño: " + f.length() + " bytes.");

		} else {
			throw new Exception("El objeto introducido debe ser un directorio o fichero.");
		}
			
	}
	
	
	
	public static void CreaCarpeta(File f) {
		
		Scanner teclado2 = new Scanner(System.in);
		
		boolean fallo = false;
		
		do {
			System.out.print("Introduce el nombre de la nueva carpeta: ");
			String nombreCarpeta = teclado2.next();

			String ruta = f.toString();

			String rutaNuevaCarpeta = ruta + "\\" + nombreCarpeta;

			File nuevaCarpeta = new File(rutaNuevaCarpeta);

			if (nuevaCarpeta.mkdir()) {
				fallo = false;
				System.out.println("Carpeta creada con éxito.");

			}else{
				fallo = true;
				System.out.println("Error al crear la carpeta. La carpeta ya existe o el nombre no es válido.");  
			}

		} while(fallo == true);
		
		teclado2.close();
	}
	
	
	

}
