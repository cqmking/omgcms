package com.omgcms.admin.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.omgcms.bean.NavigationItem;
import com.omgcms.exception.CmsExceptionConstants;
import com.omgcms.exception.CmsRuntimeException;
import com.omgcms.prop.config.NavigationMenuPropertyPlaceholderConfigurer;
import com.omgcms.util.StringPool;

public class MenusLoaderUtil {

	private static Logger logger = LoggerFactory.getLogger(MenusLoaderUtil.class);

	private static final String PARENT_NAV_PREFIX = "0;";

	private static List<NavigationItem> navigations = null;

	public static List<NavigationItem> getNavigationItemList() {

		if (!CollectionUtils.isEmpty(navigations)) {
			return navigations;
		}
		
		logger.debug("Admin navigations not initialized, start to initialize.");
		
		Map<String, Object> navsMap = NavigationMenuPropertyPlaceholderConfigurer.getCtxPropertiesMap();

		Set<Entry<String, Object>> entrySet = navsMap.entrySet();

		navigations = new ArrayList<NavigationItem>();

		for (Entry<String, Object> entry : entrySet) {

			String value = String.valueOf(entry.getValue());

			if (!StringUtils.isBlank(value) && value.startsWith(PARENT_NAV_PREFIX)) {

				String navString = String.valueOf(entry.getValue());
				String[] menuArray = navString.split(StringPool.SEMICOLON, -1);

				if (menuArray.length < 6) {
					logger.error("Invalid navigation setting in properties file.");
					throw new CmsRuntimeException(CmsExceptionConstants.INVALID_NAVIGATION);
				}

				NavigationItem navItem = getNavigationItemFromPropvalue(menuArray);

				loadSubNavigationsLoop(navItem, navsMap);

				navigations.add(navItem);

			}
		}

		sort(navigations);

		return navigations;

	}

	private static void loadSubNavigationsLoop(NavigationItem navItem, Map<String, Object> navsMap) {

		int parentCode = navItem.getId();
		String prefix = String.valueOf(parentCode).concat(StringPool.SEMICOLON);

		Set<Entry<String, Object>> entrySet = navsMap.entrySet();

		List<NavigationItem> subNavigations = new ArrayList<NavigationItem>();

		for (Entry<String, Object> entry : entrySet) {

			String value = String.valueOf(entry.getValue());

			if (!StringUtils.isBlank(value) && value.startsWith(prefix)) {

				String navString = String.valueOf(entry.getValue());
				String[] menuArray = navString.split(StringPool.SEMICOLON, -1);

				if (menuArray.length < 6) {
					logger.error("Invalid navigation setting in properties file. Prop key[{}]", entry.getKey());
					throw new CmsRuntimeException(CmsExceptionConstants.INVALID_NAVIGATION);
				}

				NavigationItem tempNavItem = getNavigationItemFromPropvalue(menuArray);

				loadSubNavigationsLoop(tempNavItem, navsMap);

				subNavigations.add(tempNavItem);

			}
		}

		sort(subNavigations);

		navItem.setSubNavigations(subNavigations);

	}

	private static NavigationItem getNavigationItemFromPropvalue(String[] propValueArray) {

		Integer parentId = Integer.valueOf(propValueArray[0]);
		Integer id = Integer.valueOf(propValueArray[1]);
		String url = String.valueOf(propValueArray[2]);
		String code = String.valueOf(propValueArray[3]);
		String target = String.valueOf(propValueArray[4]);
		String iconClass = String.valueOf(propValueArray[5]);

		return new NavigationItem(parentId, id, url, code, target, StringPool.BLANK, iconClass);

	}

	/**
	 * 连接列表排序，按照ID排序
	 * 
	 * @param links
	 *            连接列表
	 */
	private static void sort(List<NavigationItem> links) {

		Collections.sort(links, new Comparator<NavigationItem>() {

			public int compare(NavigationItem m1, NavigationItem m2) {

				int map1value = m1.getId();
				int map2value = m2.getId();

				return map1value - map2value;
			}
		});
	}
}
