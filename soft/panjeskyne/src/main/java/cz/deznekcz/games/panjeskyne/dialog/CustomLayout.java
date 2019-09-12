package cz.deznekcz.games.panjeskyne.dialog;

public class CustomLayout {

//	public static final String LAYOUTS = "/home/layouts/";
//	
//	public static class CustomLayoutError implements ErrorMessage {
//
//		private String message;
//
//		public CustomLayoutError(String message) {
//			this.message = message;
//		}
//
//		public CustomLayoutError() {
//			this("#žádná zpráva");
//		}
//
//		public ErrorMessage use(String layout) {
//			return new CustomLayoutError("Chybějicí soubor: " + layout);
//		}
//
//		@Override
//		public ErrorLevel getErrorLevel() {
//			return ErrorLevel.ERROR;
//		}
//
//		@Override
//		public String getFormattedHtmlMessage() {
//			return message;
//		}
//		
//	}
//	
//	private static final CustomLayoutError MISSING_FILE = new CustomLayoutError();
//
//	public interface Template {
//		
//		String MISSING_KEY = "#chybějící data";
//
//		Map<String, String> getMap();
//		
//		default String getAttribute(String key) {
//			String value = getMap().getOrDefault(key, MISSING_KEY);
//			return value != null ? value : MISSING_KEY;
//		}
//
//		Character getCharacter();
//	}
//
//	private Template template;
//	private String layout;
//
//	public CustomLayout(Template template, String layout) {
//		this.template = template;
//		this.layout   = layout;
//	}
//
//	public static Component use(Template template, String layout) {
//		return new CustomLayout(template, layout).start();
//	}
//
//	private Component start() {
//		Component master = null;
//		
//		StepDocument stepper = null;
//		try {
//			stepper = XMLStepper.fromFile(LAYOUTS + layout + (layout.endsWith(".xml") ? "" : ".xml"));
//		} catch (IOException e) {
//			master = new VerticalLayout();
//			((VerticalLayout) master).setComponentError(MISSING_FILE.use(layout));
//			System.err.println("Error while loading: " + layout + " Message: " + e.getLocalizedMessage());
//		}
//		
//		if (stepper != null) {
//			master = getComponent(stepper.getRoot());
//		} 
//		return master;
//	}
//
//	private Component getComponent(Step stepper) {
//		//System.err.println("Parsing layout:" + layout + " step: " + stepper.getNodeName());
//		
//		switch (stepper.getNodeName()) {
//		case "HorizontalLayout": return newHoritzontalLayout(stepper);
//		case "VerticalLayout": return newVerticalLayout(stepper);
//		case "Label": return newLabel(stepper);
//		case "TextField": return newTextField(stepper);
//		case "TextArea": return newTextArea(stepper);
//		case "Image": return new VerticalLayout(); // TODO
//		case "StatisticAsField": return newStatisticAsField(stepper); // TODO
//
//		default: return null;
//		}
//	}
//
//	public TextField newStatisticAsField(Step stepper) {
//		StatisticService service = Services.getStatisticService();
//		Statistic statistic = service.getByCodename(stepper.attribute("value"));
//		TextField textField = new TextField();
//		textField.setCaption(statistic.getName());
//		Result result = service.getValue(template.getCharacter(), statistic);
//		if (Boolean.getBoolean(stepper.attribute("floatValue"))) {
//			textField.setValue(result.getValue() + "");
//		} else {
//			textField.setValue((long) result.getValue() + "");
//		}
//		
//		textField.setReadOnly(Boolean.getBoolean(stepper.attribute("readOnly")));
//		return applyCSS(textField, stepper);
//	}
//
//	public TextArea newTextArea(Step stepper) {
//		TextArea textField = new TextArea();
//		textField.setCaption(getText(stepper.attribute("caption")));
//		textField.setValue(getText(stepper.attribute("value")));
//		textField.setReadOnly(Boolean.getBoolean(stepper.attribute("readOnly")));
//		return applyCSS(textField, stepper);
//	}
//
//	private String getText(String text) {
//		StringBuffer replaced = new StringBuffer();
//		
//		Pattern pattern = Pattern.compile("\\$\\{(\\w+)\\}");
//		Matcher matcher = pattern.matcher(text == null ? "" : text);
//		while(matcher.find()) {
//			matcher.appendReplacement(replaced, template.getAttribute(matcher.group(1)));
//		}
//		matcher.appendTail(replaced);
//		
//		return replaced.toString();
//	}
//
//	public TextField newTextField(Step stepper) {
//		TextField textField = new TextField();
//		textField.setCaption(getText(stepper.attribute("caption")));
//		textField.setValue(getText(stepper.attribute("value")));
//		textField.setReadOnly(Boolean.getBoolean(stepper.attribute("readOnly")));
//		return applyCSS(textField, stepper);
//	}
//
//	public Label newLabel(Step stepper) {
//		Label label = new Label();
//		label.setCaption(getText(stepper.attribute("caption")));
//		return applyCSS(label, stepper);
//	}
//
//	public VerticalLayout newVerticalLayout(Step stepper) {
//		VerticalLayout layout = new VerticalLayout();
//		
//		for (Step componentStep : stepper.getList()) {
//			Component component = getComponent(componentStep);
//			if (component != null) {
//				layout.addComponent(component);
//			}
//		}
//		
//		return applyCSS(layout, stepper);
//	}
//
//	public HorizontalLayout newHoritzontalLayout(Step stepper) {
//		HorizontalLayout layout = new HorizontalLayout();
//		
//		for (Step componentStep : stepper.getList()) {
//			Component component = getComponent(componentStep);
//			if (component != null) {
//				layout.addComponent(component);
//			}
//		}
//		
//		return applyCSS(layout, stepper);
//	}
//
//	private <C extends Component> C applyCSS(C component, Step stepper) {
//		if (stepper.hasAttribute("width")) component.setWidth(stepper.attribute("width"));
//		if (stepper.hasAttribute("height")) component.setHeight(stepper.attribute("height"));
//		return component;
//	}
}
