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
	/**
	 * GestorCLI ofrece capacidades relacionadas con la interfaz de usuario facilita
	 * el manejo de datos a través de la entrada/salida estándar, es decir, teclado
	 * y consola (pantalla)
	 */
	static GestorCLI cli;
	/**
	 * TareaDAO ofrece capacidades relacionadas con la persistencia de las tareas En
	 * la versión actual la persistencia es en fichero
	 */
	static TareaDAO tareaDao;

	public static void main(String[] args) {
		String rutaAgenda = null;
		byte opcionMenu = 0;
		Tarea tarea = null;

		cli = new GestorCLI();
		rutaAgenda = cli.pedirFichero();
		trazador.info("fichero = " + rutaAgenda);
		tareaDao = new TareaDAO(rutaAgenda);
		// Se repiten las siguientes acciones mientras el usuario no introduzca la
		// opción 4, que es salir del programa
		while (opcionMenu != 4) {
			/**
			 * opcionMenu contendrá la opción elegida por el usuario en el menú principal 1.
			 * Nueva tarea 2. Listar todas las tareas 3. Filtrar tareas 4. Salir
			 */
			opcionMenu = cli.menuPrincipal();
			trazador.info("respuesta = " + opcionMenu);
			switch (opcionMenu) {
			case 1:
				cli.nuevaTarea(tarea = new Tarea());
				tareaDao.guarda(tarea);
				break;
			case 2:
				listaTareas(tareaDao.listarTodas());
				break;
			case 3:
				filtrarTareas(tareaDao.listarTodas());
				
				
				break;
			}
		}
	}

	static private void listaTareas(ArrayList<Tarea> tareas) {
		byte opcionMenu;
		trazador.info("Se mostrarán " + tareas.size() + " tareas");
		for (int i = 0; i < tareas.size(); i++) {
			opcionMenu = 0;
			/**
			 * opcionMenu contendrá la opción elegida por el usuario en el menú de tarea 1.
			 * Eliminar 2. Marcar como comenzada 3. Marcar como terminada 4. Cambiar estado
			 * urgente 5. Cambiar fecha de vencimiento 6. Continuar a siguiente tarea
			 */
			while (opcionMenu != 6) {
				opcionMenu = cli.menuTarea(tareas.get(i));

				switch (opcionMenu) {
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
				case 5:
					cli.cambioVencimiento(tareas.get(i));
					tareaDao.guarda();
					break;
				}
			}
		}

	}
	static private void filtrarTareas(ArrayList<Tarea> tareas) {
		byte filtro[]=cli.menuFiltrado();
		ArrayList<Tarea> filtradas=new ArrayList<>(tareas);
	
		for(int i=0;i<filtro.length;i++) {
			/**
			 * Análisis de las opciones de filtrado
			 * "1. Tareas urgentes\n"
			 * "2. Tareas comenzadas\n"
			 * "3. Tareas no comenzadas\n"
			 * "4. Tareas retrasadas\n"
			 * "5. Tareas no terminadas\n"
			 * "6. Tareas terminadas\n"
			 */
			switch(filtro[i]) {
			case 1: //urgentes
				filtradas=tareaDao.filtraUrgentes(filtradas);
				break;
			case 2: //comenzadas
				filtradas=tareaDao.filtraComenzadas(filtradas);
				break;
			case 3: //no comenzadas
				filtradas=tareaDao.filtraNoComenzadas(filtradas);
				break;
			case 4: //retrasadas
				filtradas=tareaDao.filtraRetrasadas(filtradas);
				break;
			case 5: //no terminadas
				filtradas=tareaDao.filtraNoTerminadas(filtradas);
				break;
			case 6: //terminadas
				filtradas=tareaDao.filtraTerminadas(filtradas);
				break;
			}			
		}
		listaTareas(filtradas);
		
	}
}
