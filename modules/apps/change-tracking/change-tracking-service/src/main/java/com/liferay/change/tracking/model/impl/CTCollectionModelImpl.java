/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionModel;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the CTCollection service. Represents a row in the &quot;CTCollection&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>CTCollectionModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CTCollectionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionImpl
 * @generated
 */
@ProviderType
public class CTCollectionModelImpl
	extends BaseModelImpl<CTCollection> implements CTCollectionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ct collection model instance should use the <code>CTCollection</code> interface instead.
	 */
	public static final String TABLE_NAME = "CTCollection";

	public static final Object[][] TABLE_COLUMNS = {
		{"ctCollectionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("status", Types.INTEGER);
		TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CTCollection (ctCollectionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description VARCHAR(75) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CTCollection";

	public static final String ORDER_BY_JPQL =
		" ORDER BY ctCollection.createDate ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CTCollection.createDate ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.change.tracking.service.util.ServiceProps.get(
			"value.object.entity.cache.enabled.com.liferay.change.tracking.model.CTCollection"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.change.tracking.service.util.ServiceProps.get(
			"value.object.finder.cache.enabled.com.liferay.change.tracking.model.CTCollection"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.change.tracking.service.util.ServiceProps.get(
			"value.object.column.bitmask.enabled.com.liferay.change.tracking.model.CTCollection"),
		true);

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long NAME_COLUMN_BITMASK = 2L;

	public static final long CREATEDATE_COLUMN_BITMASK = 4L;

	public static final String MAPPING_TABLE_CTCOLLECTIONS_CTENTRIES_NAME =
		"CTCollections_CTEntries";

	public static final Object[][]
		MAPPING_TABLE_CTCOLLECTIONS_CTENTRIES_COLUMNS = {
			{"companyId", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
			{"ctEntryId", Types.BIGINT}
		};

	public static final String
		MAPPING_TABLE_CTCOLLECTIONS_CTENTRIES_SQL_CREATE =
			"create table CTCollections_CTEntries (companyId LONG not null,ctCollectionId LONG not null,ctEntryId LONG not null,primary key (ctCollectionId, ctEntryId))";

	public static final boolean FINDER_CACHE_ENABLED_CTCOLLECTIONS_CTENTRIES =
		GetterUtil.getBoolean(
			com.liferay.change.tracking.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.CTCollections_CTEntries"),
			true);

	public static final String
		MAPPING_TABLE_CTCOLLECTION_CTENTRYAGGREGATE_NAME =
			"CTCollection_CTEntryAggregate";

	public static final Object[][]
		MAPPING_TABLE_CTCOLLECTION_CTENTRYAGGREGATE_COLUMNS = {
			{"companyId", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
			{"ctEntryAggregateId", Types.BIGINT}
		};

	public static final String
		MAPPING_TABLE_CTCOLLECTION_CTENTRYAGGREGATE_SQL_CREATE =
			"create table CTCollection_CTEntryAggregate (companyId LONG not null,ctCollectionId LONG not null,ctEntryAggregateId LONG not null,primary key (ctCollectionId, ctEntryAggregateId))";

	public static final boolean
		FINDER_CACHE_ENABLED_CTCOLLECTION_CTENTRYAGGREGATE =
			GetterUtil.getBoolean(
				com.liferay.change.tracking.service.util.ServiceProps.get(
					"value.object.finder.cache.enabled.CTCollection_CTEntryAggregate"),
				true);

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.change.tracking.service.util.ServiceProps.get(
			"lock.expiration.time.com.liferay.change.tracking.model.CTCollection"));

	public CTCollectionModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _ctCollectionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCtCollectionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ctCollectionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CTCollection.class;
	}

	@Override
	public String getModelClassName() {
		return CTCollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CTCollection, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CTCollection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CTCollection, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((CTCollection)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CTCollection, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CTCollection, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CTCollection)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CTCollection, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CTCollection, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<CTCollection, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<CTCollection, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<CTCollection, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<CTCollection, Object>>();
		Map<String, BiConsumer<CTCollection, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<CTCollection, ?>>();

		attributeGetterFunctions.put(
			"ctCollectionId", CTCollection::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<CTCollection, Long>)CTCollection::setCtCollectionId);
		attributeGetterFunctions.put("companyId", CTCollection::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CTCollection, Long>)CTCollection::setCompanyId);
		attributeGetterFunctions.put("userId", CTCollection::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<CTCollection, Long>)CTCollection::setUserId);
		attributeGetterFunctions.put("userName", CTCollection::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CTCollection, String>)CTCollection::setUserName);
		attributeGetterFunctions.put("createDate", CTCollection::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CTCollection, Date>)CTCollection::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CTCollection::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CTCollection, Date>)CTCollection::setModifiedDate);
		attributeGetterFunctions.put("name", CTCollection::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<CTCollection, String>)CTCollection::setName);
		attributeGetterFunctions.put(
			"description", CTCollection::getDescription);
		attributeSetterBiConsumers.put(
			"description",
			(BiConsumer<CTCollection, String>)CTCollection::setDescription);
		attributeGetterFunctions.put("status", CTCollection::getStatus);
		attributeSetterBiConsumers.put(
			"status",
			(BiConsumer<CTCollection, Integer>)CTCollection::setStatus);
		attributeGetterFunctions.put(
			"statusByUserId", CTCollection::getStatusByUserId);
		attributeSetterBiConsumers.put(
			"statusByUserId",
			(BiConsumer<CTCollection, Long>)CTCollection::setStatusByUserId);
		attributeGetterFunctions.put(
			"statusByUserName", CTCollection::getStatusByUserName);
		attributeSetterBiConsumers.put(
			"statusByUserName",
			(BiConsumer<CTCollection, String>)
				CTCollection::setStatusByUserName);
		attributeGetterFunctions.put("statusDate", CTCollection::getStatusDate);
		attributeSetterBiConsumers.put(
			"statusDate",
			(BiConsumer<CTCollection, Date>)CTCollection::setStatusDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_columnBitmask |= NAME_COLUMN_BITMASK;

		if (_originalName == null) {
			_originalName = _name;
		}

		_name = name;
	}

	public String getOriginalName() {
		return GetterUtil.getString(_originalName);
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_status = status;
	}

	@Override
	public long getStatusByUserId() {
		return _statusByUserId;
	}

	@Override
	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	@Override
	public String getStatusByUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getStatusByUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
	}

	@Override
	public String getStatusByUserName() {
		if (_statusByUserName == null) {
			return "";
		}
		else {
			return _statusByUserName;
		}
	}

	@Override
	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	@Override
	public Date getStatusDate() {
		return _statusDate;
	}

	@Override
	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	@Override
	public boolean isApproved() {
		if (getStatus() == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDenied() {
		if (getStatus() == WorkflowConstants.STATUS_DENIED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isDraft() {
		if (getStatus() == WorkflowConstants.STATUS_DRAFT) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isExpired() {
		if (getStatus() == WorkflowConstants.STATUS_EXPIRED) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isInactive() {
		if (getStatus() == WorkflowConstants.STATUS_INACTIVE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isIncomplete() {
		if (getStatus() == WorkflowConstants.STATUS_INCOMPLETE) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isPending() {
		if (getStatus() == WorkflowConstants.STATUS_PENDING) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isScheduled() {
		if (getStatus() == WorkflowConstants.STATUS_SCHEDULED) {
			return true;
		}
		else {
			return false;
		}
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), CTCollection.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CTCollection toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (CTCollection)ProxyUtil.newProxyInstance(
				_classLoader, _escapedModelInterfaces,
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CTCollectionImpl ctCollectionImpl = new CTCollectionImpl();

		ctCollectionImpl.setCtCollectionId(getCtCollectionId());
		ctCollectionImpl.setCompanyId(getCompanyId());
		ctCollectionImpl.setUserId(getUserId());
		ctCollectionImpl.setUserName(getUserName());
		ctCollectionImpl.setCreateDate(getCreateDate());
		ctCollectionImpl.setModifiedDate(getModifiedDate());
		ctCollectionImpl.setName(getName());
		ctCollectionImpl.setDescription(getDescription());
		ctCollectionImpl.setStatus(getStatus());
		ctCollectionImpl.setStatusByUserId(getStatusByUserId());
		ctCollectionImpl.setStatusByUserName(getStatusByUserName());
		ctCollectionImpl.setStatusDate(getStatusDate());

		ctCollectionImpl.resetOriginalValues();

		return ctCollectionImpl;
	}

	@Override
	public int compareTo(CTCollection ctCollection) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), ctCollection.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTCollection)) {
			return false;
		}

		CTCollection ctCollection = (CTCollection)obj;

		long primaryKey = ctCollection.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		CTCollectionModelImpl ctCollectionModelImpl = this;

		ctCollectionModelImpl._originalCompanyId =
			ctCollectionModelImpl._companyId;

		ctCollectionModelImpl._setOriginalCompanyId = false;

		ctCollectionModelImpl._setModifiedDate = false;

		ctCollectionModelImpl._originalName = ctCollectionModelImpl._name;

		ctCollectionModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<CTCollection> toCacheModel() {
		CTCollectionCacheModel ctCollectionCacheModel =
			new CTCollectionCacheModel();

		ctCollectionCacheModel.ctCollectionId = getCtCollectionId();

		ctCollectionCacheModel.companyId = getCompanyId();

		ctCollectionCacheModel.userId = getUserId();

		ctCollectionCacheModel.userName = getUserName();

		String userName = ctCollectionCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			ctCollectionCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			ctCollectionCacheModel.createDate = createDate.getTime();
		}
		else {
			ctCollectionCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			ctCollectionCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			ctCollectionCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		ctCollectionCacheModel.name = getName();

		String name = ctCollectionCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			ctCollectionCacheModel.name = null;
		}

		ctCollectionCacheModel.description = getDescription();

		String description = ctCollectionCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			ctCollectionCacheModel.description = null;
		}

		ctCollectionCacheModel.status = getStatus();

		ctCollectionCacheModel.statusByUserId = getStatusByUserId();

		ctCollectionCacheModel.statusByUserName = getStatusByUserName();

		String statusByUserName = ctCollectionCacheModel.statusByUserName;

		if ((statusByUserName != null) && (statusByUserName.length() == 0)) {
			ctCollectionCacheModel.statusByUserName = null;
		}

		Date statusDate = getStatusDate();

		if (statusDate != null) {
			ctCollectionCacheModel.statusDate = statusDate.getTime();
		}
		else {
			ctCollectionCacheModel.statusDate = Long.MIN_VALUE;
		}

		return ctCollectionCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CTCollection, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CTCollection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CTCollection, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((CTCollection)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<CTCollection, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CTCollection, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CTCollection, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((CTCollection)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader =
		CTCollection.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
		CTCollection.class, ModelWrapper.class
	};

	private long _ctCollectionId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private String _originalName;
	private String _description;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;
	private long _columnBitmask;
	private CTCollection _escapedModel;

}