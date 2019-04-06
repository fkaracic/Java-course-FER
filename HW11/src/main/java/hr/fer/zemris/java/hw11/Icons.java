package hr.fer.zemris.java.hw11;

import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;

/**
 * Represents icons used for notepad. Has two icons: green disk and red disk
 * used as modification indicator.
 * 
 * @author Filip Karacic
 *
 */
public class Icons {
	/**
	 * Green disk icon representing file not modified.
	 */
	public static ImageIcon greenDisk;
	/**
	 * Red disk icon representing file modified.
	 */
	public static ImageIcon redDisk;

	static {
		try {
			greenDisk = create("/hr/fer/zemris/java/hw11/jnotepadpp/icons/greenDisk.png");
			redDisk = create("/hr/fer/zemris/java/hw11/jnotepadpp/icons/redDisk.png");
		} catch (Exception e) {
		}
	}

	/**
	 * Creates {@code ImageIcon} object representing icon.
	 * 
	 * @param resource
	 *            source of the icon.
	 * @return {@code ImageIcon} object representing icon
	 * 
	 * @throws IOException
	 *             if error occurs while loading icon
	 */
	private static ImageIcon create(String resource) throws IOException {
		InputStream is = Icons.class.getResourceAsStream(resource);

		if (is == null)
			throw new IllegalArgumentException();

		byte[] bytes = is.readAllBytes();

		return new ImageIcon(bytes);
	}
}
