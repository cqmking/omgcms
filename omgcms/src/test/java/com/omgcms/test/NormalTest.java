package com.omgcms.test;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.omgcms.bean.init.ImportResource;
import com.omgcms.bean.init.Permission;
import com.omgcms.bean.init.PermissionRoot;

public class NormalTest {

	@Test
	public void testPermissionInitializer() {
		
		try {
			URL classPathUri = this.getClass().getResource("/");
			String path = classPathUri.getPath() + "../classes/permissions/default.xml";
			System.out.println("path:" + path);

			File file = new File(path);
			JAXBContext jaxbC = JAXBContext.newInstance(PermissionRoot.class);
			Unmarshaller us = jaxbC.createUnmarshaller();
			PermissionRoot root = (PermissionRoot) us.unmarshal(file);

			List<Permission> permissions = root.getPermissions();
			for (Permission perm : permissions) {
				System.out.println(perm.toString());
			}
			
			List<ImportResource> resources = root.getResources();
			if (resources != null && resources.size() > 0) {
				for (ImportResource ires : resources) {
					System.out.println("import:"+ires.getResource());
				}
			}
			
			System.out.println("AAA:" + root.toString());

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
