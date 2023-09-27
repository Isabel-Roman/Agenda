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
	static {
	      // must set before the Logger
	      // loads logging.properties from the classpath
	      String path = Agenda.class
	            .getClassLoader().getResource("logging.properties").getFile();
	      System.setProperty("java.util.logging.config.file", path);

	  }
	private static Logger trazador = Logger.getLogger(Agenda.class.getName());
	/**
	 * GestorCLI ofrece capacidades relacionadas con la interfaz de usuario facilita
	 * el manejo de datos a través de la entrada/salida estándar, es decir, teclado
	 * y consola (pantalla)
	 * En esta línea se declara una variable llamada cli, que guardará una referencia a un objeto de tipo GestorCLI
	 * Aún no se ha creado el objeto, se reserva memoria para la referencia (y nos aseguramos que no hay nada en ese espacio reservado asignando null)
	 */
	static GestorCLI cli=null;
	/**
	 * TareaDAO ofrece capacidades relacionadas con la persistencia de las tareas En
	 * la versión actual la persistencia es en fichero
	 * Declaro la variable tareaDao, que guardará una referencia a un objeto de tipo TareaDAO
	 * Aún no creo el objeto, sólo reservo memoria para la referencia, y me aseguro que ese espacio está limpio, igualando a null
	 */
	static TareaDAO tareaDao=null;
    /**
     * Algoritmo principal del programa
     * @param args
     */
	public static void main(String[] args) {
		/**
		 * Variable de tipo String (cadena de caracteres) para guardar 
		 * la ruta del fichero con las tareas de la agenda, localización en el sistema de ficheros del equipo
		 */
		String rutaAgenda = null;
		/**
		 * Variable de tipo byte para guardar la opción introducida por el usuario
		 */
		byte opcionMenu = 0;
		
        /**
         * Crea un objeto de tipo GestorCLI y guarda la referencia en la variable cli
         */
		cli = new GestorCLI();
		rutaAgenda = cli.pedirFichero();
		trazador.info("fichero = " + rutaAgenda);
		/**
		 * Crea un objeto de tipo TareaDAO y guarda la referencia en la variable tareaDao
		 */
		tareaDao = new TareaDAO(rutaAgenda);
		/**
		 * TODO: iniciaTareasCuro se ejecutará sólo cuando la agenda esté vacía (normalmente en
		 * la primera ejecución del programa), y rellenará la agenda con las tareas del curso
		 */
		if (tareaDao.listarTodas().size()==0) {
			iniciaTareasCurso();
		}
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
				/**
				 * La variable tarea guardará una referencia a un objeto de la clase Tarea 
				 * En este momento esta referencia es nula
				 */
				Tarea tarea = null;
				/**
				 * nuevaTarea pide al usuario los datos de una tarea y los guarda en la tarea que se pasa como argumento, 
				 * en este caso se crea una tarea en este mismo instante, y la referencia a este objeto se guarda
				 * en la variable tarea
				 */
				cli.nuevaTarea(tarea = new Tarea());
				/**
				 * guarda almacena la tarea en la lista de tareas y en el fichero que contiene las tareas de la agenda
				 */
				tareaDao.guarda(tarea);
				break;
			case 2:
				/**
				 * listaTareas codifica un algoritmo para mostrar una lista de tareas
				 */
				listaTareas(tareaDao.listarTodas());
				break;
			case 3:
				/**
				 * filtrarTareas codifica un algoritmo para filtrar un conjunto de tareas y mostrarlas por pantalla
				 */
				filtrarTareas(tareaDao.listarTodas());
				break;
			}
		}
	}

	/**
	 * Muestra una a una las tareas de la lista recibida como argumento Para cada
	 * una de ellas usa el método menuTarea de GestorCLI para mostrar el menú de
	 * opciones disponibles para modificar la tarea
	 * 
	 * @param tareas
	 */
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

	/**
	 * Procedimiento que se ejecutará al elegir la opción de filtrar tareas (opción
	 * 3) Se mostrará un menú con las opciones de filtrado y se ejecutarán los
	 * filtros seleccionados por el usuario
	 * 
	 * @param tareas
	 */
	static private void filtrarTareas(ArrayList<Tarea> tareas) {
		/**
		 * El procedimiento menuFiltrado de GestorCLI devuelve un array con las opciones
		 * de filtrado elegidas por el usuario
		 */
		byte filtro[] = cli.menuFiltrado();
		/*
		 * Copia la lista de tareas en una nueva, llamada filtradas la lista filtradas
		 * se irá modificando al pasar cada filtro, El orden de aplicación de los
		 * filtros depende del orden en el que se introdujeron en el menú El primer
		 * filtro siempre será sobre una copia de la lista original Los posteriores
		 * sobre el resultado del filtrado anterior.
		 */
		ArrayList<Tarea> filtradas = new ArrayList<>(tareas);
		/**
		 * Recorre el array con las opciones de filtrado elegidas por el usuario y
		 * ejecuta cada una de ellos con los métodos de filtrado que proporciona el
		 * objeto de la clase TareaDAO
		 */
		for (int i = 0; i < filtro.length; i++) {
			/**
			 * Análisis de las opciones de filtrado "1. Tareas urgentes\n" "2. Tareas
			 * comenzadas\n" "3. Tareas no comenzadas\n" "4. Tareas retrasadas\n" "5. Tareas
			 * no terminadas\n" "6. Tareas terminadas\n"
			 */
			switch (filtro[i]) {
			case 1: // urgentes
				filtradas = tareaDao.filtraUrgentes(filtradas);
				break;
			case 2: // comenzadas
				filtradas = tareaDao.filtraComenzadas(filtradas);
				break;
			case 3: // no comenzadas
				filtradas = tareaDao.filtraNoComenzadas(filtradas);
				break;
			case 4: // retrasadas
				filtradas = tareaDao.filtraRetrasadas(filtradas);
				break;
			case 5: // no terminadas
				filtradas = tareaDao.filtraNoTerminadas(filtradas);
				break;
			case 6: // terminadas
				filtradas = tareaDao.filtraTerminadas(filtradas);
				break;
			}
		}
		/**
		 * Muestra una a una las tareas que han pasado el filtro seleccionado
		 */
		listaTareas(filtradas);
	}
	
	/**Este método introducirá en la agenda las primeras tareas del curso*/
	static void iniciaTareasCurso() {
		trazador.info("No se introducen tareas por defecto");
	}
}
