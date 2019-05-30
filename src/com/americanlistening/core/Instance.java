package com.americanlistening.core;

import java.lang.reflect.TypeVariable;
import java.util.List;

import com.americanlistening.dao.DAO;

public class Instance {

	private List<DAO<?>> daos;
	
	public void addDAO(DAO<?> dao) {
		if (!daos.contains(dao))
			daos.add(dao);
	}
	
	public DAO<?> getDAO(Class<?> generic){
		for (DAO<?> dao : daos) {
			for (TypeVariable<?> c : generic.getTypeParameters()) {
				if (c.getGenericDeclaration().getClass() == generic)
					return dao;
			}
		}
		return null;
	}
}
