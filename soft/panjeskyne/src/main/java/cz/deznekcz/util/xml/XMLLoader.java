package cz.deznekcz.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import cz.deznekcz.games.panjeskyne.utils.Out;

/**
 * XML loading provider
 * @author Zdenek Novotny (DeznekCZ)
 *
 */
public class XMLLoader {

	public static Node load(File xmlFile) throws IOException {
		Out<IOException> eOut = Out.init();
		Node node = load(xmlFile, eOut);
		if (eOut.isNull())
			return node;
		else
			throw eOut.get();
	}

	public static Node load(File xmlFile, Out<IOException> exception) {
		try {
			DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = parser.parse(xmlFile);
			return document.getDocumentElement();
		} catch (IOException | SAXException | IllegalArgumentException | ParserConfigurationException e) {
			exception.set(new IOException(e));
			return null;
		}
	}

	public static void save(File xmlFile, XML xmlTree) throws IOException {
		PrintStream ps = new PrintStream(xmlFile, xmlTree.getCharset());
		xmlTree.write(ps);
		ps.close();
	}

}
