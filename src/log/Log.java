package log;

import java.awt.TextArea;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

public abstract class Log {

	private static JTextArea outputArea;

	public static void log(String text) {
		if (text == null) {
			text = "<null>";
		}

		text = formatNow() + " " + text.trim();
		System.out.println(text);

		if (outputArea != null) {
			outputArea.append("\n" + text);
		}
	}

	public static void clear() {
		if (outputArea != null) {
			outputArea.setText("");
		}
	}

	public static void log(Object o) {
		if (o == null) {
			log(null);
			return;
		}
		log(o.toString());
	}

	public static String formatNow() {
		Date date = new Date();
		return new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss").format(date);
	}

	public static void setOutputArea(JTextArea outputArea) {
		Log.outputArea = outputArea;
	}

}
