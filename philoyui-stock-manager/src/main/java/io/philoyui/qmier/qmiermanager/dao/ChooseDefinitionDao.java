package io.philoyui.qmier.qmiermanager.dao;

import cn.com.gome.cloud.openplatform.repository.GenericDao;
import io.philoyui.qmier.qmiermanager.entity.ChooseDefinitionEntity;

import java.util.List;

public interface ChooseDefinitionDao extends GenericDao<ChooseDefinitionEntity,Long> {
    List<ChooseDefinitionEntity> findByEnable(boolean enable);
}