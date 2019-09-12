package cz.deznekcz.games.panjeskyne.client;

import cz.deznekcz.games.panjeskyne.service.KindService;
import cz.deznekcz.games.panjeskyne.service.SkillService;
import cz.deznekcz.games.panjeskyne.service.StatisticService;

public class Services {

	private static StatisticService statisticService;
	private static SkillService skillService;
	private static KindService kindService;

	public static StatisticService getStatisticService() {
		if (statisticService == null) {
			initServices();
		}
		return statisticService;
	}

	public static SkillService getSkillService() {
		getStatisticService();
		return skillService;
	}
	
	public static KindService getKindService() {
		getStatisticService();
		return kindService;
	}
	
	private static void initServices() {
		statisticService = new StatisticService();
		skillService     = new SkillService();
		kindService      = new KindService();
	}

}
