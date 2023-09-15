/**
 * Clase de tipo Entidad (estructura de información). Almacena los datos de una tarea y proporciona algunas capacidades básicas para la gestión de sus campos
 */
package agenda.modelo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 
 */
public class Tarea {
	static private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private boolean urgente=false;
	private boolean comenzada=false;
	
	private boolean finalizada=false;
		
	private String detalles;
	private String nombre;
	
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalDate vencimiento;
	
	/**
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
	 * @return the finalizada
	 */
	public boolean isFinalizada() {
		return finalizada;
	}
	/**
	 * Marca como finalizada una tarea
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
	 * Marca como comenzada una tarea
	 */
	public void comenzar() {
		this.comenzada = true;
		this.setFechaInicio(LocalDate.now());
	}
	/**
	 * @return the detalles
	 */
	public String getDetalles() {
		return detalles;
	}
	/**
	 * @param detalles the detalles to set
	 */
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre nombre que se le quiere poner a la tarea (identificador)
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return la fecha de inicio de la tarea
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio la fecha en la que se inicia la tarea
	 * @return 
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
		boolean overdued=false;
		if(vencimiento!=null) {
			if(!finalizada & LocalDate.now().isAfter(vencimiento)){
				overdued=true;
			}
			if(finalizada) {
				if(fechaFin.isAfter(vencimiento)) {
					overdued=true;
			}
			}
		}
		return overdued;
	}
	@Override
	public String toString() {
		String tarea="Datos de la tarea "+this.nombre+":\n";
		tarea+="Detalles: "+this.detalles+"\n";
		tarea+="Fecha de vencimiento: "+this.vencimiento.format(fmt)+"\n";
		if(comenzada) {
			tarea+="Iniciada el "+this.fechaInicio.format(fmt)+"\n";
			if(finalizada) {
				tarea+="Finalizada el "+this.fechaFin.format(fmt)+"\n";
			} else {
				tarea+="No finalizada\n";
				if(isOverdued()) {
					tarea+="CUIDADO ESTA TAREA VA RETRASADA\n";
				}				
			}
		}else {
			tarea+="No iniciada \n";
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
	 * @param vencimiento la fecha límite para terminar la tarea
	 */
	public void setVencimiento(LocalDate vencimiento) {
		this.vencimiento = vencimiento;
	}
	
	}
