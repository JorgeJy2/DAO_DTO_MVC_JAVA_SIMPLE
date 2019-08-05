package report.decoratorComponent;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import controller.ControllerPeople;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import report.Report;

/**
 * Archivo: ReportFilterPeople.java contiene la definici�n de la clase
 * ReportFilterPeople que extiende de DecoradorReporte.
 * 
 * @author Jorge Jacobo, Marcos Moreno, Gabriel Garcia, Amanda Franco
 * @version 1.0
 *
 */
public class ReportFilterPeople extends DecoradorReporte {
	// declaraci�n de atributos
	private static final String REPORT_PEOPLE_FILTER = "rep_filter_people.jasper";

	private ControllerPeople controller;

	private Map<String, Object> parameters;

	/**
	 * Constructor con par�metros
	 * 
	 * @param controller objeto de tipo ControllerPeople
	 * @param report     objeto de tipo Report
	 */
	public ReportFilterPeople(ControllerPeople controller, Report report) {
		super(report);
		this.controller = controller;
		obtenerParametros();
	}// cierre constructor

	// m�todo obtenerParametros
	public void obtenerParametros() {
		parameters = new HashMap<String, Object>();
		parameters.put("search", controller.getParametro());
	}// cierre m�todo obtenerParametros

	/**
	 * M�todo obtenerInforme
	 * 
	 * @throws JRException
	 */
	@Override
	public void obtenerInforme() throws JRException {

		jasperPrint = JasperFillManager.fillReport("reports/" + REPORT_PEOPLE_FILTER, parameters, conexion);

	}// cierre m�todo obtenerInforme

	/**
	 * M�ttodo compilarInforme
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
	 * @throws excepcion IOException
	 */
	@Override
	public void MuestraInforme() throws IOException {

		File path = new File("reports/reporte.pdf");
		Desktop.getDesktop().open(path);
	}// cierre m�todo MuestraInforme

}// cierre clase ReportFilterPeople
