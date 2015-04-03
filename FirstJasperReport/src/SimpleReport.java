import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import ro.fortech.business.MovieController;
import ro.fortech.model.Movie;

public class SimpleReport {
	DefaultTableModel tableModel;

	public SimpleReport(String[][] movieData) {
		JasperPrint jasperPrint = null;
		TableModelData(movieData);
		try {
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", "Movies");
			parameters.put("Author", "P.A.I.");

			/*String s = JasperCompileManager
					.compileReportToFile("reports/report1.jrxml");*/

			jasperPrint = JasperFillManager.fillReport(
					"reports/report1.jasper", parameters,
					new JRTableModelDataSource(tableModel));
			JasperViewer jasperViewer = new JasperViewer(jasperPrint);
			jasperViewer.setVisible(true);
		} catch (JRException ex) {
			ex.printStackTrace();
		}
	}

	private void TableModelData(String[][] movieData) {
		String[] columnNames = { "Id", "Title", "Director", "Year" };
		/*
		 * String[][] data = { { "111", "G Conger", " Orthopaedic",
		 * "jim@wheremail.com" }, { "222", "A Date", "ENT", "adate@somemail.com"
		 * }, { "333", "R Linz", "Paedriatics", "rlinz@heremail.com" }, { "444",
		 * "V Sethi", "Nephrology", "vsethi@whomail.com" }, { "555", "K Rao",
		 * "Orthopaedics", "krao@whatmail.com" }, { "666", "V Santana",
		 * "Nephrology", "vsan@whenmail.com" }, { "777", "J Pollock",
		 * "Nephrology", "jpol@domail.com" }, { "888", "H David", "Nephrology",
		 * "hdavid@donemail.com" }, { "999", "P Patel", "Nephrology",
		 * "ppatel@gomail.com" }, { "101", "C Comer", "Nephrology",
		 * "ccomer@whymail.com" } };
		 */
		tableModel = new DefaultTableModel(movieData, columnNames);
	}

	public static void main(String[] args) {

		MovieController movieCtrl = new MovieController();
		List<Movie> movies = movieCtrl.searchDocument();

		String[][] movieData = new String[movies.size()][4];

		int i = 0;
		for (Movie movie : movies) {
			String[] movieArray = new String[4];

			movieArray[0] = String.valueOf(movie.getId());
			movieArray[1] = movie.getTitle();
			movieArray[2] = movie.getDirector();
			movieArray[3] = String.valueOf(movie.getYear());
			movieData[i++] = movieArray;
		}

		new SimpleReport(movieData);

	}
}