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
		String rutaAgenda=null;
		byte opcionMenu=0;
		Tarea tarea=null;
		
		/**
		 * TareaDAO permite manejar las tareas
		 */
		
		GestorCLI cli = new GestorCLI();
		rutaAgenda=cli.pedirFichero();
		trazador.info("fichero = "+rutaAgenda);
		TareaDAO tareas = new TareaDAO(rutaAgenda);
		//La opción 4 es la terminar de ejecutar el programa
		while(opcionMenu!=4) {
			opcionMenu=cli.menuPrincipal();
			trazador.info("respuesta = "+opcionMenu);
			switch(opcionMenu) {
			case 1:
				cli.nuevaTarea(tarea=new Tarea());
				tareas.guarda(tarea);
				break;
			case 2:
				byte res;
				for (int i = 0; i < tareas.listarTodas().size(); i++) {		
					res=cli.menuTarea(tareas.listarTodas().get(i));
					switch (res){				
					case 1:
						tareas.borra(tareas.listarTodas().get(i));
						break;
					case 2:
						tareas.listarTodas().get(i).comenzar();
						tareas.guarda();
						break;
					case 3:
						tareas.listarTodas().get(i).finalizar();
						tareas.guarda();
						break;
					case 4:
						tareas.listarTodas().get(i).cambiaUrgente();
						tareas.guarda();
						break;						
					}					
				}
				break;
			case 3:
				break;			
			}
		}
	}
}
