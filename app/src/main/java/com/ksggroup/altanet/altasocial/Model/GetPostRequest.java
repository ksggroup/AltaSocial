package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class GetPostRequest implements KvmSerializable {
	
	private static final long serialVersionUID = 1L;
	private Long user_id;

	public GetPostRequest(Long user_id) {
		this.user_id = user_id;
	}

	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Override
	public Object getProperty(int i) {
		return this.user_id;
	}

	@Override
	public int getPropertyCount() {
		return 1;
	}

	@Override
	public void setProperty(int i, Object o) {
		this.user_id = Long.valueOf(o.toString());
	}

	@Override
	public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
		propertyInfo.setName("user_id");
		propertyInfo.setType(Long.class);
	}
}
