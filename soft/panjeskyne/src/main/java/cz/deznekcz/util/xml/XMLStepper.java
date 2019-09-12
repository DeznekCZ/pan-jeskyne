package cz.deznekcz.util.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.deznekcz.games.panjeskyne.service.formula.Pair;
import cz.deznekcz.games.panjeskyne.utils.ForEach;
import cz.deznekcz.games.panjeskyne.utils.OutString;


/**
 * Handles loading of XML document.
 * @author Zdenek Novotny (DeznekCZ)
 * @see #from(Document)
 * @see #fromFile(String)
 * @see #fromFile(File)
 * @see XML
 */
public class XMLStepper {

	private XMLStepper() {}

	public static class StepList implements Step, Iterable<Step> {

		private Step parent;
		private List<Node> list;
		private List<Step> stepList;
		private String listValueName;


		public StepList(String listValueName, NodeList childNodes, Step parent) {
			this.list = new ArrayList<>();
			this.stepList = new ArrayList<>();
			this.listValueName = listValueName;
			
			if ("*".equals(listValueName)) {
				for (Node node : ForEach.DOMNodeIterable(childNodes)) {
					if (node instanceof Element)
					{
						list.add(node);
						stepList.add(new StepNode(node, this));
					}
				}
			} else {
				for (Node node : ForEach.DOMNodeIterable(childNodes)) {
					if (node.getNodeName().equals(listValueName))
					{
						list.add(node);
						stepList.add(new StepNode(node, this));
					}
				}
			}
			this.parent = parent;
		}


		public StepList(String listValueName, List<Step> childNodes, Step parent) {
			this.list = new ArrayList<>();
			this.stepList = new ArrayList<>();
			this.listValueName = listValueName;
			for (Step node : childNodes) {
				if (node.getNodeName().equals(listValueName))
				{
					list.add(node.getXmlNode());
					stepList.add(node);
				}
			}
			this.parent = parent;
		}

		public StepList() {
			// FOR CLONE
		}

		@Override
		public void setXmlNode(Node node) {

		}

		@Override
		public Node getXmlNode() {
			return parent.getXmlNode();
		}

		@Override
		public Step getParent() {
			return parent;
		}

		@Override
		public String getNodeName() {
			return listValueName;
		}

		/**
		 * @see StepList#forEach
		 * @see StepList#asList()
		 * @see StepList#asNodeList()
		 * @throws XMLStepperException
		 */
		@Override
		@Deprecated
		public Step getNode(String path, Where... where) {
			return this;
		}

		/**
		 * @see StepList#forEach
		 * @throws XMLStepperException
		 */
		@Override
		@Deprecated
		public StepList getList(String path, Where... where) {
			return this;
		}

		@Override
		public void collectText(List<String> collector) {
			forEach((step)->collector.add(step.text()));
		}

		/**
		 * Applies loop consumer for each elements that matches filter test.
		 * @param filter instance or lambda of {@link Predicate}&lt;{@link Step}&gt;
		 * @param loop instance or lambda of {@link Consumer}&lt;{@link Step}&gt;
		 * @see Iterable#forEach(Consumer)
		 */
		public void forEach(Predicate<Step> filter, Consumer<Step> loop) {
			for (Step step : stepList) {
				if (filter.test(step))
					loop.accept(step);
			}
		}

		/**
		 * Alternatively can be used {@link StepList this} instance
		 * @return instance of {@link List}. While using do not remove elements, is able no roll back.
		 */
		public List<Node> asNodeList() {
			return list;
		}

		public List<Step> asList() {
			return stepList;
		}

		@Override
		public Iterator<Step> iterator() {
			return stepList.iterator();
		}

		@Override
		public String toString() {
			return "Node list: "+getPath() + "/" + listValueName + "*";
		}

		@Override
		public String getPath() {
			return parent.getPath();
		}

		public int size() {
			return list.size();
		}
	}

	@SuppressWarnings("serial")
	public static class XMLStepperException extends RuntimeException {
		public static final String ELEMENT = "Element not exists: \"";
		public XMLStepperException(String message) {
			super(message);
		}
		public static XMLStepperException notExists(String type, String value) {
			return new XMLStepperException(type + value + "\"");
		}
	}

	public static class StepNode implements Step {

		private Node node;
		private Step parent;

		public StepNode(Node current, Step parent) {
			this.parent = parent;
			this.node = current;
		}

		@Override
		public Node getXmlNode() {
			return node;
		}

		@Override
		public Step getParent() {
			return parent;
		}

		@Override
		public void setXmlNode(Node node) {
			this.node = node;
		}

		@Override
		public String toString() {
			return "Node: "+getParent().getPath() + "/" + node.getNodeName();
		}

	}

	public static class StepDocument implements Step {
		private Document document;
		private Node rootNode;

		public StepDocument(Document document) {
			this.document = document;
			this.rootNode = document;
		}

		@Override
		public Node getXmlNode() {
			return rootNode;
		}

		@Override
		public Step getParent() {
			return null;
		}

		@Override
		public void setXmlNode(Node node) {
			rootNode = node;
		}

		public Document getXMLDocument() {
			return document;
		}

		@Override
		public String toString() {
			return "XML Document with root: "+rootNode.getNodeName();
		}

		@Override
		public String getPath() {
			return "/";
		}

		public Step getRoot() {
			return new StepNode(document.getFirstChild(), this);
		}
	}

	/**
	 * Util class of static method generating specialized instances.
	 * @author Zdenek Novotny (DeznekCZ)
	 * @see Where#atributeMatches(String, String) Where.atributeMatches(name, regex)
	 * @see Where#atributeEquals(String, String) Where.atributeEquals(name, equaledValue)
	 * @see Where#atributeEqualsIgnoreCase(String, String) Where.atributeEqualsIgnoreCase(name, equaledValue)
	 */
	@SuppressWarnings("serial")
	public static class Where extends Pair<String, Predicate<String>> {

		private Supplier<String> toString;

		private Where(String key, Predicate<String> value, Supplier<String> toString) {
			super(key, value);
			this.toString = toString;
		}

		public static Where atributeMatches(String attribute, String regex) {
			return new Where(attribute, string -> string != null && string.matches(regex), () -> String.format("/%s/",regex));
		}

		public static Where atributeEquals(String attribute, String value) {
			return new Where(attribute, string -> string != null && string.equals(value), () -> value);
		}

		public static Where atributeEqualsIgnoreCase(String attribute, String value) {
			return new Where(attribute, string -> string != null && string.equalsIgnoreCase(value), () -> value);
		}

		@Override
		public String toString() {
			return getKey() + "=" + toString.get();
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Where && toString().equals(o.toString());
		}

		@Override
		public int hashCode() {
			return toString().hashCode();
		}
	}

	public static interface Step {

		public default String getNodeName() {
			return getXmlNode() != null ? getXmlNode().getNodeName() : "#NULL";
		}

		public default String getPath() {
			return getParent().getPath() + "/" + getXmlNode().getNodeName();
		}

		public void setXmlNode(Node node);
		public Node getXmlNode();
		public Step getParent();

		public default String attribute(String name) {
			OutString result = OutString.init();
			ForEach.start(ForEach.DOMNodeIterableMap(getXmlNode().getAttributes()), (node) -> {
				if (node.getNodeName().startsWith(name))
				{
					result.set(node.getNodeValue());
					return false;
				}
				else
				{
					return true;
				}
			});
			return result.get();
		}
		public default <R> R attribute(String name, Function<String, R> converter) {
			OutString result = OutString.init();
			ForEach.start(ForEach.DOMNodeIterableMap(getXmlNode().getAttributes()), (node) -> {
				if (node.getNodeName().startsWith(name))
				{
					result.set(node.getNodeValue());
					return false;
				}
				else
				{
					return true;
				}
			});
			return converter.apply(result.get());
		}

		public default void collectText(List<String> collector) {
			collector.add(text());
		}

		public default boolean hasElement(String name)
		{
			if (this.getXmlNode().hasChildNodes())
				for (Node node : ForEach.DOMNodeIterable(getXmlNode().getChildNodes()))
					if (node.getNodeName().equals(name))
						return true;

			return false;
		}

		public default boolean hasAttribute(String name)
		{
			if (this.getXmlNode().hasAttributes())
				for (Node node : ForEach.DOMNodeIterableMap(getXmlNode().getAttributes()))
					if (node.getNodeName().startsWith(name))
						return true;

			return false;
		}

		public default String text() { return getXmlNode().getTextContent(); }

		/**
		 * Returns list of nodes which matches where (if defined)
		 * @param path steps to tree list node "elem1/elem2/finalElem"
		 * @param where array instances of {@link Where}
		 * @return new instance of {@link StepList}
		 * @see Where
		 */
		public default StepList getList(String path, Where... where) {
			if (where == null || where.length == 0) {
				if (path.contains("/")) {
					String rootPath = path.substring(0, path.lastIndexOf('/'));
					String rootEnclosing = path.substring(path.lastIndexOf('/') + 1, path.length());
					return getNode(rootPath).getList(rootEnclosing);
				} else {
					return new StepList(path, getXmlNode().getChildNodes(), this);
				}
			} else {
				StepList list = getList(path);
				List<Step> steps = new ArrayList<>();

				for (Step step: list.asList()) {
					boolean accepted = true;

					for (Where attribute : where) {
						if (!accepted) break;
						accepted &=
							   step.hasAttribute(attribute.getKey())
							&& attribute.getValue().test(step.attribute(attribute.getKey()));
					}

					if (accepted) steps.add(step);
				}
				return new StepList(list.getNodeName(), steps, list.getParent());
			}
		}

		/**
		 * Returns nodes which matches where (if defined)
		 * @param path steps to tree node "elem1/elem2/finalElem"
		 * @param where array instances of {@link Where}
		 * @return new instance of {@link Step}
		 * @see Where
		 */
		public default Step getNode(String path, Where... where) {
			if (where == null || where.length == 0) {
				if (path.contains("/")) {
					String rootPath = path.substring(0, path.lastIndexOf('/'));
					String rootEnclosing = path.substring(path.lastIndexOf('/') + 1, path.length());
					return getNode(rootPath).getNode(rootEnclosing);
				} else {
					for (Node node : ForEach.DOMNodeIterable(getXmlNode().getChildNodes())) {
						if (node.getNodeName().equals(path))
							return new StepNode(node, this);
					}
				}
			} else {
				for (Step step: getList(path).asList()) {
					boolean accepted = true;

					for (Where attribute : where) {
						if (!accepted) break;
						accepted &=
							   step.hasAttribute(attribute.getKey())
							&& attribute.getValue().test(step.attribute(attribute.getKey()));
					}

					if (accepted) return step;
				}
			}
			return null;
		}

		public default String getTextContent() {
			return getXmlNode() != null ? getXmlNode().getTextContent() : "";
		}

		public default StepList getList(Where...where) {
			return getList("*", where);
		}
	}

	public static StepDocument from(Document document) {
		return new StepDocument(document);
	}


	public static boolean hasAttribute(Step step)
	{
		return step.getXmlNode() != null && step.getXmlNode().hasAttributes();
	}

	public static StepDocument fromFile(String xml) throws IOException {
		return fromFile(new File(xml));
	}

	public static StepDocument fromFile(File xml) throws IOException {
		return from(XMLLoader.load(xml).getOwnerDocument());
	}
}
