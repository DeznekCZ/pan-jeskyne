package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.data.SkillData;
import cz.deznekcz.games.panjeskyne.data.WorldData;
import cz.deznekcz.games.panjeskyne.model.xml.Bonus;
import cz.deznekcz.games.panjeskyne.model.xml.Skill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.SkillGroup;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.service.SkillGroupService;
import cz.deznekcz.games.panjeskyne.service.SkillService;

public class SkillService {
	
	private static SkillService instance;

	private static final File DIRECTORY = new File("/home/data/rules/drdplus2/skills");

	private Map<String, SkillData> skills;

	public SkillService() {
		skills = new HashMap<>();
	}

	public List<SkillData> getAll() {
		return new ArrayList<>(skills.values());
	}

	public SkillData getByCodename(String codename) {
		return skills.get(codename);
	}
	
	private static boolean fileFilter(File file) {
		return file.isFile() && file.getName().endsWith(".skill.xml");
	}

	public static SkillService getInstance() {
		if (instance == null) {
			instance = new SkillService();
			
			File[] rules = DIRECTORY.listFiles(SkillService::fileFilter);
			
			List<SkillData> list = Lists.newArrayList();
			
			if (rules != null && rules.length > 0) {
				for (File skillXml: rules) {
		            try {
						JAXBContext jc = JAXBContext.newInstance(WorldData.class);
			            Unmarshaller u = jc.createUnmarshaller();
			            SkillData data = (SkillData)u.unmarshal(skillXml);
						instance.skills.put(data.getId(), data);
						list.add(data);
					} catch (JAXBException e) {
						e.printStackTrace();
					}
				}
			}
			
			for (SkillData skillData : list) {
				instance.skills.put(skillData.getId(), skillData);
			}
		}
		return instance;
	}
}
