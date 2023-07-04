package com.cottonconnect.elearning.ELearning.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cottonconnect.elearning.ELearning.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
	List<Menu> findByIdIn(List<Long> menuIds);

	List<Menu> findByNameIn(List<String> menus);

	@Query(value = "SELECT menu.ID, menu.NAME, menu.disp_name, menu.identification FROM users u INNER JOIN role r ON r.ID = u.role_id INNER JOIN cc_role_menu_map rm ON rm.role_id = r.ID INNER JOIN cc_menu menu ON menu.ID = rm.menu_id WHERE u.user_id = :userName", nativeQuery = true)
	List<Object[]> getMenuByUser(String userName);

	@Query(value = "SELECT ent.name, menu.disp_name, menu.identification from cc_entitlements ent inner join cc_menu menu on menu.id = ent.menu WHERE menu.id in :menus", nativeQuery = true)
	List<Object[]> getEntitlementsByMenu(List<Long> menus);
	
	List<Menu> findByParentIdNullOrderBySeqAsc();
	
	List<Menu> findByParentIdOrderBySeqAsc(Long parentId);
	
	List<Menu> findByParentIdInOrderByParentIdAscSeqAsc(List<Long> parentId);
}
