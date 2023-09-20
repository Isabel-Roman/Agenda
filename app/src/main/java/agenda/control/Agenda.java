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
	static GestorCLI cli;
	static TareaDAO tareaDao;
	public static void main(String[] args) {
		String rutaAgenda=null;
		byte opcionMenu=0;
		Tarea tarea=null;
		
		/**
		 * TareaDAO permite manejar las tareas
		 */
		
		cli = new GestorCLI();
		rutaAgenda=cli.pedirFichero();
		trazador.info("fichero = "+rutaAgenda);
		tareaDao = new TareaDAO(rutaAgenda);
		//La opción 4 es la terminar de ejecutar el programa
		while(opcionMenu!=4) {
			opcionMenu=cli.menuPrincipal();
			trazador.info("respuesta = "+opcionMenu);
			switch(opcionMenu) {
			case 1:
				cli.nuevaTarea(tarea=new Tarea());
				tareaDao.guarda(tarea);
				break;
			case 2:
				listaTareas(tareaDao.listarTodas());
				break;
			case 3:
				break;			
			}
		}
	}
	static void listaTareas(ArrayList<Tarea> tareas) {
		byte res;
		for (int i = 0; i < tareas.size(); i++) {		
			res=cli.menuTarea(tareas.get(i));
			switch (res){				
			case 1:
				tareaDao.borra(tareas.get(i));
				break;
			case 2:
				tareas.get(i).comenzar();
				tareaDao.guarda();
				break;
			case 3:
				tareas.get(i).finalizar();
				tareaDao.guarda();
				break;
			case 4:
				tareas.get(i).cambiaUrgente();
				tareaDao.guarda();
				break;						
			}					
		}
		
	}
}
