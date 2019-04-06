package hr.fer.zemris.java.hw13.servleti;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Represents servlet that create a Microsoft Excel document. It accepts a three
 * parameters a (integer from [-100,100]) b (integer from [-100,100]) and n
 * (where n>=1 and n<=5). If any parameter is invalid error message is
 * displayed.
 *
 * <p>
 * Action creates a Microsoft Excel document with n pages. On page i there is a
 * table with the two columns. The first column contains integer numbers from a
 * to b. The second column contains i-th powers of these numbers.
 * 
 * @author Filip Karacic
 *
 */
public class PowersServlet extends HttpServlet {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = null;
		Integer b = null;
		Integer n = null;

		try {
			a = Integer.parseInt(req.getParameter("a"));
			b = Integer.parseInt(req.getParameter("b"));
			n = Integer.parseInt(req.getParameter("n"));
		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		if (a < -100 || a > 100 || b < -100 || b > 100 || n < 1 || n > 5) {
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename=tablica.xls");

		createXLS(a, b, n, resp);
	}

	/**
	 * Creates Microsoft Excel document of i-th power for number between a and b,
	 * where i can go from 1 to n.
	 * 
	 * @param a
	 *            interval number
	 * @param b
	 *            interval number
	 * @param n
	 *            power number
	 * @param resp
	 *            response for this request
	 * 
	 * @throws IOException
	 *             if error creating document occurs
	 */
	private void createXLS(Integer a, Integer b, Integer n, HttpServletResponse resp) throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet(i + ". powers");

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Number");
			rowhead.createCell((short) 1).setCellValue(i + ". power");

			int start = Math.min(a, b);
			int end = Math.max(a, b);

			for (int j = start, k = 1; j <= end; j++, k++) {
				HSSFRow row = sheet.createRow((short) k);
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
			}
		}

		hwb.write(resp.getOutputStream());
		hwb.close();

	}

}
