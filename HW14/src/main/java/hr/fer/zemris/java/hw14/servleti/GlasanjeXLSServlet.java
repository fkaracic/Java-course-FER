package hr.fer.zemris.java.hw14.servleti;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.model.PollOption;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Represents servlet that creates Microsoft Excel document representing results
 * of voting in a poll among the given options. File will be downloaded.
 * 
 * @author Filip Karacic
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * Name of the download file.
	 */
	private static final String fileName = "glasovi.xls";
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename=" + fileName);

		try {
			createDocument(req, resp);
		} catch (Exception e) {
			req.setAttribute("message", "Document could not be created.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}
	}

	/**
	 * Creates new Microsoft Excel document represeting voting results for the
	 * poll.
	 * 
	 * @param req
	 *            current request
	 * @param resp
	 *            response to be made
	 * 
	 * @throws IOException
	 *             if error reading or writing occurs
	 *             
	 * @throws ServletException if servlet error occurs
	 */
	private void createDocument(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		HSSFWorkbook hwb = new HSSFWorkbook();

		HSSFSheet sheet = hwb.createSheet("Voting");

		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Option Name");
		rowhead.createCell((short) 1).setCellValue("Votes");

		String pollID = (String) req.getParameter("pollID");

		List<PollOption> options;
		try {
			options = DAOProvider.getDao().getPollOptions(Long.parseLong(pollID));
			options.sort((o1, o2) -> Long.compare(o2.getVotes(), o1.getVotes()));
			for (int i = 0, h = options.size(); i < h; i++) {
				PollOption option = options.get(i);
				HSSFRow row = sheet.createRow((short) i + 1);
				row.createCell((short) 0).setCellValue(option.getTitle());
				row.createCell((short) 1).setCellValue(option.getVotes());
			}

			hwb.write(resp.getOutputStream());
			hwb.close();

		} catch (NumberFormatException e) {
			req.setAttribute("message", "pollID must be a number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		} catch (SQLException e) {
			req.setAttribute("message", "Error creating XLS file.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		}

	}
}
