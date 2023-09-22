/**
 * 
 */
package agenda.vista;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 */
class CLITest {

	/**
	 * Test method for {@link agenda.vista.GestorCLI#nuevaTarea(agenda.modelo.Tarea)}.
	 */
	@Test
	void testNuevaTarea() {
		GestorCLI cliProbado=new GestorCLI();
	
	
		assertTrue(cliProbado.validaFiltro("2,3,4"),"El filtro 2,3,4 es correcto");
	
		assertFalse(cliProbado.validaFiltro("er"),"El filtro er es incorrecto");
		assertFalse(cliProbado.validaFiltro("9,2,3"),"El filtro 9,2,3 es incorrecto");
		assertFalse(cliProbado.validaFiltro("4,9,3"),"El filtro 4,9,3 es incorrecto");
		assertFalse(cliProbado.validaFiltro("er,6"),"El filtro er,6 es incorrecto");
		assertTrue(cliProbado.validaFiltro("4,3,2,1,5,3"),"El filtro 4,3,2,1,5,3 es correcto");
	}

}
