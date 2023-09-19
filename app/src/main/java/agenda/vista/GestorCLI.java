/**
 * Clase de tipo vista. Responsable de la interfaz de usuario en modo comando.
 */
package agenda.vista;

import java.util.ArrayList;

import agenda.modelo.Tarea;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * 
 */
public class GestorCLI {
	private static Logger trazador = Logger.getLogger(GestorCLI.class.getName());
	private static Scanner teclado = new Scanner(System.in);
	public byte menuPrincipal() {
		trazador.info("menuPrincipal");
		byte respuesta=0;
	//	Scanner teclado = new Scanner(System.in);
		
		

        while(respuesta<1 || respuesta>4) {
		System.out.println("MENÚ PRINCIPAL\n"
				+ "1. Nueva tarea\n"
				+ "2. Listar todas las tareas\n"
				+ "3. Filtrar tareas\n"
				+ "4. Salir\n"
				+ "Por favor introduzca la opción elegida");
		
		respuesta = Byte.parseByte(teclado.nextLine());
	
        }
        //teclado.nextLine();
    
		
		/**
		 * Listar todas las tareas Listar las tareas no iniciadas Listar las tareas no
		 * terminadas Añadir una tarea nueva Modificar una tarea existente Eliminar una
		 * tarea
		 */
	//	teclado.close();
		return respuesta;
	}
	public String pedirFichero() {
		trazador.info("pedirFichero");
		String fichero;
	//	Scanner teclado = new Scanner(System.in);
		teclado.useDelimiter("\n");
		

       
		System.out.println("Por favor introduzca el nombre del fichero correspondiente a la agenda\n");				
		fichero = teclado.nextLine();
	   
		
		
		//teclado.close();
		return fichero;
	}

	public byte menuTarea(Tarea tarea) {
		byte respuesta = 0;

		return respuesta;
	}

	public void modificarTarea(Tarea tarea) {

	}

	public void mostrarListado(ArrayList<Tarea> tareas) {
		String listado = "Las " + tareas.size() + " tareas son:\n";
		for (int i = 0; i < tareas.size(); i++) {
			listado += tareas.get(i).toString() + "\n";
		}
		System.out.println(listado);
	}

	public void nuevaTarea(Tarea tarea) {

	}

}
