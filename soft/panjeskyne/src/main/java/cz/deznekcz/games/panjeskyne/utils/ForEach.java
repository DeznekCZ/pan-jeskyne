package cz.deznekcz.games.panjeskyne.utils;

import java.util.Iterator;
import java.util.function.Predicate;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ForEach {

	public static Iterable<Node> DOMNodeIterable(NodeList childNodes) {
		return new Iterable<Node>() {
			@Override
			public Iterator<Node> iterator() {
				return new Iterator<Node>() {
					NodeList mChildNodes = childNodes;
					int index = 0;
					
					@Override
					public Node next() {
						return mChildNodes.item(index++);
					}
					
					@Override
					public boolean hasNext() {
						return index < mChildNodes.getLength();
					}
				};
			}
		}; 
	}

	public static Iterable<Node> DOMNodeIterableMap(NamedNodeMap attributes) {
		return new Iterable<Node>() {
			@Override
			public Iterator<Node> iterator() {
				return new Iterator<Node>() {
					NamedNodeMap mAttributes = attributes;
					int index = 0;
					
					@Override
					public Node next() {
						return mAttributes.item(index++);
					}
					
					@Override
					public boolean hasNext() {
						return index < mAttributes.getLength();
					}
				};
			}
		};
	}

	public static <E> void start(Iterable<E> iterable, Predicate<E> step) {
		Iterator<E> iterator = iterable.iterator();
		
		while (iterator.hasNext() && step.test(iterator.next())) ;
	}

}
