/**
 * Clase de tipo vista. Responsable de la interfaz de usuario en modo comando.
 */
package agenda.vista;

import java.util.ArrayList;

import agenda.modelo.Tarea;

/**
 * 
 */
public class GestorCLI {
	public byte menuPrincipal() {
		byte respuesta = 0;
		/**
		 * Listar todas las tareas Listar las tareas no iniciadas Listar las tareas no
		 * terminadas Añadir una tarea nueva Modificar una tarea existente Eliminar una
		 * tarea
		 */
		return respuesta;
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
