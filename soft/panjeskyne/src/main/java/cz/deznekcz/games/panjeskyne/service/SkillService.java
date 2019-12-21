package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Lists;

import cz.deznekcz.games.panjeskyne.data.SkillData;
import cz.deznekcz.games.panjeskyne.data.SkillGroupData;

public class SkillService {
	
	private static SkillService instance;

	private static final File DIRECTORY_SKILLS = new File("/home/data/rules/drdplus2/skills");
	private static final File DIRECTORY_GROUPS = new File("/home/data/rules/drdplus2/skillgroups");

	public static final String UNASSIGNED_GROUP = "unassigned";

	private Map<String, SkillData> skills;
	private Map<String, SkillGroupData> groups;

	public SkillService() {
		skills = new HashMap<>();
		groups = new HashMap<>();
	}

	public List<SkillData> getAllSkills() {
		return new ArrayList<>(skills.values());
	}

	public SkillData getSkill(String codename) {
		return skills.get(codename);
	}
	
	private static boolean skillFilter(File file) {
		return file.isFile() && file.getName().endsWith(".skill.xml");
	}
	
	private static boolean groupFilter(File file) {
		return file.isFile() && file.getName().endsWith(".skillgroup.xml");
	}

	public static SkillService getInstance() {
		if (instance == null) {
			instance = new SkillService();
			
			readGroups();
			readSkills();
		}
		return instance;
	}
	
	private static void readGroups() {
		File[] rules = DIRECTORY_GROUPS.listFiles(SkillService::groupFilter);
		
		List<SkillGroupData> list = Lists.newArrayList();
		
		if (rules != null && rules.length > 0) {
			for (File skillXml: rules) {
	            try {
					JAXBContext jc = JAXBContext.newInstance(SkillGroupData.class);
		            Unmarshaller u = jc.createUnmarshaller();
		            SkillGroupData data = (SkillGroupData)u.unmarshal(skillXml);
					list.add(data);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		}
		
		for (SkillGroupData skillData : list) {
			instance.groups.put(skillData.getId(), skillData);
		}
	}

	private static void readSkills() {
		File[] rules = DIRECTORY_SKILLS.listFiles(SkillService::skillFilter);
		
		List<SkillData> list = Lists.newArrayList();
		
		if (rules != null && rules.length > 0) {
			for (File skillXml: rules) {
	            try {
					JAXBContext jc = JAXBContext.newInstance(SkillData.class);
		            Unmarshaller u = jc.createUnmarshaller();
		            SkillData data = (SkillData)u.unmarshal(skillXml);
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

	public String getDescription(String id) {
		return skills.containsKey(id) ? skills.get(id).getDescription() : "";
	}

	public List<SkillGroupData> getAllGroups() {
		return new ArrayList<>(groups.values());
	}

	public SkillGroupData getGroup(String codename) {
		return groups.get(codename);
	}
}
