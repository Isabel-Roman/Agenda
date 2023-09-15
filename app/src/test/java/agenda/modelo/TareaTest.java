package agenda.modelo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import java.io.Serializable;

class TareaTest implements Serializable {

	/**
	 * Id de la versión, para la serialización
	 */
	private static final long serialVersionUID = 1L;
	static private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Test
	void testVencimiento() {
		Tarea tarea = new Tarea();
		tarea.setNombre("Acabar Curso");
		tarea.setDetalles("Terminar las tareas del curso de iniciación a la programación");
		tarea.comenzar();
		assertFalse(tarea.isOverdued(), "La tarea no debería estar marcada como vencida");
		tarea.setVencimiento(LocalDate.parse("30/11/3000", fmt));
		assertFalse(tarea.isOverdued(), "La tarea no debería estar marcada como vencida");
		tarea.setVencimiento(LocalDate.parse("30/11/1999", fmt));
		assertTrue(tarea.isOverdued(), "La tarea debería estar marcada como vencida");
		tarea.finalizar();
		assertTrue(tarea.isOverdued(), "La tarea no debería estar marcada como vencida");
		tarea.setVencimiento(LocalDate.parse("30/11/3000", fmt));
		assertFalse(tarea.isOverdued(), "La tarea no debería estar marcada como vencida");
	}

}
