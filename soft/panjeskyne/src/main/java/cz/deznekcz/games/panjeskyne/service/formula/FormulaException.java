package cz.deznekcz.games.panjeskyne.service.formula;


import cz.deznekcz.games.panjeskyne.i18n.I18NA;

public class FormulaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1134016100267431628L;
	private I18NA message;

	public FormulaException(I18NA localisedMessage) {
		this.message = localisedMessage;
	}

	@Override
	public String getLocalizedMessage() {
		return message.getString();
	}
	
	@Override
	public String getMessage() {
		throw new RuntimeException("NOT SUPPORTED METHOD");
	}
}
