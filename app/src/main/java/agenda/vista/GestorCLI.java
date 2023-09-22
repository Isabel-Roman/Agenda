/**
 * Clase de tipo vista. Responsable de la interfaz de usuario en modo comando.
 */
package agenda.vista;

import agenda.modelo.Tarea;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Clase encargada de la gestión de la interfaz con el usuario a través de la
 * interfaz de comandos (CLI)
 */
public class GestorCLI {
	private static Logger trazador = Logger.getLogger(GestorCLI.class.getName());
	private Scanner teclado = new Scanner(System.in);

	/**
	 * Este método presenta por pantalla (salida estándar) un mensaje para solicitar
	 * el nombre del fichero con las tareas, es decir la agenda Lee el nombre
	 * introducido y lo devuelve
	 * 
	 * @return Devuelve un String con el nombre del fichero que contiene las tareas
	 */

	public String pedirFichero() {
		trazador.info("pedirFichero");
		String fichero;
		teclado.useDelimiter("\n");
		System.out.println("Por favor introduzca el nombre del fichero correspondiente a la agenda\n");
		fichero = teclado.nextLine();
		return fichero;
	}

	/**
	 * Este método muestra por pantalla (interfaz de salida estándar) el menú
	 * principal del programa y recoge la opción elegida por el usuario
	 * 
	 * @return una opción válida elegida por el usuario, un valor entre 1 y 4
	 */
	public byte menuPrincipal() {
		trazador.info("menuPrincipal");
		byte respuesta = 0;
		// Mientras que la opción introducida no sea válida se presenta el menú y se
		// recoge la opción
		while (respuesta < 1 || respuesta > 4) {
			System.out.println("MENÚ PRINCIPAL\n" + "1. Nueva tarea\n" + "2. Listar todas las tareas\n"
					+ "3. Filtrar tareas\n" + "4. Salir\n" + "Por favor introduzca la opción elegida");
			try {
				respuesta = Byte.parseByte(teclado.nextLine());
			} catch (NumberFormatException e) {
				// Si no se ha introducido un número al convertir a byte ocurrirá una excepción
				// Se da el valor 0 a respuesta para que se vuelva a mostrar el menú y repetir
				// la operación
				respuesta = 0;
			}
		}
		if (respuesta == 4)
			System.out.println("Muchas gracias por haber utilizado el gestor de tareas\n");
		return respuesta;
	}

	/**
	 * Este método muestra por pantalla el menú correspondiente a las acciones que
	 * se pueden ejecutar en una tarea Lee el valor introducido por el usuario y
	 * devuelve un valor válido, entre 1 y 6
	 * 
	 * @param tarea el argumento de entrada es la tarea sobre la que se puede
	 *              realizar alguna acción
	 * @return devuelve una opción válida, introducida por el usuario
	 */
	public byte menuTarea(Tarea tarea) {
		byte respuesta = 0;
		/**
		 * Muestra el menú hasta que se introduzca una opción válida
		 */
		while (respuesta < 1 || respuesta > 6) {
			System.out.println(tarea);

			System.out.println("MENU TAREA:\n" + "1. Eliminar\n" + "2. Marcar como comenzada\n"
					+ "3. Marcar como terminada\n" + "4. Cambiar estado urgente\n" + "5. Cambiar fecha de vencimiento\n"
					+ "6. Continuar\n" + "Por favor elija la opción elegida \n");
			try {
				respuesta = Byte.parseByte(teclado.nextLine());
			} catch (NumberFormatException e) {
				respuesta = 0;
			}
		}
		return respuesta;
	}

	/**
	 * Este método muestra un mensaje pidiendo la fecha de vencimiento y la lee,
	 * asegurándose de que el formato es correcto
	 * 
	 * @param tarea el argumento de entrada es la tarea a la cuál se le quiere
	 *              cambiar la fecha de vencimiento
	 */
	public void cambioVencimiento(Tarea tarea) {
		boolean fechaOK = false;
		String fecha=null;
		while (!fechaOK) {
			System.out.println("Introduzca la fecha de vencimiento en formato " + tarea.getFormato() + ":\n");
			fecha = teclado.nextLine();
		//	fechaOK=fechaValida(fecha, tarea);
			fechaOK=tarea.fechaValida(fecha);
		}
		tarea.setVencimiento(fecha);
	}
    /**
     * Este método solicita al usuario los datos de una tarea nueva y los incluye en la misma
     * @param tarea recibe como argumento la tarea que se está creando
     */
	public void nuevaTarea(Tarea tarea) {
		System.out.println("Introduzca los datos de la nueva tarea\n");
		System.out.println("Nombre de la tarea:\n");
		tarea.setNombre(teclado.nextLine());
		System.out.println("Detalles de la tarea:\n");
		tarea.setDetalles(teclado.nextLine());
		System.out.println("¿Es la tarea urgente? (S/N):\n");
		if (leeBoolean())
			tarea.setUrgente();
		System.out.println("¿Tiene una fecha de vencimiento esta tarea? (S/N):\n");
		if (leeBoolean())
			cambioVencimiento(tarea);
	}
    /**
     * Este método se utiliza para leer una entrada de tipo boolean
     * @return devuelve true si se introdujo s o S, y false en cualquier otro caso
     */
	private boolean leeBoolean() {
		boolean respuesta = false;
		String linea = teclado.nextLine().trim();
		if ((linea.equals("S")) || (linea.equals("s"))) {
			respuesta = true;
		}
		trazador.info("La respuesta booleana es :" + respuesta);
		return respuesta;
	}
	public byte[] menuFiltrado() {
		byte filtro[]= new byte[6];
		byte respuesta=0;
		byte indice=0;
		while(respuesta!=7 && indice<6) {
		System.out.println("Elija la opción que desea añadir al filtro\n"
				+ "1. Tareas urgentes\n"
				+ "2. Tareas comenzadas\n"
				+ "3. Tareas no comenzadas\n"
				+ "4. Tareas retrasadas\n"
				+ "5. Tareas no terminadas\n"
				+ "6. Tareas terminadas\n"
				+ "7. Fin de filtro\n");
		try {
			respuesta =  Byte.parseByte(teclado.nextLine());
			/**Si se ha introducido un número válido lo añado al filtro**/
			if(respuesta>0 && respuesta<7) {
				filtro[indice] = respuesta;
				indice++;
			}
		} catch (NumberFormatException e) {
			// Si no se ha introducido un número al convertir a byte ocurrirá una excepción
			// no se aumenta el índice, para que el siguiente número se meta en la posición adecuada
		}
		}
		if (indice==6)
				System.out.println("Ha establecido todos los filtros posibles\n");
		return filtro;
	}
	public boolean validaFiltro(String filtro) {
		boolean valido=true;
		/**
		 * El filtro tiene que ser una cadena de números entre 1 y 6 separados por comas
		 */
		String regex="^([1-6][,])*[1-6]$";
		valido= filtro.matches(regex);
		return valido;
	}

}
