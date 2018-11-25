package cz.panjeskyne.service.impl;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.util.ResourceUtils;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.xml.Skill;
import cz.panjeskyne.model.xml.SkillGroup;
import cz.panjeskyne.model.xml.SkillGroups;

public class SkillLoader {
	
	private static final String SKILL_XML = "classpath:moduleData/skills.xml";

	public static ImmutableMap<String, SkillGroup> loadSkills() {
		ImmutableMap.Builder<String,SkillGroup> builder= ImmutableMap.builder();
		
		try {
			File file = ResourceUtils.getFile(SKILL_XML);
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
