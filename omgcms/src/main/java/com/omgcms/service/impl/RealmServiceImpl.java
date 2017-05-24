package com.omgcms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omgcms.service.RealmService;
import com.omgcms.web.security.DbAuthorizingRealm;

@Service
public class RealmServiceImpl implements RealmService{
	
	@Autowired
	private DbAuthorizingRealm realm;
	
	@Override
	public void clearShiroRealmCache() {
		realm.clearRealmCache();
	}
	
}
