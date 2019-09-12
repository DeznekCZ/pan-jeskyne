package cz.deznekcz.games.panjeskyne.dialog;

import com.vaadin.server.ErrorMessage;
import com.vaadin.shared.ui.ErrorLevel;

public class LoginError {

	public static final ErrorMessage LOGIN_EMPTY = new ErrorMessage() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getFormattedHtmlMessage() {
			return "Nezadal jste žádné jméno!";
		}
		
		@Override
		public ErrorLevel getErrorLevel() {
			return ErrorLevel.CRITICAL;
		}
	};

	public static final ErrorMessage LOGIN_BAD_USER = new ErrorMessage() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getFormattedHtmlMessage() {
			return "Uživatel s tímto mailem neexistuje!";
		}
		
		@Override
		public ErrorLevel getErrorLevel() {
			return ErrorLevel.CRITICAL;
		}
	};

	public static final ErrorMessage PASSWORD = new ErrorMessage() {
		
		private static final long serialVersionUID = 1L;

		@Override
		public String getFormattedHtmlMessage() {
			return "Heslo neodpovídá!";
		}
		
		@Override
		public ErrorLevel getErrorLevel() {
			return ErrorLevel.CRITICAL;
		}
	};

}
