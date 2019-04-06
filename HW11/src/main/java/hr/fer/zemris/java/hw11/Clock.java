package hr.fer.zemris.java.hw11;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Represents label that displays current date and current time in form:
 * year/month/day hours:minutes:seconds
 * 
 * @author Filip Karacic
 *
 */
public class Clock extends JLabel {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * String representing current date.
	 */
	private volatile String date;
	/**
	 * String representing current time.
	 */
	private volatile String time;
	/**
	 * Says if displaying should be stopped.
	 */
	private volatile boolean stopRequested;

	/**
	 * Format of the date.
	 */
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	/**
	 * Format of the time.
	 */
	private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	/**
	 * Constructor for this {@code Clock} object representing date and time
	 * displayer.
	 */
	public Clock() {
		updateTime();

		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(500);
				} catch (Exception ex) {
				}

				if (stopRequested)
					break;

				SwingUtilities.invokeLater(() -> {
					updateTime();
				});
			}
		});

		t.setDaemon(true);
		t.start();
	}

	/**
	 * Updates current time and display.
	 */
	private void updateTime() {
		date = dateFormatter.format(LocalDate.now());
		time = timeFormatter.format(LocalTime.now());

		setText(date + " " + time);
	}

	/**
	 * Stops displaying if {@code stopRequested} is <code>true</code>
	 * 
	 * @param stopRequested says if displaying should be stopped
	 */
	public void setStopRequested(boolean stopRequested) {
		this.stopRequested = stopRequested;
	}

}
