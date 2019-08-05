package report;

import java.sql.Connection;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Archivo: FormatReport.java contiene la definici�n de la clase abstracta
 * FormatReport que implementa de Report.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public abstract class FormatReport implements Report {

	// declaraci�n de atributos
	protected JasperPrint jasperPrint;
	protected String name;
	protected Connection conexion;
	protected JRPdfExporter exportar;
	protected JasperPrint jasperPrintWindow;
	protected SimplePdfExporterConfiguration conf;

	/**
	 * M�todo setConexion
	 * 
	 * @param conexion objeto de tipo Connection
	 */
	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}// cierre m�todo setConexion

}// cierre clase FormatReport
