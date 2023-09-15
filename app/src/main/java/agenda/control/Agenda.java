/**
 * 
 * Clase controladora de la agenda de tareas
 * Incluye el método main, inicia y controla el flujo del programa
 **/
package agenda.control;

import java.util.ArrayList;

import agenda.modelo.Tarea;
import agenda.modelo.TareaDAO;
import agenda.vista.GestorCLI;

public class Agenda {
	

    public static void main(String[] args) {
    	byte opcionMenu;       
        /**
         * TareaDAO permite manejar las tareas
         */
        TareaDAO tareas=new TareaDAO("D:\\tareas.dat");
        GestorCLI cli=new GestorCLI();
        
        
    }
}
