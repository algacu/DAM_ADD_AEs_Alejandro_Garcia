package es.ADD.AE1;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
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
		boolean continuar = true;
		
		System.out.println("Bienvenid@ a la App de gestión de directorios y ficheros.");
		
		do {
			
			System.out.println("\n¿Qué deseas hacer?\n"
					+ "\t1-Obtener información de un directorio o fichero\n"
					+ "\t2-Crear una carpeta en el directorio local\n"
					+ "\t3-Crear una fichero\n"
					+ "\t4-Eliminar un fichero o carpeta\n"
					+ "\t5-Renombrar un fichero o carpeta\n");
			
			try {
				while (numero < 1 || numero > 5) {
				System.out.print("Introduce una opción (1-5): ");
				numero = teclado.nextInt();
				}
			} catch (Exception e) {
				System.out.println("\nError: el dato introducido no es un número.");
				break;
			}
			
			switch (numero) {
			
				case 1:
					GetInformacion(file);
					break;
		
				case 2:
					CreaCarpeta(file);
					break;
				
				case 3:
					CreaFichero(file);
					break;
					
				case 4:
					EliminaFichero(file);
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
		
		System.out.print("\nPrograma finalizado.");
		
		teclado.close();
		
	}
	
	
	
	public static void GetInformacion(File f) {
		
		try {
			
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void CreaCarpeta(File f) {
		
		try {
			
			Scanner teclado = new Scanner(System.in);
			
			boolean fallo = false;
			
			do {
				System.out.print("Introduce el nombre de la nueva carpeta: ");
				String nombreCarpeta = teclado.next();

				String ruta = f.toString();

				String rutaNuevaCarpeta = ruta + "\\" + nombreCarpeta;

				File nuevaCarpeta = new File(rutaNuevaCarpeta);

				if (nuevaCarpeta.mkdir()) {
					fallo = false;
					System.out.println("\nCarpeta creada con éxito.");
				}else{
					fallo = true;
					System.out.println("Error al crear la carpeta. La carpeta ya existe o el nombre no es válido."); 
				}
				
			} while(fallo == true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void CreaFichero(File f){
		
		try {
			
			if (f.isDirectory()) {
				
				FileFilter filtroCarpetas = new FileFilter() {
					@Override
					public boolean accept(File arch) {
						return arch.isDirectory();
					}
				};
					
				File[] listaCarpetas = f.listFiles(filtroCarpetas);
				
				if (listaCarpetas == null || listaCarpetas.length == 0) {
					System.out.println("El directorio actual no contiene subcarpetas. Escribe *.* para crear el fichero en el directorio actual.");
				} else {
					System.out.println("\nSubdirectorios disponibles: ");
					for (File carpeta: listaCarpetas) {
						System.out.println("\t"+ carpeta.getName());
					}
				}
				
				Scanner teclado = new Scanner(System.in);
				
				boolean fallo = false;
				
				do {
					System.out.print("\nEscribe el subdirectorio donde deseas crear el fichero o escribe *local* para crearlo en el directorio actual: ");

					String nombreCarpeta = teclado.next();

					String ruta = f.toString();
					
					String rutaCarpeta;
					
					if (nombreCarpeta.equals("local")) {
						rutaCarpeta = ruta;
					} else {
						rutaCarpeta = ruta + "\\" + nombreCarpeta;
					}

					File carpeta = new File(rutaCarpeta);

					if (carpeta.exists() && carpeta.isDirectory()) {

						System.out.print("Introduce el nombre del fichero y su extensión (por ejemplo, 'fichero.txt'): ");
						String nombreFichero = teclado.next();

						String rutaFichero = rutaCarpeta + "\\" + nombreFichero;

						File fichero = new File (rutaFichero);

						if (fichero.createNewFile()) {
							System.out.println("\nEl fichero se ha creado correctamente.");
							fallo = false;
						} else {
							System.out.println("\nNo se ha podido crear el fichero.");
							fallo = true;
						}

					} else {
						System.out.println("\nLa carpeta seleccionada no existe.");
						fallo = true;
					}
				} while(fallo == true);

					
			} else if (f.isFile()) {
				throw new Exception("El objeto pasado por parámetro es un fichero.");
				
			} else {
				throw new Exception("El objeto introducido debe ser un directorio o fichero.");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	
	
public static void EliminaFichero(File f) {
		
	try {
		
		if (f.isDirectory()) {

			FileFilter filtroFicheros = new FileFilter() {
				@Override
				public boolean accept(File arch) {
					return arch.isFile();
				}
			};

			File[] listaFicheros = f.listFiles(filtroFicheros);

			if (listaFicheros == null || listaFicheros.length == 0) {
				System.out.println("El directorio actual no contiene ficheros.");
			} else {
				System.out.println("\nFicheros existentes: ");
				for (File fichero: listaFicheros) {
					System.out.println("\t"+ fichero.getName());
				}
			}

			Scanner teclado = new Scanner(System.in);

			boolean fallo = false;

			do {
				System.out.print("\nEscribe el nombre del fichero que deseas eliminar, incluyendo su extensión (por ejemplo, 'fichero.txt'): ");

				String nombreFichero = teclado.next();

				String ruta = f.toString();

				String rutaFichero = ruta + "\\" + nombreFichero;

				File fichero = new File(rutaFichero);

				if (fichero.exists()) {

					if (fichero.delete()) {
						System.out.println("\nEl fichero se ha eliminado correctamente.");
						fallo = false;
					} else {
						System.out.println("\nNo se ha podido eliminar el fichero.");
						fallo = true;
					}

				} else {
					System.out.println("\nEl fichero introducido no existe.");
					fallo = true;
				}
			} while(fallo == true);


		} else if (f.isFile()) {
			throw new Exception("El objeto pasado por parámetro es un fichero.");

		} else {
			throw new Exception("El objeto introducido debe ser un directorio o fichero.");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
		
	}
	

}
