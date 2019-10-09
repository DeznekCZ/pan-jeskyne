package cz.deznekcz.games.panjeskyne.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.google.common.collect.Lists;

import cz.deznekcz.games.panjeskyne.data.WorldData;

public class WorldService {

	private static final File DIRECTORY = new File("/home/data/world");
	private static WorldService instance;
	static {
		if (!DIRECTORY.exists()) DIRECTORY.mkdirs();
	}
	
	public List<WorldData> getAll() {
		return new ArrayList<>(worlds.values());
	}

	private static boolean fileFilter(File file) {
		return file.isDirectory();
	}

	public static WorldService getInstance() {
		if (instance == null) {
			instance = new WorldService();
			
			File[] rules = DIRECTORY.listFiles(WorldService::fileFilter);
			
			if (rules != null && rules.length > 0) {
				for (File ruleDir: rules) {
					File worldXml = new File(ruleDir.getAbsolutePath() + "/world.xml");
		            
		            try {
						JAXBContext jc = JAXBContext.newInstance(WorldData.class);
			            Unmarshaller u = jc.createUnmarshaller();
						WorldData data = (WorldData)u.unmarshal(worldXml);
						instance.worlds.put(data.getId(), data);
					} catch (JAXBException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return instance;
	}
	
	@Override
	protected void finalize() throws Throwable {
		worlds.values().forEach(world -> {
			try {
				JAXBContext jc = JAXBContext.newInstance(WorldData.class);
	            Marshaller u = jc.createMarshaller();
	            u.marshal(world, new File(DIRECTORY.getAbsolutePath() + "/" + world.getId() + ".xml"));
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		});
		
		super.finalize();
	}
	
	private Map<String, WorldData> worlds;
	
	public WorldService() {
		worlds = new HashMap<>();
	}

	public WorldData getWorld(String worldId) {
		return worlds.getOrDefault(worldId, WorldData.NONE);
	}
}
