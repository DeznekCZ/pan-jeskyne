package cz.panjeskyne.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.google.common.collect.ImmutableMap;

import cz.panjeskyne.model.xml.Statistic;
import cz.panjeskyne.model.xml.Statistics;
import cz.panjeskyne.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {

	private static final String STATS_XML = "classpath:moduleData/stats.xml";

	private ImmutableMap<String, Statistic> statistics;
	
	private ImmutableMap<String, List<Statistic>> dependents;

	@PostConstruct
	private void init() {
		try {
			File file = ResourceUtils.getFile(STATS_XML);
			JAXBContext jc = JAXBContext.newInstance(Statistics.class);
            Unmarshaller u = jc.createUnmarshaller();
            
			Statistics stats = (Statistics)u.unmarshal(file);
			ImmutableMap.Builder<String, Statistic> builder = ImmutableMap.builder();
			for(Statistic stat : stats.getStatistics()) {
				builder.put(stat.getCodename(), stat);
			}
			statistics = builder.build();
		} catch (JAXBException | IOException e) {
			throw new IllegalStateException("Statistics could not be loaded from the XML.", e);
		}
		
		ImmutableMap.Builder<String, List<Statistic>> builderDep = ImmutableMap.builder();
		for(Statistic statistic : statistics.values()) {
			List<Statistic> dependents = new ArrayList<>();
			for(Statistic dependent : statistics.values()) {
				String forumula = dependent.getFormula();
				if(StringUtils.containsIgnoreCase(forumula, statistic.getCodename())) {
					dependents.add(dependent);
				}
			}
			builderDep.put(statistic.getCodename(), dependents);
		}
		dependents = builderDep.build();
	}

	@Override
	public Statistic getByCodename(String codename) {
		return statistics.get(codename);
	}

	@Override
	public List<Statistic> getDependentStatistics(String codename) {
		return dependents.get(codename);
	}
	
	@Override
	public Collection<Statistic> getAll() {
		return statistics.values();
	}
}
