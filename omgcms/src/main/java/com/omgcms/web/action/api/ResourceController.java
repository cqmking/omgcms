package com.omgcms.web.action.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omgcms.model.core.ResourceAction;
import com.omgcms.model.core.ResourcePermission;
import com.omgcms.model.core.Role;
import com.omgcms.service.ResourceActionService;
import com.omgcms.service.ResourcePermissionService;
import com.omgcms.service.RoleService;
import com.omgcms.util.ModelConstants;
import com.omgcms.util.StringPool;
import com.omgcms.web.util.ParamUtil;

@Controller
@RequestMapping("/resource")
public class ResourceController {

	private Logger logger = LoggerFactory.getLogger(ResourceController.class);

	@Autowired
	private ResourceActionService resourceActionService;

	@Autowired
	ResourcePermissionService resourcePermissionService;

	@Autowired
	private RoleService roleService;

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/list/all-in-map", method = RequestMethod.GET)
	public Map<String, Object> getAllResourceActions() {

		logger.debug("Get all resourceActions.");

		List<ResourceAction> resourceActionList = resourceActionService.findAll();

		Map<String, Object> resourceActionsMap = new HashMap<String, Object>();

		for (ResourceAction resAction : resourceActionList) {

			String resourceType = resAction.getResourceType();
			List<ResourceAction> _resActionList = (List<ResourceAction>) resourceActionsMap.get(resourceType);

			if (_resActionList == null) {
				_resActionList = new ArrayList<ResourceAction>();
				resourceActionsMap.put(resourceType, _resActionList);
			}

			_resActionList.add(resAction);

		}

		return resourceActionsMap;
	}

	@ResponseBody
	@RequestMapping(value = "/list/resourceName", method = RequestMethod.GET)
	public List<ResourceAction> getResourceActionsByNameAndType(@RequestParam(required = false) String resourceName) {

		logger.debug("resourceName:{}", resourceName);
		resourceName = ParamUtil.get(resourceName, StringPool.BLANK);
		List<ResourceAction> resourceActionList = resourceActionService.getByResourceName(resourceName);

		return resourceActionList;
	}

	@ResponseBody
	@RequestMapping(value = "/loadResourcePermission", method = RequestMethod.POST)
	public List<ResourcePermission> loadResourcePermission(@RequestParam Long roleId, @RequestParam String resourceName,
			@RequestParam(required = false) Long ownerId, @RequestParam(required = false) Long primaryKey) {
		logger.debug("loadResourcePermission resourceName:{}", resourceName);
		roleId = ParamUtil.get(roleId, 0L);
		resourceName = ParamUtil.get(resourceName, StringPool.BLANK);
		ownerId = ParamUtil.get(ownerId, ModelConstants.DEFAULT_USER_ID);
		primaryKey = ParamUtil.get(primaryKey, ModelConstants.DEFAULT_PRIMARY_KEY);

		Role role = roleService.getByRoleId(roleId);
		ResourcePermission resourcePermission = resourcePermissionService.getByResourceNameAndPrimaryKeyAndRoleAndOwnerId(resourceName,
				primaryKey, role, ownerId);

		List<ResourcePermission> list = new ArrayList<ResourcePermission>();

		if (resourcePermission != null) {
			list.add(resourcePermission);
		}

		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/addResourcePermissions", method = RequestMethod.POST)
	public List<ResourcePermission> addResourcePermissions(
			@RequestParam Long roleId, 
			@RequestParam String resActIds,
			@RequestParam(required = false) Long ownerId, 
			@RequestParam(required = false) Long primaryKey,
			@RequestParam(required = false) String resourceName) {

		logger.debug("addResourcePermissions is running, roleId:{}", roleId);

		roleId = ParamUtil.get(roleId, 0L);
		ownerId = ParamUtil.get(ownerId, ModelConstants.DEFAULT_USER_ID);
		primaryKey = ParamUtil.get(primaryKey, ModelConstants.DEFAULT_PRIMARY_KEY);
		resActIds = ParamUtil.get(resActIds, StringPool.BLANK);

		Role role = roleService.getByRoleId(roleId);
		// User currentUser = CmsUtil.getCurrentUser();

		String[] resIdsStr = resActIds.split(StringPool.COMMA);
		Long[] _resActionIds = new Long[resIdsStr.length];

		if (!StringUtils.isBlank(resActIds)) {
			for (int i = 0; i < resIdsStr.length; i++) {
				_resActionIds[i] = Long.valueOf(resIdsStr[i]);
			}
		}

		List<Long> resActIdList = Arrays.asList(_resActionIds);
		List<ResourceAction> resourceActionList = resourceActionService.findAll(resActIdList);

		Map<String, Long> resActMap = new HashMap<String, Long>();

		if (!CollectionUtils.isEmpty(resourceActionList)) {
			for (ResourceAction resAct : resourceActionList) {
				Long value = resActMap.get(resAct.getResourceName());
				if (value == null) {
					value = 0L;
				}
				value = value | resAct.getBitwiseValue();

				resActMap.put(resAct.getResourceName(), value);
			}
		} else {
			if (!StringUtils.isBlank(resourceName)){
				resActMap.put(resourceName, 0L);
			}
		}
		
		List<ResourcePermission> updateResPermissionList = new ArrayList<ResourcePermission>();

		for (Entry<String, Long> entry : resActMap.entrySet()) {
			String key = entry.getKey();
			Long value = entry.getValue();

			ResourcePermission rp = new ResourcePermission();

			rp.setRole(role);
			rp.setOwnerId(ownerId);
			rp.setPrimaryKey(primaryKey);
			rp.setActionIds(value);
			rp.setResourceName(key);

			updateResPermissionList.add(rp);
		}

		List<ResourcePermission> updateList = resourcePermissionService.saveOrUpdate(updateResPermissionList);

		return updateList;
	}

}
