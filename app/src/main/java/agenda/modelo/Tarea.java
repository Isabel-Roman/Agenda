/**
 * Clase de tipo Entidad (estructura de información). Almacena los datos de una tarea y proporciona algunas capacidades básicas para la gestión de sus campos
 */
package agenda.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Clase de tipo entidad. Los objetos de esta clase almacenan la información de una tarea determinada
 * Como queremos persistir estos objetos en un fichero necesitamos que sean de tipo Serializable, por eso lo incluimos en la declaración
 */
public class Tarea implements Serializable {
	/**
	 * serialVersionUID es un identificador que servirá para verificar que un objeto persistido corresponde con la versión del objeto donde se quiere leer
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String patronFormato = "dd/MM/yyyy";
	private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern(patronFormato);
	
	/**
	 * Lista de campos de una tara
	 */
	private String nombre;
	private String detalles;
	
	private boolean urgente = false;
	private boolean comenzada = false;
	private boolean finalizada = false;
	
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalDate vencimiento;

	/**
	 * Devuelve si es urgente o no esta tarea
	 * @return the urgente
	 */
	public boolean isUrgente() {
		return urgente;
	}

	/**
	 * Marca como urgente una tarea
	 */
	public void setUrgente() {
		this.urgente = true;
	}
    /**
     * Cambia el estado de urgencia de la tarea
     */
	public void cambiaUrgente() {
		this.urgente = !this.urgente;
	}

	/**
	 * Devuelve si la tarea está finalizada o no
	 * @return the finalizada
	 */
	public boolean isFinalizada() {
		return finalizada;
	}

	/**
	 * Marca como finalizada una tarea, y establece la fecha de finalización a al fecha actual
	 */
	public void finalizar() {
		this.finalizada = true;
		this.setFechaFin(LocalDate.now());
	}

	/**
	 * @return the comenzada
	 */
	public boolean isComenzada() {
		return comenzada;
	}

	/**
	 * Marca como comenzada una tarea, y establece la fecha de comienzo a la fecha actual
	 */
	public void comenzar() {
		this.comenzada = true;
		this.setFechaInicio(LocalDate.now());
	}

	/**
	 * Devuelve la descripción de la tarea
	 * @return La descripción de la tarea, los detalles
	 */
	public String getDetalles() {
		return detalles;
	}

	/**
	 * Establece la descripción de la tarea
	 * @param detalles el argumento del método son los detalles o la descripción de la tarea
	 */
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	/**
	 * Consulta el nombre de la tarea
	 * @return Devuelve el nombre de la tarea
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre de la tarea
	 * @param nombre nombre que se le quiere poner a la tarea (identificador)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Consulta la fecha de inicio de la tarea
	 * @return la fecha de inicio de la tarea
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * Establece la fecha de inicio de la tarea
	 * @param fechaInicio la fecha en la que se inicia la tarea
	 */
	private void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public LocalDate getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin fecha en la que se termina la tarea
	 */
	private void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public boolean isOverdued() {
		boolean overdued = false;
		if (vencimiento != null) {
			if (!finalizada & LocalDate.now().isAfter(vencimiento)) {
				overdued = true;
			}
			if (finalizada) {
				if (fechaFin.isAfter(vencimiento)) {
					overdued = true;
				}
			}
		}
		return overdued;
	}

	@Override
	public String toString() {
		String tarea = "Datos de la tarea " + this.nombre + ":\n";
		tarea += "Detalles: " + this.detalles + "\n";
		if (!finalizada && urgente)
			tarea += "ESTA TAREA ES URGENTE\n";
		if (isOverdued()) {
			tarea += "CUIDADO ESTA TAREA VA RETRASADA\n";
		}
		if (this.vencimiento != null)
			tarea += "Fecha de vencimiento: " + this.vencimiento.format(fmt) + "\n";

		if (comenzada) {
			tarea += "Iniciada el " + this.fechaInicio.format(fmt) + "\n";
			if (finalizada) {
				tarea += "Finalizada el " + this.fechaFin.format(fmt) + "\n";
			} else {
				tarea += "No finalizada\n";

			}
		} else {
			tarea += "No iniciada \n";
		}
		return tarea;
	}

	/**
	 * @return la fecha de vencimiento de la tarea
	 */
	public LocalDate getVencimiento() {
		return vencimiento;
	}

	/**
	 * Establece la fecha de vencimiento de la tarea, recibiendo como argumento un objeto LocalDate
	 * @param vencimiento la fecha límite para terminar la tarea
	 */
	public void setVencimiento(LocalDate vencimiento) {
		this.vencimiento = vencimiento;
	}

	/**
	 * Establece la fecha de vencimiento de la tarea, recibiendo como parámetro un String
	 * @param vencimiento la fecha límite para terminar la tarea
	 */
	public void setVencimiento(String vencimiento) {
		if(fechaValida(vencimiento))
			this.vencimiento = LocalDate.parse(vencimiento, fmt);
	}
    /**
     * Devuelve el formato de fecha que se maneja en esta tarea
     * @return una cadena de texto con el formato manejado
     */
	public String getFormato() {

		return patronFormato;
	}
	
	 /**
     * Este método se utiliza para saber si el formato de fecha introducido coincide con el que se utiliza en la tarea
     * @param fecha la fecha que queremos verificar si tiene un formato válido
     * @return true si el formato es válido, false en caso contrario
     */
	public boolean fechaValida(String fecha) {
		boolean valido = true;
		try {
			LocalDate.parse(fecha, fmt);
		} catch (Exception e) {
			valido = false;
		}
		return valido;
	}

}
