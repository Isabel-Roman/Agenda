/**
 * Clase para la persistencia de objetos Tarea en ficheros
 */
package agenda.modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.Iterator;

public class TareaDAO {

	private FileOutputStream fos = null;
	private FileInputStream fis = null;
	private ObjectOutputStream salida = null;
	private ObjectInputStream entrada = null;
	private ArrayList<Tarea> tareas;
	private String path;

	public TareaDAO(String path) {
		super();
		this.path = path;
		tareas = new ArrayList<Tarea>();
		leeTodas();
	}

	private void save() {
		try {
			// Se crea el fichero
			fos = new FileOutputStream(path);
			salida = new ObjectOutputStream(fos);
			salida.writeObject(tareas);
			salida.close();
			fos.close();

		} catch (Exception e) {
			System.out.println("Error al guardar las tareas " + e.getMessage());
			e.printStackTrace();
		}
	}

	public ArrayList<Tarea> listarTodas() {
		return tareas;
	}

	private ArrayList<Tarea> leeTodas() {

		try {
			// Para poder leer utilizaremos un FileInputStream pasandole
			// como referencia el archivo de tipo File.
			fis = new FileInputStream(path);
			entrada = new ObjectInputStream(fis);

			// En una variable objeto de tipo Persona almacenaremos
			// el objeto leido de tipo Object convertido en un objeto
			// de tipo persona
			tareas = (ArrayList<Tarea>) entrada.readObject();

			entrada.close();
			fis.close();
		} catch (Exception e) {
			System.out.println("Error al leer las tareas " + e.getMessage());
		}
		return tareas;
	}

	public void guarda(Tarea tarea) {
		tareas.add(tarea);
		this.save();
	}

	public void borra(Tarea tarea) {
		tareas.indexOf(tarea);
		tareas.remove(tarea);
		this.save();
	}

	/** Devuelve una tarea nueva, sin datos **/
	public Tarea nueva() {
		return new Tarea();
	}

	@Override
	public String toString() {
		String listado = "Las " + tareas.size() + " tareas de tu agenda son:\n";
		for (int i = 0; i < tareas.size(); i++) {
			listado += tareas.get(i).toString() + "\n";
		}
		return listado;
	}
}
