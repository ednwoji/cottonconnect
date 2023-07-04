import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.cottonconnect.elearning.ELearning.dto.Document;
import com.cottonconnect.elearning.ELearning.dto.EntryDTO;
import com.cottonconnect.elearning.ELearning.dto.FaqQueryDTO;
import com.cottonconnect.elearning.ELearning.model.Entitlements;
import com.cottonconnect.elearning.ELearning.model.Menu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	public static void main(String[] args) throws JsonProcessingException {
		/* ObjectMapper mapper = new ObjectMapper();
		EntryDTO question = new EntryDTO();
		
		List<EntryDTO> entryList = new ArrayList<>();
		EntryDTO answer = new EntryDTO();
		answer.setTitle("Answer");
		
		entryList.add(answer);
		question.setTitle("Question");
		question.setChildren(entryList);
		
		System.out.println(mapper.writeValueAsString(question)); */
		
		getMenus();
	}
	
	static void queryDTO() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		FaqQueryDTO queryDTO = new FaqQueryDTO();
		
		Document doc= new Document();
		List<Document> docList = new ArrayList<Document>();
		docList.add(doc);
		
		queryDTO.setDocuments(docList);
		
		System.out.println(mapper.writeValueAsString(queryDTO));
	}
	
	static void getMenus() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		List<Menu> menuList = new ArrayList<Menu>();

		Menu dashboard = new Menu();
		dashboard.setDispName("Dashboard");
		dashboard.setIcon("simple-icon-pie-chart");
		dashboard.setName("dashboard");
		dashboard.setParentId(null);
		dashboard.setSeq(1);
		dashboard.setUrl("dashboard.html");

		List<Entitlements> dashEntList = new ArrayList<Entitlements>();
		Entitlements dashList = new Entitlements();
		dashList.setMenu(dashboard);
		dashList.setName("dashboard.list");
		dashEntList.add(dashList);

		dashboard.setEntitlements(dashEntList);

		// menuList.add(dashboard);

		String dispName = "";
		String icon = "";
		String name = "";
		Menu parentMenu = null;
		int seq = 0;
		String url = "";

		menuList.add(addMenu("Country", "", "Country", 2L, 1, "country.html", "country.list", "country.add",
				"country.edit", "country.delete"));

		menuList.add(addMenu("State", "", "State", 2L, 2, "state.html", "state.list", "state.add", "state.edit",
				"state.delete"));

		menuList.add(addMenu("District", "", "District", 2L, 3, "district.html", "district.list", "district.add",
				"district.edit", "district.delete"));

		menuList.add(addMenu("Taluk", "", "Taluk", 2L, 4, "block.html", "block.list", "block.add", "block.edit",
				"block.delete"));

		menuList.add(addMenu("Village", "", "Village", 2L, 5, "village.html", "village.list", "village.add",
				"village.edit", "village.delete"));

		menuList.add(addMenu("Brand", "", "Brand", 2L, 6, "brand.html", "brand.list", "brand.add", "brand.edit",
				"brand.delete"));

		menuList.add(addMenu("Program", "", "Program", 2L, 7, "program.html", "program.list", "program.add",
				"program.edit", "program.delete"));

		menuList.add(addMenu("Local Partner", "", "Local Partner", 2L, 8, "lpa.html", "lpa.list", "lpa.add", "lpa.edit",
				"lpa.delete"));

		menuList.add(addMenu("Farm Group", "", "Farm Group", 2L, 9, "farmgroup.html", "farmgroup.list", "farmgroup.add",
				"farmgroup.edit", "farmgroup.delete"));

		menuList.add(addMenu("Farmer", "", "Farmer", 3L, 1, "farmer.jsp", "farmer.list", "farmer.add", "farmer.edit",
				"farmer.delete"));

		menuList.add(addMenu("Insect", "", "Insect", 3L, 2, "inspectList.jsp", "insect.list", "insect.add",
				"insect.edit", "insect.delete"));

		menuList.add(addMenu("Disease", "", "Disease", 3L, 3, "diseaseList.jsp", "disease.list", "disease.add",
				"disease.edit", "disease.delete"));

		menuList.add(addMenu("Manure", "", "Manure", 3L, 4, "manureList.jsp", "manure.list", "manure.add",
				"manure.edit", "manure.delete"));

		menuList.add(addMenu("Fertilizer", "", "Fertilizer", 3L, 5, "fertilizerList.jsp", "fertilizer.list",
				"fertilizer.add", "fertilizer.edit", "fertilizer.delete"));

		menuList.add(addMenu("Nutrients", "", "Nutrients", 3L, 6, "nutrientList.jsp", "insect.list", "insect.add",
				"insect.edit", "insect.delete"));

		menuList.add(addMenu("Useful Videos", "", "Useful Videos", 3L, 7, "videoList.jsp", "video.list", "video.add",
				"video.edit", "video.delete"));

		menuList.add(addMenu("Notifications", "", "notifications", 3L, 8, "notificationsList.jsp", "notifications.list",
				"notifications.add", "notifications.edit", "notifications.delete"));

		menuList.add(addMenu("FAQ", "", "faq", 3L, 9, "faqList.jsp", "faq.list", "faq.add", "faq.edit", "faq.delete"));

		menuList.add(
				addMenu("Role", "", "role", 4L, 1, "role.jsp", "role.list", "role.add", "role.edit", "role.delete"));
		
		menuList.add(
				addMenu("Role Menu", "", "Role Menu", 4L, 2, "roleMenu.jsp", "roleMenu.list", "roleMenu.add", "roleMenu.edit", "roleMenu.delete"));
		
		menuList.add(
				addMenu("Role Entitlement", "", "Role Entitlement", 4L, 3, "roleent.jsp", "roleent.list", "roleent.add", "roleent.edit", "roleent.delete"));
		
		menuList.add(
				addMenu("User", "", "User", 4L, 4, "user.jsp", "user.list", "user.add", "user.edit", "user.delete"));
		
	
		System.out.println(mapper.writeValueAsString(menuList));

	
	}

	private static Menu addMenu(String dispName, String icon, String name, Long parent, int seq, String url,
			String ent1, String ent2, String ent3, String ent4) {
		Menu dashboard = new Menu();
		dashboard.setDispName(dispName);
		dashboard.setIcon(icon);
		dashboard.setName(name);
		dashboard.setParentId(parent);
		dashboard.setSeq(seq);
		dashboard.setUrl(url);

		List<Entitlements> dashEntList = new ArrayList<Entitlements>();
		if (!StringUtils.isEmpty(ent1)) {
			Entitlements dashList = new Entitlements();
			dashList.setMenu(dashboard);
			dashList.setName(ent1);
			dashEntList.add(dashList);
		}
		if (!StringUtils.isEmpty(ent2)) {
			Entitlements dashList = new Entitlements();
			dashList.setMenu(dashboard);
			dashList.setName(ent2);
			dashEntList.add(dashList);
		}
		if (!StringUtils.isEmpty(ent3)) {
			Entitlements dashList = new Entitlements();
			dashList.setMenu(dashboard);
			dashList.setName(ent3);
			dashEntList.add(dashList);
		}
		if (!StringUtils.isEmpty(ent4)) {
			Entitlements dashList = new Entitlements();
			dashList.setMenu(dashboard);
			dashList.setName(ent4);
			dashEntList.add(dashList);
		}
		dashboard.setEntitlements(dashEntList);

		return dashboard;
	}

}
