package com.nttdata.logback_CGR;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FP DUAL NTT DATA - Ejercicio Logback
 * 
 * Clase principal del gestor de descargas. El ejercicio consiste en realizar un 
 * gestor que ""descargue"" una serie de paquetes, y luego comprueba la integridad
 * de cada uno de estos paquete. Esta integridad se basa en si encontramos registros
 * con valores que no estén registrados.
 * 
 * Si se ocurre un fallo de integridad, se vuelve a intentar descargar el paquete.
 * Tras 5 intentos, se indica que no se ha podido descargar el paquete
 * 
 * @author Carlos González Ruiz
 *
 */
public class App {
	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	
	/** Constantes */
	private static final int N_PACKAGES = 10000;
	private static final int N_ATTEMPTS = 5;
	
	/** Variables */
	private static int packages[] = new int[N_PACKAGES];
	private static int downloaded = 0;
	private static int attempts = 0;
	private static boolean errors = false;
	
	/**
	 * Punto de entrada del gestor de descargas.
	 * 
	 * @param args
	 */
    public static void main( String[] args ) {
    	LOG.debug("Método: main() | TRAZA INICIO");
    	
    	do  {
    		// Obtener los datos del paquete con getPackage()
    		packages[downloaded] = getPackage();
    		
    		// Comprobar si los datos son correctos, es decir, si los datos descargados valen 0.
    		if (checkIntegrity(packages[downloaded])) {
    			LOG.info("El paquete nº {} se ha podido descargar correctamente.", downloaded);
    			
    			++downloaded;
    			attempts = 0;
    		// Si el número de intentos es 5, se indica que no se pudo descargar cierto paquete.  
    		} else if (attempts == N_ATTEMPTS) {
    			LOG.error("No se pudo descargar el paquete nº {} correctamente tras {} intentos.", downloaded, N_ATTEMPTS);
    			
    			errors = true;
    			
    			++downloaded;
    			attempts = 0;
    		// Volver a intentar si hay menos de 5 intentos.
    		} else {
    			LOG.warn("No se ha podido descargar el paquete nº {} correctamente, volviendo a intentar...", downloaded);
    			
    			++attempts;
    		}
    		
    	} while (downloaded != N_PACKAGES);
    	
    	// Indicar finalmente si hay errores.
    	if (errors) {
    		LOG.error("Ha habido fallos en la descarga de los paquetes.");
    	} else {
    		LOG.info("Se han descargado todos los paquetes con éxito.");
    	}
    	
    	LOG.debug("Método: main() | TRAZA FIN");
    }
    
    /**
	 * Función para "descargar" los paquetes. 
	 * 
	 * "data" ha de ser 0 ó 1. Si es 1, no se han descargado bien el paquete. Si es 0, lo contrario. 
	 * 
	 */
    private static int getPackage() {
    	int data;
    	LOG.debug("Método: getPackage() | TRAZA INICIO");
    	
    	data = (int)(Math.random() * 2);
    	
    	LOG.debug("Método: getPackage() | TRAZA FIN");
    	return data;
    }
    
    /**
	 * Función para comprobar si se ha descargado bien el paquete según el dato que se pase como argumento. 
	 * 
	 * "data" ha de ser 0 ó 1. Si es 1, no se han descargado bien el paquete (false). Si es 0, lo contrario. (true) 
	 * 
	 * @param data
	 */
    private static boolean checkIntegrity(int data) {
    	boolean check = true;
    	LOG.debug("Método: checkIntegrity() | TRAZA INICIO");
    	
    	if (data == 1) {
    		check = false;
    	}
    	
    	LOG.debug("Método: checkIntegrity() | TRAZA FIN");
    	return check;
    }
}
