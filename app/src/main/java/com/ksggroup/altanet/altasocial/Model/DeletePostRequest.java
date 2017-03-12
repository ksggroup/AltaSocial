package com.ksggroup.altanet.altasocial.Model;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.util.Hashtable;

public class DeletePostRequest  implements KvmSerializable {
	public DeletePostRequest(Long post_id) {
		this.post_id = post_id;
	}

	private Long post_id;

	public Long post_id() {
		return post_id;
	}

	public void setpost_id(Long post_id) {
		this.post_id = post_id;
	}

	@Override
	public Object getProperty(int i) {
		return post_id;
	}

	@Override
	public int getPropertyCount() {
		return 1;
	}

	@Override
	public void setProperty(int i, Object o) {
		this.post_id = Long.valueOf(o.toString());
	}

	@Override
	public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
		propertyInfo.setName("post_id");
		propertyInfo.setType(Long.class);
	}
}
