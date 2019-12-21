package cz.deznekcz.games.panjeskyne.error;

import com.vaadin.server.ErrorMessage;
import com.vaadin.shared.ui.ErrorLevel;
import com.vaadin.ui.Notification;

import cz.deznekcz.games.panjeskyne.model.enums.Gender;
import cz.deznekcz.games.panjeskyne.pages.Dialogs;

public class Errors {

	public static class Notify {

		public static void closeOtherTabs() {
			Dialogs.info("Odhlášení neúspěšné!", "Zavři nejdříve ostatní okna!");
		}

		public static void characterUnsaved(String characterName) {
			Dialogs.info("Upozornění", String.format("Postava \"%s\" <font color=\"red\">NENÍ</font> uložena", characterName));
		}
		
	}

	public static ErrorMessage lenghtMinimum(Gender gender, String string, int i) {
		return new ErrorMessage() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getFormattedHtmlMessage() {
				int mod = (i % 10);
				
				String genderEnd = Gender.MALE == gender ? "ý" : (Gender.FEMALE == gender ? "á" : "é");
				
				switch (mod) {
				case 1: return String.format("%s musí být dlouh%s alespoň než %s znak!", string, genderEnd, i);
				case 2: 
				case 3: 
				case 4: return String.format("%s musí být dlouh%s alespoň než %s znaky!", string, genderEnd, i);
				default: return String.format("%s musí být dlouh%s alespoň než %s znaků!", string, genderEnd, i);
				}
					
			}
			
			@Override
			public ErrorLevel getErrorLevel() {
				return ErrorLevel.ERROR;
			}
		};
	}

	public static ErrorMessage lenghtMaximum(Gender gender, String string, int i) {
		return new ErrorMessage() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getFormattedHtmlMessage() {
				int mod = (i % 10);
				
				String genderEnd = Gender.MALE == gender ? "ý" : (Gender.FEMALE == gender ? "á" : "é");
				
				switch (mod) {
				case 1: return String.format("%s musí být dlouh%s maximálně %s znak!", string, genderEnd, i);
				case 2: 
				case 3: 
				case 4: return String.format("%s musí být dlouh%s maximálně %s znaky!", string, genderEnd, i);
				default: return String.format("%s musí být dlouh%s maximálně %s znaků!", string, genderEnd, i);
				}
					
			}
			
			@Override
			public ErrorLevel getErrorLevel() {
				return ErrorLevel.ERROR;
			}
		};
	}

	public static ErrorMessage wrongPassword() {
		return new ErrorMessage() {
		
			private static final long serialVersionUID = 1L;
			@Override
			public String getFormattedHtmlMessage() {
				return "Chybné heslo!";
			}
			
			@Override
			public ErrorLevel getErrorLevel() {
				return ErrorLevel.ERROR;
			}
		};
	}

	public static ErrorMessage missingUser(String username) {
		return new ErrorMessage() {
			
			private static final long serialVersionUID = 1L;
			@Override
			public String getFormattedHtmlMessage() {
				return String.format("Uživatelské jméno \"%s\" neexistuje!", username);
			}
			
			@Override
			public ErrorLevel getErrorLevel() {
				return ErrorLevel.ERROR;
			}
		};
	}

}
