package cz.deznekcz.games.panjeskyne.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

import cz.deznekcz.games.panjeskyne.data.StringList.StringListItem;
import cz.deznekcz.games.panjeskyne.model.xml.ListType;
import cz.deznekcz.games.panjeskyne.model.xml.XmlSerialized;

@XmlRootElement
public class StringList implements XmlSerialized {

	private static final long serialVersionUID = 1L;

	@XmlRootElement(name = "item")
	public static class StringListItem implements XmlSerialized {

		private static final long serialVersionUID = 1L;
		
		@XmlAttribute(name = "value")
		private String value;
		
		public String getValue() {
			return value;
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj instanceof StringListItem
				&& value != null 
				&& value.equals(((StringListItem) obj).getValue());
		}
	}

	@XmlElement(name = "item")
	private List<StringListItem> items;

	public synchronized List<String> getValues() {
		List<String> values = Lists.newArrayList();
		
		if (items != null) {
			for (StringListItem string : items) {
				if (string.getValue() != null) 
					values.add(string.getValue());
			}
		}
		
		return values;
	}
	
	public synchronized void addValue(String value) {
		StringListItem item = new StringListItem();
		item.value = value;
		if (items == null) {
			items = Lists.newArrayList();
			items.add(item);
		} else {
			for (StringListItem stringListItem : items) {
				if (stringListItem.equals(item)) {
					return;
				}
			}
			items.add(item);
		}
	}
	
	public synchronized void removeValue(String value) {
		if (items != null && value != null) {
			for (StringListItem stringListItem : items) {
				if (value.equals(stringListItem.getValue())) {
					items.remove(stringListItem);
				}
			}
		}
	}
}
