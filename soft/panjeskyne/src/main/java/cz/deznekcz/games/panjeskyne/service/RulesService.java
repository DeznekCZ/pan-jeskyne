package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Lists;

import cz.deznekcz.games.panjeskyne.data.Character;
import cz.deznekcz.games.panjeskyne.data.RulesData;

public class RulesService {

	private static final File DIRECTORY = new File("/home/data/rules");
	static {
		if (!DIRECTORY.exists()) DIRECTORY.mkdirs();
	}
	
	public static List<RulesData> getAll() {
		File[] rules = DIRECTORY.listFiles(RulesService::fileFilter);
		
		List<RulesData> list = Lists.newArrayList();
		
		if (rules != null && rules.length > 0) {
			for (File ruleDir: rules) {
				File ruleXml = new File(ruleDir.getAbsolutePath() + "/rules.xml");
	            
	            try {
					JAXBContext jc = JAXBContext.newInstance(RulesData.class);
		            Unmarshaller u = jc.createUnmarshaller();
					RulesData data = (RulesData)u.unmarshal(ruleXml);
					list.add(data);
				} catch (JAXBException e) {
					RulesData data = new RulesData();
					data.init(ruleXml.getName());
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
