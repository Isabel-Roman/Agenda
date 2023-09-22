/**
 * Clase para la persistencia de objetos Tarea en ficheros
 */
package agenda.modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Esta clase se encarga de las capacidades de persistencia de las tareas, en
 * este caso en un fichero binario Permite guardar y recuperar las tareas en un
 * fichero, para que la información sobreviva las ejecuciones del programa
 */
public class TareaDAO {

	/**
	 * la variable tareas guardará una referencia aun objeto de tipo ArrayList
	 * Este ArrayList será la lista de tareas manejadas en el programa
	 * En este momento no se crea la lista, sólo se reserva en memoria espacio para una referenia
	 * Se iguala a null para asegurar que ese referencia, en este momento, es nula
	 */
	private ArrayList<Tarea> tareas=null;
	/**
	 * La variable path es una referencia a un objeto de tipo String que almacenará la
	 * Ruta del fichero en el que se persistirán las tareas (Agenda)
	 * En este momento aún no se ha creado el objeto, por tanto la referencia es nula
	 */
	private String path=null;
    /**
     * Estas variables son referencias a distintos objetos que se necesitan para manejar el fichero binario
     * que persistirá los datos de la agenda
     */
	private FileOutputStream fos = null;
	private FileInputStream fis = null;
	private ObjectOutputStream salida = null;
	private ObjectInputStream entrada = null;

	/**
	 * Los objetos de tipo Logger sirven para enviar mensajes de trazado de código
	 * que ayudan al programador a tener información detallada de cómo se está ejecutando el programa
	 * Sirven principalmente para la depuración del código
	 */
	private static Logger trazador = Logger.getLogger(TareaDAO.class.getName());

	/**
	 * Para crear un objeto de la clase TareaDAO es necesario indicar el fichero en el que
	 * se quiere persistir la información. Si el fichero existe y tiene tareas
	 * almacenas estas se guardan en la lista de tareas Si el fichero no existe se
	 * comienza con una lista de tareas vacía
	 * 
	 * @param path
	 */
	public TareaDAO(String path) {
		super();
		this.path = path;

		leeTodas();
	}

	/**
	 * Este método persiste toda la lista en el fichero (Agenda)
	 */
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

	/**
	 * Devuelve la lista con todas las tareas
	 * 
	 * @return la lista con todas las tareas
	 */
	public ArrayList<Tarea> listarTodas() {
		return tareas;
	}

	/**
	 * Lee todas las tareas desde el fichero, si el fichero no existe o está vacío
	 * devuelve la lista vacía
	 * 
	 * @return la lista de tareas que se encuentran en el fichero
	 */
	private ArrayList<Tarea> leeTodas() {
		try {
			// Para poder leer utilizaremos un FileInputStream pasandole
			// como referencia el archivo de tipo File.
			trazador.info("leeTodas las de " + path);
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
			trazador.info(path + " no existe, se crea una agenda nueva");
			tareas = new ArrayList<Tarea>();
		} catch (EOFException e) {
			trazador.info(path + " está vacío, se crea una agenda nueva");
			tareas = new ArrayList<Tarea>();
		} catch (IOException | ClassNotFoundException e) {
			trazador.info("Error al leer el archivo: " + e.getMessage());
		}
		return tareas;
	}
	

	/**
	 * Añade una tarea a la lista y las persiste todas en el fichero
	 * 
	 * @param tarea El argumento es la tarea que se quiere añadir a la lista
	 */
	public void guarda(Tarea tarea) {
		tareas.add(tarea);
		this.guarda();
	}

	/**
	 * Elimina una tarea de la lista y las persiste todas en el fichero
	 * 
	 * @param tarea El argumento es la tarea que se quiere eliminar de la lista
	 */
	public void borra(Tarea tarea) {
		tareas.indexOf(tarea);
		tareas.remove(tarea);
		this.guarda();
	}

	/** Devuelve una tarea nueva, sin datos, no la incorpora a la lista **/
	public Tarea nueva() {
		return new Tarea();
	}
	/**
	 * Filtra la lista de tareas pasada, quedándose solamente con las retrasadas
	 * @param tareas
	 * @return una nueva lista que sólo contiene las retrasadas
	 */
	public ArrayList<Tarea> filtraRetrasadas(ArrayList<Tarea> tareas) {
		ArrayList<Tarea> filtrada=new ArrayList<Tarea>();
		for(int i=0;i<tareas.size();i++) {
			if(tareas.get(i).isOverdued())
				filtrada.add(tareas.get(i));
		}
		return filtrada;
	}
	/**
	 * Filtra la lista de tareas pasada, quedándose solamente con las urgentes
	 * @param tareas
	 * @return una nueva lista que sólo contiene las urgentes
	 */
	public ArrayList<Tarea> filtraUrgentes(ArrayList<Tarea> tareas) {
		trazador.info("Filtrado urgente");
		ArrayList<Tarea> filtrada=new ArrayList<Tarea>();
		for(int i=0;i<tareas.size();i++) {
			if(tareas.get(i).isUrgente())
				filtrada.add(tareas.get(i));
		}
		return filtrada;
	}
	
	/**
	 * Filtra la lista de tareas pasada, quedándose solamente con las no terminadas
	 * @param tareas
	 * @return una nueva lista que sólo contiene las no terminadas
	 * 	 
	 * */
	public ArrayList<Tarea> filtraNoTerminadas(ArrayList<Tarea> tareas) {
		trazador.info("Filtrado no terminadas");
		ArrayList<Tarea> filtrada=new ArrayList<Tarea>();
		for(int i=0;i<tareas.size();i++) {
			if(!tareas.get(i).isFinalizada())
				filtrada.add(tareas.get(i));
		}
		return filtrada;
	}
	
	/**
	 * Filtra la lista de tareas pasada, quedándose solamente con las terminadas
	 * @param tareas
	 * @return una nueva lista que sólo contiene las terminadas
	 * 	 
	 * */
	public ArrayList<Tarea> filtraTerminadas(ArrayList<Tarea> tareas) {
		trazador.info("Filtrado terminadas");
		ArrayList<Tarea> filtrada=new ArrayList<Tarea>();
		for(int i=0;i<tareas.size();i++) {
			if(tareas.get(i).isFinalizada())
				filtrada.add(tareas.get(i));
		}
		return filtrada;
	}
	
	/**
	 * Filtra la lista de tareas pasada, quedándose solamente con las comenzadas
	 * @param tareas
	 * @return una nueva lista que sólo contiene las comenzadas
	 * 	 
	 * */
	public ArrayList<Tarea> filtraComenzadas(ArrayList<Tarea> tareas) {
		trazador.info("Filtrado comenzadas");
		ArrayList<Tarea> filtrada=new ArrayList<Tarea>();
		for(int i=0;i<tareas.size();i++) {
			if(tareas.get(i).isComenzada())
				filtrada.add(tareas.get(i));
		}
		return filtrada;
	}
	
	
	/**
	 * Filtra la lista de tareas pasada, quedándose solamente con las no comenzadas
	 * @param tareas
	 * @return una nueva lista que sólo contiene las no comenzadas
	 * 	 
	 * */
	public ArrayList<Tarea> filtraNoComenzadas(ArrayList<Tarea> tareas) {
		trazador.info("Filtrado no comenzadas");
		ArrayList<Tarea> filtrada=new ArrayList<Tarea>();
		for(int i=0;i<tareas.size();i++) {
			if(!tareas.get(i).isComenzada())
				filtrada.add(tareas.get(i));
		}
		return filtrada;
	}
	
	
	/**
	 * Formato que queremos que tenga la salida si imprimimos este objeto DAO por
	 * pantalla En este caso un mensaje con el número de tareas y el listado
	 */
	@Override
	public String toString() {
		String listado = "Las " + tareas.size() + " tareas de tu agenda son:\n";
		for (int i = 0; i < tareas.size(); i++) {
			listado += tareas.get(i).toString() + "\n";
		}
		return listado;
	}
}
