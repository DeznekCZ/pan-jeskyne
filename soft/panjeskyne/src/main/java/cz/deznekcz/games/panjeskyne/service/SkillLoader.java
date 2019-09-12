package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.ImmutableMap;

import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroup;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroups;

public class SkillLoader {
	
	private static final String SKILL_XML = "/home/data/skills.xml";

	public static ImmutableMap<String, SkillGroup> loadSkills() {
		ImmutableMap.Builder<String,SkillGroup> builder= ImmutableMap.builder();
		
		try {
			File file = new File(SKILL_XML);
			if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());
			JAXBContext jc = JAXBContext.newInstance(SkillGroups.class);
            Unmarshaller u = jc.createUnmarshaller();
            
            SkillGroups stats = (SkillGroups)u.unmarshal(file);
			for(SkillGroup skillGroup : stats.getSkillGroups()) {
				for (Skill skill : skillGroup.getSkills().values()) {
					skill.setSkillGroup(skillGroup);
				}
				builder.put(skillGroup.getCodename(), skillGroup);
			}
		} catch (JAXBException | IOException e) {
			throw new IllegalStateException("Statistics could not be loaded from the XML.", e);
		}
		
		return builder.build();
	}

}
