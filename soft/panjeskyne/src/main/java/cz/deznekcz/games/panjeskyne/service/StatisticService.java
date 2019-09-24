package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.i18n.I18N;
import cz.deznekcz.games.panjeskyne.model.xml.Kind;
import cz.deznekcz.games.panjeskyne.model.xml.Statistic;
import cz.deznekcz.games.panjeskyne.model.xml.Statistics;
import cz.deznekcz.games.panjeskyne.model.xml.skill.CharacterSkill;
import cz.deznekcz.games.panjeskyne.model.xml.skill.KindSkill;
import cz.deznekcz.games.panjeskyne.module.AModule;
import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.SkillService;
import cz.deznekcz.games.panjeskyne.service.StatisticService;
import cz.deznekcz.games.panjeskyne.service.formula.Formula;
import cz.deznekcz.games.panjeskyne.service.formula.FormulaException;
import cz.deznekcz.games.panjeskyne.service.formula.Result;
import cz.deznekcz.util.xml.XML;
import cz.deznekcz.util.xml.XMLPairTag;
import cz.deznekcz.util.xml.XMLRoot;

public class StatisticService {

	private static final String STATS_XML = "/home/data/%s/stats.xml";

	private Map<String, Statistic> statistics;

	private Map<String, List<Statistic>> groups;
	
	private Map<String, List<Statistic>> dependents;

	private AModule module;

	public StatisticService(AModule module) {
		this.module = module;
		
		try {
			File file = new File(String.format(STATS_XML, module.getId()));
			if (!file.exists()) throw new FileNotFoundException(file.getAbsolutePath());
			
			JAXBContext jc = JAXBContext.newInstance(Statistics.class);
            Unmarshaller u = jc.createUnmarshaller();
            
			Statistics stats = (Statistics)u.unmarshal(file);
			statistics = Maps.newHashMap();
			for(Statistic stat : stats.getStatistics()) {
				statistics.put(stat.getCodename(), stat);
			}
		} catch (JAXBException | IOException e) {
			throw new IllegalStateException("Statistics could not be loaded from the XML.", e);
		}
		
		dependents = Maps.newHashMap();
		for(Statistic statistic : statistics.values()) {
			List<Statistic> dependents = new ArrayList<>();
			for(Statistic dependent : statistics.values()) {
				String forumula = dependent.getFormula();
				if(StringUtils.contains(forumula, statistic.getCodename())) {
					dependents.add(dependent);
				}
			}
			this.dependents.put(statistic.getCodename(), dependents);
		}
		
		groups = Maps.newHashMap();
		for(Statistic statistic : statistics.values()) {
			if (statistic.hasGroups()) {
				for (String groupCode : statistic.getGroups().split(";")) {
					List<Statistic> group = groups.get(groupCode);
					if (group == null) {
						group = new ArrayList<>(2);
						groups.put(groupCode, group);
					}
					group.add(statistic);
				}
			}
		}
	}

	public Statistic getByCodename(String codename) {
		return statistics.get(codename);
	}

	public List<Statistic> getDependentStatistics(String codename) {
		return dependents.get(codename);
	}
	
	public Collection<Statistic> getAll() {
		return statistics.values();
	}

	public Result getValue(Character character, Statistic statistic) {
		Result result = new Result(); 
		
		if (statistic.hasFormula()) {
			Result inMiddle = parseFormula(statistic);
			if (!inMiddle.isSuccessful())
				return inMiddle;
			
			inMiddle.applyFormula(this, character);
			result.setValue(inMiddle.getValue());
			result.setException(inMiddle.getException());
		} else if (statistic.isCharacterData()) {
//			result.setValue(character.getData(statistic.getCodename()));
		}
		
		if (result.isSuccessful() && !statistic.isVoid()) {
			KindService kindService = getModule().getKindService();
			SkillService skillService = getModule().getSkillService();
			
			Kind kind = kindService.getCharactersKind(character);
			if (kind == null) kind = Kind.EMPTY;
			result.increase(kind.getStatisticBonus(statistic.getCodename()));
			
			double addition = 0;
			double multiply = 100;
			
//			for (SkillData skill : module.getCharacterService().getCharacterSkills(character)) {
//				addition += skillService.getAdditionBonus(skill.getRef(), skill.getLevel(), statistic.getId());
//				multiply += skillService.getMultiplyBonus(skill.getRef(), skill.getLevel(), statistic.getId());
//			}
			
			result.increase(addition);
			result.multiply(0.01 * multiply);
		}
		
		return result;
	}

	private AModule getModule() {
		return module;
	}

	public Result validateFormula(String formula) {
		Statistic statistic = new Statistic();
		statistic.setFormula(formula);
		return parseFormula(statistic);
	}

	private Result parseFormula(Statistic statistic) {
		return Formula.parse(this, statistic);
	}

	public Result getFormulaValue(Character character, String formula) {
		Statistic statistic = new Statistic();
		statistic.setId("void");
		statistic.setFormula(formula);
		
		return getValue(character, statistic);
	}

	public List<Statistic> getStatisticsByGroup(String group) {
		return groups.getOrDefault(group, new ArrayList<>(0));
	}
	
	public Result getValue(Character character, String codename) {
		Statistic statistic = getByCodename(codename);
		if (statistic == null) {
			Result r = new Result();
			r.setException(new FormulaException(I18N.argumented(I18N.DATA_NOT_FOUND, I18N.id(codename))));
			return r;
		} else {
			return getValue(character, statistic);
		}
	}
	
	public void save() {
		XML xml = XML.init("stats");
		XMLRoot root = xml.root();
		
		for (Statistic statistic : statistics.values()) {
			XMLPairTag<XMLRoot> stat = root.newPairTag("statistic");
			statistic.write(stat);
		}
		
		System.out.println(xml.write());
	}
}