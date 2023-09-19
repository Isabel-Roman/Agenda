/**
 * 
 * Clase controladora de la agenda de tareas
 * Incluye el método main, inicia y controla el flujo del programa
 **/
package agenda.control;

import java.util.ArrayList;

import agenda.modelo.Tarea;
import agenda.modelo.TareaDAO;
import agenda.vista.GestorCLI;
import java.util.logging.Logger;
public class Agenda {
	private static Logger trazador = Logger.getLogger(Agenda.class.getName());
	public static void main(String[] args) {
		byte opcionMenu=0;
		/**
		 * TareaDAO permite manejar las tareas
		 */
		
		GestorCLI cli = new GestorCLI();
		String fichero=cli.pedirFichero();
		trazador.info("fichero = "+fichero);
		TareaDAO tareas = new TareaDAO(fichero);
		//La opción 4 es la terminar de ejecutar el programa
		while(opcionMenu!=4) {
			opcionMenu=cli.menuPrincipal();
			trazador.info("respuesta = "+opcionMenu);
		}
	}
}
