package com.omgcms.web.init;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.omgcms.admin.util.Config;
import com.omgcms.bean.init.ImportResource;
import com.omgcms.bean.init.Permission;
import com.omgcms.bean.init.PermissionRoot;
import com.omgcms.model.core.ResourceAction;
import com.omgcms.model.utils.ResourceActionConstants;
import com.omgcms.service.ResourceActionService;
import com.omgcms.util.StringPool;

/**
 * 
 * @author Freddy
 * @version create date: 2017年7月18日
 */
public class PermissionInitializer {

	private Logger logger = LoggerFactory.getLogger(PermissionInitializer.class);

	private static final String permissionConfigPath = "permission.config.path";

	/**
	 * 
	 * Init permission data
	 * 
	 * 1. Read data from XML 2. Update the data from database.
	 * 
	 * @throws JAXBException
	 * 
	 */
	public void initPermissions() {

		logger.debug("==============> Start to initialize permissions from xml config file.");

		PermissionRoot permissionRoot = readPermissionRoot();

		if (permissionRoot == null) {
			return;
		}

		List<Permission> allPermissionList = null;

		try {

			allPermissionList = getAllPermissionList(permissionRoot);
			saveAndUpdatePermissions(allPermissionList);

		} catch (JAXBException e) {
			logger.error("Can not read permissions from initial file." + e.toString());
			e.printStackTrace();
			return;
		}

		logger.debug("==============> Initialize permissions completed successful.");
	}

	protected void saveAndUpdatePermissions(List<Permission> permissions) {

		WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
		ResourceActionService resourceActionService = webAppContext.getBean(ResourceActionService.class);

		if (!CollectionUtils.isEmpty(permissions)) {

			for (Permission permission : permissions) {

				String resourceName = permission.getResourceName();
				String resourceType = permission.getType();
				List<String> actions = permission.getActions();

				if (resourceType == null) {
					resourceType = ResourceActionConstants.RESOURCE_TYPE_RESOURCE.toLowerCase();
				}else
				{
					resourceType = resourceType.toLowerCase();
				}

				if (CollectionUtils.isEmpty(actions)) {
					continue;
				}

				logger.debug("Start to process permission with resource name [{}], it has {} actions.", resourceName, actions.size());

				List<ResourceAction> resourceActionList = resourceActionService.getByResourceName(resourceName);

				List<String> newActions = new ArrayList<String>();

				boolean isActionExist = false;

				for (String action : actions) {

					for (ResourceAction resourceAction : resourceActionList) {

						if (resourceAction.getActionId().equals(action)) {
							// This action is already exist.
							isActionExist = true;
							break;
						}
					}

					if (!isActionExist) {
						// Current action need to be created.
						newActions.add(action);
					}

				}

				logger.debug("Permission with resource name[{}] has {} new actions will be created.", resourceName, newActions.size());

				long bitwiseValue = getMaxBitwisvalue(resourceActionList);

				for (String newActionId : newActions) {
					bitwiseValue = bitwiseValue << 1;
					ResourceAction resourceAction = new ResourceAction(resourceName, resourceType, newActionId, bitwiseValue);
					resourceActionService.save(resourceAction);
					logger.debug(resourceAction.toString());
				}

				resourceActionService.flush();

			}
		}

	}

	protected long getMaxBitwisvalue(List<ResourceAction> resourceActionList) {

		long maxValue = 0L;

		for (ResourceAction resourceAction : resourceActionList) {
			maxValue = resourceAction.getBitwiseValue() > maxValue ? resourceAction.getBitwiseValue() : maxValue;
		}

		return maxValue == 0 ? 1 : maxValue;
	}

	protected List<Permission> getAllPermissionList(PermissionRoot permissionRoot) throws JAXBException {

		List<ImportResource> resources = permissionRoot.getResources();
		List<PermissionRoot> importPermissionRoots = getImportPermissionRoots(resources);

		List<Permission> permissions = new ArrayList<Permission>();

		if (!CollectionUtils.isEmpty(permissionRoot.getPermissions())) {
			permissions.addAll(permissionRoot.getPermissions());
		}

		for (PermissionRoot pRoot : importPermissionRoots) {

			if (CollectionUtils.isEmpty(pRoot.getPermissions())) {
				continue;
			}
			
			permissions.addAll(pRoot.getPermissions());
		}

		return permissions;
	}

	protected List<PermissionRoot> getImportPermissionRoots(List<ImportResource> resources) throws JAXBException {

		if (CollectionUtils.isEmpty(resources)) {
			return Collections.emptyList();
		}

		List<PermissionRoot> prList = new ArrayList<PermissionRoot>();

		JAXBContext jaxbC = JAXBContext.newInstance(PermissionRoot.class);
		Unmarshaller us = jaxbC.createUnmarshaller();

		for (ImportResource importResource : resources) {
			String resourceFile = importResource.getResource();
			String classPath = this.getClass().getResource("/").getPath();
			String fileFullPath = StringPool.BLANK;

			if (resourceFile.startsWith("/")) {
				// From ClassPath
				fileFullPath = classPath + resourceFile;
			} else {
				// Relative path
				fileFullPath = classPath + Config.get(permissionConfigPath) + File.separator + resourceFile;
			}

			File file = new File(fileFullPath);
			if (!file.exists()) {
				logger.warn("Can not read permission resource file:{}, file not exist.", fileFullPath);
				continue;
			}

			logger.debug("Start to read perssmions from resource file:{}", resourceFile);

			PermissionRoot root = (PermissionRoot) us.unmarshal(file);
			prList.add(root);

		}

		return prList;

	}

	protected PermissionRoot readPermissionRoot() {

		try {

			URL classPathUri = this.getClass().getResource("/");
			String path = classPathUri.getPath() + Config.get(permissionConfigPath) + "/default.xml";

			logger.debug("Start to read permission initial file. File path: {}", path);

			File file = new File(path);
			if (!file.exists()) {
				return null;
			}

			JAXBContext jaxbC = JAXBContext.newInstance(PermissionRoot.class);
			Unmarshaller us = jaxbC.createUnmarshaller();
			PermissionRoot root = (PermissionRoot) us.unmarshal(file);

			return root;

		} catch (JAXBException e) {
			logger.error("Can not read permission initial file." + e.toString());
			e.printStackTrace();
		}

		return null;
	}

}
