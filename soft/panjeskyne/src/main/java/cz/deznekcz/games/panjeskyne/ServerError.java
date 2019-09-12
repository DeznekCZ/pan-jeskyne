package cz.deznekcz.games.panjeskyne;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.vaadin.server.ErrorMessage;
import com.vaadin.shared.ui.ErrorLevel;

public class ServerError implements ErrorMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6740146095186908556L;
	private String errText;

	ServerError(Throwable e) {
		StringWriter writer = new StringWriter();
		PrintWriter print = new PrintWriter(writer, true);
		
		e.printStackTrace(print);
		
		errText = writer.toString();
	}

	@Override
	public ErrorLevel getErrorLevel() {
		return ErrorLevel.CRITICAL;
	}

	@Override
	public String getFormattedHtmlMessage() {
		return errText;
	}

}
