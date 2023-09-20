/**
 * Clase para la persistencia de objetos Tarea en ficheros
 */
package agenda.modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import agenda.control.Agenda;

public class TareaDAO {

	private FileOutputStream fos = null;
	private FileInputStream fis = null;
	private ObjectOutputStream salida = null;
	private ObjectInputStream entrada = null;
	private ArrayList<Tarea> tareas;
	private String path;
	private static Logger trazador = Logger.getLogger(TareaDAO.class.getName());

	public TareaDAO(String path) {
		super();
		this.path = path;
		
		leeTodas();
	}

	public void guarda() {
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
			trazador.info("leeTodas las de "+path);
			fis = new FileInputStream(path);
			
			trazador.info("Creado el FileInputStream");
			
			entrada = new ObjectInputStream(fis);
            trazador.info("Creado el ObjectInputStream");
			// En una variable objeto de tipo Persona almacenaremos
			// el objeto leido de tipo Object convertido en un objeto
			// de tipo persona
			tareas = (ArrayList<Tarea>) entrada.readObject();
            trazador.info("Leido objeto");
			entrada.close();
			trazador.info("Cerrado el input stream");
			fis.close();
		 } catch (FileNotFoundException e) {
			    trazador.info(path+" no existe, se crea una agenda nueva");
	            tareas=new ArrayList<Tarea>();
	        } catch (EOFException e) {
	        	trazador.info(path+" está vacío, se crea una agenda nueva");
	            tareas=new ArrayList<Tarea>();
	        } catch (IOException | ClassNotFoundException e) {
	            System.err.println("Error al leer el archivo: " + e.getMessage());
	        }			
		return tareas;
	}

	public void guarda(Tarea tarea) {
		tareas.add(tarea);
		this.guarda();
	}

	public void borra(Tarea tarea) {
		tareas.indexOf(tarea);
		tareas.remove(tarea);
		this.guarda();
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
