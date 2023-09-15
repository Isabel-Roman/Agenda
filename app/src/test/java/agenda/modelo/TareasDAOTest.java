package agenda.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TareasDAOTest {
	static private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testLeeTodas() {
		fail("Not yet implemented");
	}

	@Test
	void testNueva() {
		TareaDAO dao=new TareaDAO("D:\\tareas.dat");
		System.out.println(dao);
		Tarea tarea=dao.nueva();
		tarea.setNombre("nueva2");
		tarea.setDetalles("detalles2");
		tarea.setVencimiento( LocalDate.parse("30/12/2023", fmt));
		dao.guarda(tarea);
		
		System.out.println(dao);
	}

}
