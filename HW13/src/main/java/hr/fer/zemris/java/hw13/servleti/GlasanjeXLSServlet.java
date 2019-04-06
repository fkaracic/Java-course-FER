package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Represents servlet that creates Microsoft Excel document representing results
 * of voting for favourite band among the given. File will be downloaded.
 * 
 * @author Filip Karacic
 *
 */
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename=glasovi.xls");

		try{
			createDocument(req, resp);
		}catch(Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}

	/**
	 * Creates new Microsoft Excel document represeting voting results for the
	 * favourite band.
	 * 
	 * @param req current request
	 * @param resp response to be made
	 * 
	 * @throws IOException if error reading or writing occurs
	 */
	private void createDocument(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Voting");

		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Band");
		rowhead.createCell((short) 1).setCellValue("Votes");

		String rezultati = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path path = Paths.get(rezultati);

		String definicija = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Path filePath = Paths.get(definicija);

		if (!Files.exists(path)) {
			List<String> def = Files.readAllLines(filePath, StandardCharsets.UTF_8);

			for (int i = 0, h = def.size(); i < h; i++) {
				String name = def.get(i).split("\t")[1];
				HSSFRow row = sheet.createRow((short) i+1);
				row.createCell((short) 0).setCellValue(name);
				row.createCell((short) 1).setCellValue(0);
			}
		} else {
			List<String> def = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			List<String> rez = Files.readAllLines(path, StandardCharsets.UTF_8);

			for (int i = 0, h = def.size(); i < h; i++) {
				String name = def.get(i).split("\t")[1];
				String votes = rez.get(i).split("\t")[1];
				HSSFRow row = sheet.createRow((short) i+1);
				row.createCell((short) 0).setCellValue(name);
				row.createCell((short) 1).setCellValue(votes);
			}
		}

		hwb.write(resp.getOutputStream());
		hwb.close();

	}
}
