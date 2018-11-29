package cz.panjeskyne.controller;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cz.panjeskyne.model.xml.Kind;
import cz.panjeskyne.model.xml.Race;
import cz.panjeskyne.service.KindService;
import cz.panjeskyne.service.RaceService;

@Controller
@RequestMapping(RaceKindController.BASE_PATH)
public class RaceKindController {

	public static final String BASE_PATH = "/raceKind";

	@Autowired
	private RaceService raceService;

	@Autowired
	private KindService kindService;
	
	@RequestMapping("/getAllRaces")
	@ResponseBody
	public String getAllRaces() {
		Collection<Race> races = raceService.getAll();
		JSONObject response = new JSONObject();
		response.put("success", true);
		response.put("races", new JSONArray(races));
		
		return response.toString();
	}

	@RequestMapping("/getKindsForRace")
	@ResponseBody
	public String getKindsForRace(@RequestParam String raceCodename) {
		Collection<Kind> kinds = kindService.getKindsForRace(raceCodename);
		JSONObject response = new JSONObject();
		response.put("success", true);
		JSONArray kindsJson = new JSONArray();
		for (Kind kind : kinds) {
			JSONObject kindJson = new JSONObject();
			kindJson.put("value", kind.getId());
			kindJson.put("text", kind.getName());
			kindsJson.put(kindJson);
		}
		response.put("kinds", kindsJson);

		return response.toString();
	}

}
