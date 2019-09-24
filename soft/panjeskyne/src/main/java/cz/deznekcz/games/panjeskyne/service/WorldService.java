package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Lists;

import cz.deznekcz.games.panjeskyne.data.RulesData;
import cz.deznekcz.games.panjeskyne.data.WorldData;

public class WorldService {

	private static final File DIRECTORY = new File("/home/data/world");
	static {
		if (!DIRECTORY.exists()) DIRECTORY.mkdirs();
	}
	
	public static List<WorldData> getAll() {
		File[] rules = DIRECTORY.listFiles(WorldService::fileFilter);
		
		List<WorldData> list = Lists.newArrayList();
		
		if (rules != null && rules.length > 0) {
			for (File ruleDir: rules) {
				File worldXml = new File(ruleDir.getAbsolutePath() + "/world.xml");
	            
	            try {
					JAXBContext jc = JAXBContext.newInstance(WorldData.class);
		            Unmarshaller u = jc.createUnmarshaller();
					WorldData data = (WorldData)u.unmarshal(worldXml);
					list.add(data);
				} catch (JAXBException e) {
					WorldData data = new WorldData();
					data.init(worldXml.getName());
					data.setError(e);
					list.add(data);
				}
			}
		}
		
		return list;
	}

	private static boolean fileFilter(File file) {
		return file.isDirectory();
	}

}
