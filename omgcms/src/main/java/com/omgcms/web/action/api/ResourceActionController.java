package com.omgcms.web.action.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.omgcms.model.core.ResourceAction;
import com.omgcms.service.ResourceActionService;

@Controller
@RequestMapping("/resource")
public class ResourceActionController {

	private Logger logger = LoggerFactory.getLogger(ResourceActionController.class);

	@Autowired
	private ResourceActionService resourceActionService;

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/list/all-in-map", method = RequestMethod.GET)
	public Map<String, Object> getRoleList() {
		
		logger.debug("Get all resourceActions.");

		List<ResourceAction> resourceActionList = resourceActionService.findAll();
		
		Map<String, Object> resourceActionsMap = new HashMap<String, Object>();

		for (ResourceAction resAction : resourceActionList) {
			
			String resourceType = resAction.getResourceType();
			List<ResourceAction> _resActionList = (List<ResourceAction>) resourceActionsMap.get(resourceType);
			
			if(_resActionList==null){
				_resActionList = new ArrayList<ResourceAction>();
				resourceActionsMap.put(resourceType, _resActionList);
			}
			
			_resActionList.add(resAction);
			
		}
		
		return resourceActionsMap;
	}

}
