package report;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Archivo: ReportPeople.java contiene la definici�n de la clase ReportPeople
 * que extiende de FormatReport.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class ReportPeople extends FormatReport {
	// declaraci�n de atributos
	private static final String REPORTE = "personas.jasper";

	/**
	 * M�todo obtenerInforme
	 * 
	 * @throws excepcion JRException
	 */
	@Override
	public void obtenerInforme() throws JRException {
		jasperPrint = JasperFillManager.fillReport("reports/" + REPORTE, null, conexion);
	}// cierre m�todo obtenerInforme

	/**
	 * M�todo compilarInforme
	 * 
	 * @throws excepcion JRException
	 */
	@Override
	public void compilarInforme() throws JRException {
		exportar = new JRPdfExporter();
		exportar.setExporterInput(new SimpleExporterInput(jasperPrint));
		exportar.setExporterOutput(new SimpleOutputStreamExporterOutput("reports/reporte.pdf"));
		conf = new SimplePdfExporterConfiguration();
		exportar.setConfiguration(conf);
		exportar.exportReport();
	}// cierre m�todo compilarInforme

	/**
	 * M�todo MuestraInforme
	 * 
	 * @throws excepcion JRException
	 */
	@Override
	public void MuestraInforme() throws IOException {
		File path = new File("reports/reporte.pdf");
		Desktop.getDesktop().open(path);

	}// cierre m�todo MuestraInforme

}// cierre clase ReportPeople
