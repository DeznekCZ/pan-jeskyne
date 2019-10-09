package cz.deznekcz.games.panjeskyne.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "type")
@XmlEnum
public enum CharacterType {
	PC, NPC;
	
	public String value() {
        return name();
    }

    public static CharacterType fromValue(String v) {
        return valueOf(v);
    }
}
