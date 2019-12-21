package cz.deznekcz.games.panjeskyne.pages;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vaadin.data.TreeData;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.VaadinRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ItemClick;
import com.vaadin.ui.Tree.ItemClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.vaadin.ui.MenuBar.MenuItem;

import cz.deznekcz.games.panjeskyne.Redirection;
import cz.deznekcz.games.panjeskyne.commands.Commands;
import cz.deznekcz.games.panjeskyne.component.SkillTreeItem;
import cz.deznekcz.games.panjeskyne.component.SkillView;
import cz.deznekcz.games.panjeskyne.data.SkillData;
import cz.deznekcz.games.panjeskyne.data.SkillGroupData;
import cz.deznekcz.games.panjeskyne.drdplus2.SkillInfo;
import cz.deznekcz.games.panjeskyne.layout.MainBar;
import cz.deznekcz.games.panjeskyne.pages.Dialogs.Type;
import cz.deznekcz.games.panjeskyne.service.SkillService;
import cz.deznekcz.games.panjeskyne.user.User;
import cz.deznekcz.games.panjeskyne.utils.Out;
import cz.deznekcz.util.With;
import cz.deznekcz.games.events.ChangeType;
import cz.deznekcz.games.events.SkillChanged;

public class SkillPage extends VerticalLayout implements RootComponent, SkillChanged.Accept, ItemClickListener<SkillTreeItem>, ClickListener {

	private static final String SKILL_ALL = "/skill(group)?/all.*";
	private static final String SKILL_VIEW = "/skill(group)?/view=(\\w+)/?";
	private static final String SKILL_NEW = "/skill(group)?/new.*";
	private static final String SKILL_ADMIN = "skill";
	
	private User user;
	private TextField skillName;
	private PageType type;
	private int added;
	private Label addedCount;
	private MenuItem skills;
	private String skillId;
	private SkillView selectedItem;
	private HorizontalLayout itemHolder;
	private Tree<SkillTreeItem> skillTree;
	private boolean isGroup;

	@Override
	public void init(VaadinRequest request) {
		user = getSession().getAttribute(User.class);
		
		if (user != null) {
			String path = request.getPathInfo();
			if (path.matches(SKILL_ALL)) {
				type = PageType.LIST;
			} else if (path.matches(SKILL_VIEW)) {
				type = PageType.VIEW;
				Matcher parse = Pattern.compile(SKILL_VIEW).matcher(path);
				skillId = parse.group(2);
				isGroup = !parse.group(1).isEmpty();
			} else if (path.matches(SKILL_NEW) && user.isAdmin(SKILL_ADMIN)) {
				type = PageType.NEW;
			} else {
				Redirection.redirect("/skill/all");
				return;
			}
			
			showPage();
		} else {
			Redirection.toLogin();
		}
	}

	private void showPage() {
		setWidth(100, Unit.PERCENTAGE);
		setHeight(100, Unit.PERCENTAGE);
		
		Out<MenuBar> menuBar = Out.init();
		
		Component mainBar = MainBar.get(this, menuBar, "Tvorba postavy");
		addComponent(mainBar);
		
		skills = menuBar.get().addItem("Dovednosti");
		skills.addItem("Zobrazit ...").setCommand(Commands.listSkills());
		if (user.isAdmin(SKILL_ADMIN)) {
			skills.addItem("Vytvořit ...").setCommand(Commands.newSkills());
		}
		
		itemHolder = new HorizontalLayout();
		itemHolder.setWidth(100, Unit.PERCENTAGE);
		itemHolder.setHeight(100, Unit.PERCENTAGE);
		addComponentsAndExpand(itemHolder);
		
		listAll();
		if (type == PageType.EDIT) {
			skillTree.select(treeItemFromId(skillId));
		}
	}

	private SkillTreeItem treeItemFromId(String id) {
		TreeData<SkillTreeItem> data = skillTree.getTreeData();
		if (isGroup) {
			Queue<SkillTreeItem> next = Lists.newLinkedList(data.getRootItems());
			while(!next.isEmpty()) {
				SkillTreeItem ptr = next.poll();
				if (ptr.getType() == SkillTreeItem.Type.SKILL) continue;
				if (ptr.getId().equals(id)) return ptr;
				next.addAll(data.getChildren(ptr));
			}
			return null;
		} else {
			Queue<SkillTreeItem> next = Lists.newLinkedList(data.getRootItems());
			while(!next.isEmpty()) {
				SkillTreeItem ptr = next.poll();
				if (ptr.getType() == SkillTreeItem.Type.GROUP) next.addAll(data.getChildren(ptr));
				if (ptr.getId().equals(id)) return ptr;
				continue;
			}
			return null;
		}
	}

	private void listAll() {
		SkillTreeItem unassigned = null;
		
		List<SkillGroupData> groups = SkillService.getInstance().getAllGroups();
		List<SkillData> values = SkillService.getInstance().getAllSkills();
		
		TreeData<SkillTreeItem> data = new TreeData<>();
		
		Map<String, SkillTreeItem> groupItemHash = Maps.newHashMap();
		Queue<SkillTreeItem> queue = Lists.newLinkedList();
		for (SkillGroupData skillData : groups) {
			SkillTreeItem sgi = new SkillTreeItem();
			sgi.setId(skillData.getId());
			sgi.setType(SkillTreeItem.Type.GROUP);
			sgi.setGroup(skillData.getGroup());
			if (unassigned == null && sgi.getId().equals(SkillService.UNASSIGNED_GROUP))
				unassigned = sgi;
			
			if (skillData.hasGroup()) {
				if (groupItemHash.containsKey(skillData.getGroup())) {
					data.addItem(groupItemHash.get(skillData.getGroup()), sgi);
					groupItemHash.put(skillData.getId(), sgi);
				} else {
					queue.add(sgi);
				}
			} else {
				data.addRootItems(sgi);
				groupItemHash.put(skillData.getId(), sgi);
			}
		}
		
		Map<String, Integer> checks = Maps.newHashMap();
		while (!queue.isEmpty()) {
			SkillTreeItem sgi = queue.poll();
			if (groupItemHash.containsKey(sgi.getGroup())) {
				data.addItem(groupItemHash.get(sgi.getGroup()), sgi);
			} else {
				int act = checks.getOrDefault(sgi.getId(), 0);
				if (act > 10) {
					data.addItem(unassigned, sgi);
					groupItemHash.put(sgi.getId(), sgi);
				} else {
					queue.add(sgi);
					checks.put(sgi.getId(), act + 1);
				}
			}
		}
		
		for (SkillData skillData : values) {
			SkillTreeItem sgi = new SkillTreeItem();
			sgi.setId(skillData.getId());
			sgi.setType(SkillTreeItem.Type.SKILL);
			sgi.setGroup(skillData.getGroup());
			
			if (skillData.hasGroup() && groupItemHash.containsKey(skillData.getGroup())) {
				data.addItem(groupItemHash.get(skillData.getGroup()), sgi);
			} else {
				data.addItem(unassigned, sgi);
			}
		}
		
		if (data.getChildren(unassigned).isEmpty())
			data.removeItem(unassigned);
		
		skillTree = new Tree<>();
		skillTree.setTreeData(data);
		skillTree.setHeight(100, Unit.PERCENTAGE);
		skillTree.addItemClickListener(this);
		
		itemHolder.addComponentsAndExpand(skillTree);
	}

	@Override
	public void skillAdded(String id) {
		if (type == PageType.LIST) {
			skills.setText("<b>(+" + (++added) + ")</b> Dovednosti");
		}
	}

	@Override
	public void skillEdited(String id) {
		if (type == PageType.VIEW && id.equals(skillId)) {
			selectedItem.refreshValues();
		}
	}

	@Override
	public void skillRemoved(String id) {
		if (type == PageType.VIEW && id.equals(skillId)) {
			Dialogs.info("Dovednost", "Dovednost byla odebrána!");
		}
	}

	@Override
	public void itemClick(ItemClick<SkillTreeItem> event) {
		if (event.getItem().getId().equals(skillId)) return;
		
		
		
		if (selectedItem != null) {
			selectedItem.unregisterAccept();
			itemHolder.removeComponent(selectedItem);
		}
			
		selectedItem = new SkillView(event.getItem());
		selectedItem.setHeight(100, Unit.PERCENTAGE);
		itemHolder.addComponentsAndExpand(selectedItem);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		switch (type) {
		case NEW:
		case EDIT:
			if (selectedItem != null && !selectedItem.isSaved()) {
				Dialogs.ask(Type.YES_NO_CANCEL)
					.clickEvent(event)
					.title("Uložit poslední změny:")
					.onOk(this::logoutAndSave)
					.onNo(this::logoutAndScrap);
			}
			break;
		default:
			MainBar.logout(event.getButton().getUI().getSession(), false);
			break;
		}
	}
	
	private void logoutAndSave(Dialogs dialog) {
		saveSkill();
		if (selectedItem == null && selectedItem.isSaved())
			MainBar.logout(dialog.getClickEvent().getButton().getUI().getSession(), false);
	}
	
	private void saveSkill() {
		if (selectedItem == null) selectedItem.save();
	}

	private void logoutAndScrap(Dialogs dialog) {
		selectedItem.unregisterAccept();
		MainBar.logout(dialog.getClickEvent().getButton().getUI().getSession(), false);
	}
}
