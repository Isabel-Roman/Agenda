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
		teclado.useDelimiter("\n");       
		System.out.println("Por favor introduzca el nombre del fichero correspondiente a la agenda\n");				
		fichero = teclado.nextLine();	   
		return fichero;
	}

	public byte menuTarea(Tarea tarea) {
		byte respuesta = 0;
		System.out.println(tarea);
		System.out.println("MENU TAREA:\n"
				+"1. Eliminar\n"
				+"2. Marcar como comenzada\n"
				+"3. Marcar como terminada\n"
				+"4. Cambiar estado urgente\n"
				+"5. Cambiar fecha de vencimiento\n"
				+"6. Continuar\n"
				+"Por favor elija la opción elegida \n");
		respuesta = Byte.parseByte(teclado.nextLine());
		return respuesta;
	}

	
	public void mostrarListado(ArrayList<Tarea> tareas) {
		System.out.println("Las " + tareas.size() + " tareas son:\n");
		for (int i = 0; i < tareas.size(); i++) {		
			Tarea tarea=tareas.get(i);
			byte respuesta=menuTarea(tarea);			
			switch(respuesta) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
				     break;		   
			}
		}		
	}

	public void nuevaTarea(Tarea tarea) {
         System.out.println("Introduzca los datos de la nueva tarea\n");
         System.out.println("Nombre de la tarea:\n");
         tarea.setNombre(teclado.nextLine());
         System.out.println("Detalles de la tarea:\n");
         tarea.setDetalles(teclado.nextLine());
         System.out.println("¿Es la tarea urgente? (S/N):\n");
         if(leeBoolean())
        	 tarea.setUrgente();
         System.out.println("¿Tiene una fecha de vencimiento esta tarea? (S/N):\n");
         if(leeBoolean()) {
        	 System.out.println("Introduzca la fecha de vencimiento en formato "+tarea.getFormato()+":\n");
        	 tarea.setVencimiento(teclado.nextLine());
         }
	}
	private boolean leeBoolean() {
		boolean respuesta=false;
		String linea=teclado.nextLine().trim();
		if((linea.equals("S")) || (linea.equals("s"))) {
			respuesta=true;
		}
		trazador.info("La respuesta booleana es :"+respuesta);
		return respuesta;
	}

}
