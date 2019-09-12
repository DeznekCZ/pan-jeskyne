package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroup;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroups;
import cz.deznekcz.games.panjeskyne.module.AModule;

public class SkillGroupService {

	private final Map<String, SkillGroup> skillGroups;
	
	private static final String SKILL_XML = "/home/data/%s/skills.xml";

	public SkillGroupService(AModule aModule) {
		skillGroups = Maps.newHashMap();
		
		try {
			File file = new File(String.format(SKILL_XML, aModule.getId()));
			if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());
			JAXBContext jc = JAXBContext.newInstance(SkillGroups.class);
            Unmarshaller u = jc.createUnmarshaller();
            
            SkillGroups stats = (SkillGroups)u.unmarshal(file);
			for(SkillGroup skillGroup : stats.getSkillGroups()) {
				for (Skill skill : skillGroup.getSkills().values()) {
					skill.setSkillGroup(skillGroup);
				}
				skillGroups.put(skillGroup.getCodename(), skillGroup);
			}
		} catch (JAXBException | IOException e) {
			throw new IllegalStateException("Statistics could not be loaded from the XML.", e);
		}
	}

	public Collection<SkillGroup> getAll() {
		return skillGroups.values();
	}
}
