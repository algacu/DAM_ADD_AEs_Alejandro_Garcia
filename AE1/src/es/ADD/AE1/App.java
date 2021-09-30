package es.ADD.AE1;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.sql.Date;
import java.util.Scanner;

public class App {

	
	// Método: main
	// Descripción: método principal. Recibe un string por parámetro de entrada que convierte en objeto de tipo File.
	//				Si el objeto File cumple las condiciones marcadas, invoca un método (StartMenu) que da opciones al usuario.
	// Parámetros de entrada: string pasado por parámetro.
	// Parámetros de salida: no
	
	public static void main(String[] args) {
		
		File f = new File(args[0]);
			
		if (f.exists() && f.isDirectory() && f.canWrite() && f.canRead()) {
			
			StartMenu(f);
			
		} else {
			System.out.println("El directorio pasado por parámetro no existe, no es un directorio o está protegido.");
		}
	}
	
	
	
	// Método: StartMenu
		// Descripción: método que funciona como interfaz-menú que da opciones al usuario.
		//				El método comprueba tanto que la opción seleccionada por el usuario sea válida y
		//				también incorpora un objeto de tipo booleano (continuar) para volver a mostrar las opciones
		//				si el usuario lo desea, una vez finalice el método escogido, para lanzar uno nuevo.
		// Parámetros de entrada: objeto de tipo file (directorio) pasado por parámetro.
		// Parámetros de salida: no
	
	public static void StartMenu(File file) {
		
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
					Elimina(file);
					break;
					
				case 5:
					Renombra(file);
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
	
	
	
	// Método: GetInformacion
			// Descripción: método que ofrece información sobre el directorio o fichero escogido por el usuario.			
			//				El método comprueba que el elmento indicado sea carpeta o fichero y muestra diferente
			//				información según el caso.
			//				también incorpora un objeto de tipo booleano (continuar) para volver a mostrar las opciones
			//				si el usuario lo desea, una vez finalice el método escogido, para lanzar uno nuevo.
			// Parámetros de entrada: objeto de tipo file (directorio) pasado por parámetro.
			// Parámetros de salida: no

	public static void GetInformacion(File f) {
		
		try {
				
			File[] listaElementos = f.listFiles();
			
			//Se muestran al usuario los elementos existentes en el directorio.		
			if (listaElementos == null || listaElementos.length == 0) {
				System.out.println("El directorio actual no contiene elementos. Escribe 'directorioactual' para obtener información del directorio actual..");
			} else {
				System.out.println("\nElementos disponibles: ");
				for (File carpeta: listaElementos) {
					System.out.println("\t"+ carpeta.getName());
				}
			}
			
			Scanner teclado = new Scanner(System.in);
			
			boolean fallo = false;
			
			//El siguiente bucle do-while sirve para preguntar constantemente al usuario en caso de que cometa algún error al introducir datos.
			do {
				
				//Se pide al usuario que seleccione un elemento, generando su ruta para crear un objeto de tipo File y acceder a sus opciones.
				System.out.print("\nEscribe el nombre del elemento para obtener info. o introduce 'estedirectorio' para obtener info. del directorio actual. Escribe 'salir' para cancelar: ");

				String nombreElemento = teclado.next();

				String ruta = f.toString();
				
				String nuevaRuta;
				
				if (nombreElemento.equals("estedirectorio") || nombreElemento.equals("ESTEDIRECTORIO")) {
					nuevaRuta = ruta;
				} else if (nombreElemento.equals("salir") || nombreElemento.equals("SALIR")){
					break;
				} else {
					nuevaRuta = ruta + "\\" + nombreElemento;
				}

				File elemento = new File(nuevaRuta);
				
				
				//Se comprueba que el elemento elegido exista, sea un directorio o fichero y se pueda leer.
				if (elemento.exists() && elemento.isDirectory() && elemento.canRead()) {
					
					System.out.println("\n_INFORMACIÓN DEL DIRECTORIO_");
					
					System.out.println("\nNombre: " + elemento.getName());
					System.out.println("Es directorio: " + elemento.isDirectory());
					System.out.println("Ruta completa: " + elemento.getAbsolutePath());

					long ultimaModificacion = elemento.lastModified();
					Date fecha = new Date(ultimaModificacion);
					System.out.println("Última modificación: " + fecha);

					System.out.println("¿Está oculto? " + elemento.isHidden());
					System.out.println("¿Se puede leer? " + elemento.canRead());
					System.out.println("¿Se puede modificar? " + elemento.canWrite());

					int espacioTotal = (int) (elemento.getTotalSpace()/1024/1024/1024);
					int espacioLibre = (int) (elemento.getFreeSpace()/1024/1024/1024);
					int espacioOcupado = espacioTotal - espacioLibre;
					System.out.println("Espacio total: " + espacioTotal + " GB");
					System.out.println("Espacio ocupado: " + espacioOcupado + " GB");
					System.out.println("Espacio libre: " + espacioLibre + " GB");

			
					String[] listaArchivos = elemento.list();
					
					System.out.println("\nContiene: ");
					
					if (listaArchivos != null) {
						for (String item: listaArchivos) {
							System.out.println(item);
						}
					} else {
						System.out.println("\nNo se puede acceder al contenido de la carpeta (es privada).");
					}
					
					fallo = false;

				} else if (elemento.exists() && elemento.isFile() && elemento.canRead()) {
					
					System.out.println("\n_INFORMACIÓN DEL FICHERO_");
					
					System.out.println("\nNombre: " + elemento.getName());
					System.out.println("Es fichero: " + elemento.isFile());
					System.out.println("Ruta completa: " + elemento.getAbsolutePath());

					long ultimaModificacion = elemento.lastModified();
					Date fecha = new Date(ultimaModificacion);
					System.out.println("Última modificación: " + fecha);

					System.out.println("¿Está oculto? " + elemento.isHidden());
					System.out.println("¿Se puede leer? " + elemento.canRead());
					System.out.println("¿Se puede modificar? " + elemento.canWrite());

					System.out.println("Tamaño: " + elemento.length() + " bytes.");
					
					fallo = false;
				
				} else {
					System.out.println("\nEl elemento seleccionado no existe o no se puede leer.");
					fallo = true;
				}
				
			} while(fallo == true);

				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// Método: CreaCarpeta
				// Descripción: método que crea una carpeta con el nombre escogido por el usuario en el directorio actual.
				// Parámetros de entrada: objeto de tipo file (directorio) pasado por parámetro.
				// Parámetros de salida: no.
	
	public static void CreaCarpeta(File f) {
		
		try {
			
			Scanner teclado = new Scanner(System.in);
			
			boolean fallo = false;
			
			//Se pide un nombre al usuario para la nueva carpeta. El bucle se repite si el nombre escogido no es válido (a través de la variable booleana 'fallo').
			do {
				System.out.print("Introduce el nombre de la nueva carpeta o escribe 'salir' para cancelar: ");
				String nombreCarpeta = teclado.next();
				
				if (nombreCarpeta.equals("salir") || nombreCarpeta.equals("SALIR")){
					break;
				}

				String ruta = f.toString();

				String rutaNuevaCarpeta = ruta + "\\" + nombreCarpeta;

				File nuevaCarpeta = new File(rutaNuevaCarpeta);

				if (nuevaCarpeta.mkdir()) {
					fallo = false;
					System.out.println("\nCarpeta '" + nombreCarpeta + "' creada con éxito.");
				}else {
					fallo = true;
					System.out.println("Error al crear la carpeta '" + nombreCarpeta + "'. La carpeta ya existe, el nombre escogido no es válido o el directorio actual está protegido contra escritura."); 
				}
				
			} while(fallo == true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	// Método: CreaFichero
	// Descripción: método que genera un fichero cuyo nombre y extensión elige el usuario.
	// Parámetros de entrada: objeto de tipo file (directorio) pasado por parámetro.
	// Parámetros de salida: no.
	
	public static void CreaFichero(File f){
		
		try {
			
			//Genero un objeto de tipo FileFilter que sirve para seleccionar, más adelante, las carpetas
			//	donde se puede crear un fichero de entre todos los elementos del directorio pasado por parámetro.
			FileFilter filtroCarpetas = new FileFilter() {
				@Override
				public boolean accept(File arch) {
					return arch.isDirectory();
				}
			};

			//Se seleccionan y muestran las carpetas del directorio donde se pueden generar ficheros.
			File[] listaCarpetas = f.listFiles(filtroCarpetas);
			if (listaCarpetas == null) {
				System.out.println("El directorio actual está protegido.");
			} else if (listaCarpetas.length == 0) {
				System.out.println("El directorio actual no contiene subcarpetas. Escribe 'estedirectorio' para crear el fichero en el directorio actual.");
			} else {
				System.out.println("\nSubdirectorios disponibles: ");
				for (File carpeta: listaCarpetas) {
					System.out.println("\t"+ carpeta.getName());
				}
			}

			Scanner teclado = new Scanner(System.in);

			boolean fallo = false;

			//El siguiente bucle do-while sirve para preguntar constantemente al usuario en caso de que cometa algún error al introducir datos (a través de la variable booleana 'fallo').
			do {
				System.out.print("\nEscribe el subdirectorio donde deseas crear el fichero o escribe 'estedirectorio' para crearlo en el directorio actual. Introduce 'salir' para cancelar: ");

				//Se genera la ruta de la carpeta escogida en función de la opción introducida por el usuario y se crea un obetjo de tipo File.
				String nombreCarpeta = teclado.next();
				
				if (nombreCarpeta.equals("salir") || nombreCarpeta.equals("SALIR")){
					break;
				}

				String ruta = f.toString();

				String rutaCarpeta;

				if (nombreCarpeta.equals("estedirectorio") || nombreCarpeta.equals("ESTEDIRECTORIO")) {
					rutaCarpeta = ruta;
				} else {
					rutaCarpeta = ruta + "\\" + nombreCarpeta;
				}

				File carpeta = new File(rutaCarpeta);
				
				//Se comprueba que el objeto de tipo File generado a partir de la ruta dada sea una carpeta existente en la que se pueda escribir.
				if (carpeta.exists() && carpeta.isDirectory() && carpeta.canWrite()) {

					System.out.print("Introduce el nombre del fichero y su extensión (por ejemplo, 'fichero.txt'): ");
					String nombreFichero = teclado.next();

					String rutaFichero = rutaCarpeta + "\\" + nombreFichero;

					File fichero = new File (rutaFichero);

					try {

						if (fichero.createNewFile()) {
							System.out.println("\n'" + nombreFichero + "' creado correctamente.");
							fallo = false;
						} else {
							System.out.println("\n'" + nombreFichero + "' ya existe o no se ha podido crear.");
							fallo = true;
						}

					} catch (IOException ioe) {
						System.out.println("\nLa carpeta seleccionada es privada. El archivo no se ha podido crear.");
					}

				} else {
					System.out.println("\nLa carpeta seleccionada no existe.");
					fallo = true;
				}
			} while(fallo == true);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	
	
	// Método: Elimina
		// Descripción: método que elimina un fichero o directorio escogido por el usuario.
		// Parámetros de entrada: objeto de tipo file (directorio) pasado por parámetro.
		// Parámetros de salida: no.
	
	public static void Elimina(File f) {
			
		try {
	
			File[] listaElementos = f.listFiles();
			
			//En primer lugar, se muestran los elementos del directorio.
			if (listaElementos == null || listaElementos.length == 0) {
				System.out.println("El directorio actual no contiene ficheros o directorios.");
			} else {
				System.out.println("\nElementos existentes: ");
				for (File item: listaElementos) {
					System.out.println("\t"+ item.getName());
				}
			}

			Scanner teclado = new Scanner(System.in);

			boolean fallo = false;

			//Bucle do-while que sirve para preguntar constantemente al usuario en caso de que cometa algún error al introducir datos (a través de la variable booleana 'fallo').
			do {
				System.out.print("\nEscribe el nombre del elemento que deseas eliminar. Si seleccionas un fichero, incluye también su extensión (por ejemplo, 'fichero.txt'). Introduce 'salir' para cancelar: ");

				String nombreElemento = teclado.next();
				
				if (nombreElemento.equals("salir") || nombreElemento.equals("SALIR")){
					break;
				}

				String ruta = f.toString();

				String rutaElemento = ruta + "\\" + nombreElemento;

				File elemento = new File(rutaElemento);
				
				//Se comprueba si el elemento escogido es fichero o carpeta.
				//En caso de que sea carpeta, se invoca el método auxiliar "BorrarDirectorio", que permite borrar carpetas con contenido (se explica más adelante).
				if (elemento.exists() && elemento.isFile() && elemento.canWrite()) {

					if (elemento.delete()) {
						System.out.println("\n'" + nombreElemento + "' eliminado correctamente.");
						fallo = false;
					} else {
						System.out.println("\n'" + nombreElemento + "' no se ha podido eliminar.");
						fallo = true;
					}

				} else if (elemento.exists() && elemento.isDirectory() && elemento.canWrite()) {

					BorrarDirectorio(elemento);

					if (elemento.delete()) {
						System.out.println("\n'" + nombreElemento + "' eliminado correctamente.");
						fallo = false;
					} else {
						System.out.println("\n'" + nombreElemento + "' no se ha podido eliminar.");
						fallo = true;
					}

				} else {
					System.out.println("\nEl fichero o la carpeta introducida no existe.");
					fallo = true;
				}

			} while(fallo == true);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
	
	// Método: Renombra
			// Descripción: método que renombra un fichero o directorio escogido por el usuario.
			// Parámetros de entrada: objeto de tipo file (directorio) pasado por parámetro.
			// Parámetros de salida: no.
	
	public static void Renombra(File f) {
		
		try {
			
			//Se muestran los elementos contenidos en el directorio actual.
			File[] listaFicherosCarpetas = f.listFiles();

			if (listaFicherosCarpetas == null || listaFicherosCarpetas.length == 0) {
				System.out.println("El directorio actual no contiene ficheros o carpetas.");
			} else {
				System.out.println("\nCarpetas y ficheros existentes: ");
				for (File elemento: listaFicherosCarpetas) {
					System.out.println("\t"+ elemento.getName());
				}
			}

			Scanner teclado = new Scanner(System.in);

			boolean fallo = false;

			//Bucle do-while que sirve para preguntar constantemente al usuario en caso de que cometa algún error al introducir datos (a través de la variable booleana 'fallo').
			do {
				System.out.print("\nEscribe el nombre del elemento que deseas renombrar. Si seleccionas un fichero, incluye también su extensión (por ejemplo, 'fichero.txt'). Introduce 'salir' para cancelar: ");

				String nombreElemento = teclado.next();
				
				if (nombreElemento.equals("salir") || nombreElemento.equals("SALIR")){
					break;
				}

				String ruta = f.toString();

				String rutaFichero = ruta + "\\" + nombreElemento;

				File elemento1 = new File(rutaFichero);
				
				//Se comprueba si el elemento escogido por el usuario existe y se puede modificar. En caso afirmativo, se pide el nombre nuevo al usuario y procede a renombrar.
				if (elemento1.exists() && elemento1.canWrite()) {

					System.out.print("\nIntroduce un nuevo nombre. Si renombras un fichero, incluye también su extensión (por ejemplo, 'fichero.txt'): ");
					String nombreNuevo = teclado.next();

					String rutaFicheroNuevo = ruta + "\\" + nombreNuevo;

					File elemento2 = new File(rutaFicheroNuevo);

					if (elemento1.renameTo(elemento2)) {
						System.out.println("\n'" + nombreElemento + "' ha sido renombrado correctamente como '" + nombreNuevo + "'.");
						fallo = false;
					} else {
						System.out.println("\n'" + nombreElemento + "' no se ha podido renombrar.");
						fallo = true;
					}

				} else {
					System.out.println("\nEl fichero o la carpeta introducida no existe.");
					fallo = true;
				}

			} while(fallo == true);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	
	
	
	// Método: BorrarDirectorio
				// Descripción: método auxiliar que recorre los elementos de una carpeta, eliminándolos.
				//				Si la carpeta contiene subcarpetas con contenido, el método se autoinvoca.
				//				Este método es llamado en el método "Elimina", para poder borrar carpetas con contenido.
				// Parámetros de entrada: objeto de tipo file (directorio) pasado por parámetro.
				// Parámetros de salida: no.
	
	public static void BorrarDirectorio (File directorio) {
		
		File[] fichero = directorio.listFiles();
		
		if (fichero == null) {
			System.out.println("\nNo se puede acceder a la carpeta (está protegida o es privada).");
		} else {
			for (File elemento: fichero) {

				if (elemento.isDirectory()) {
					BorrarDirectorio(elemento);
				} else {
					elemento.delete();
				}
			}
		}
	}
	
	
	
}
