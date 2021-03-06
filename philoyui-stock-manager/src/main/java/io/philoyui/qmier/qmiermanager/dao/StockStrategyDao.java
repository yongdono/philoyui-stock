package io.philoyui.qmier.qmiermanager.dao;

import cn.com.gome.cloud.openplatform.repository.GenericDao;
import io.philoyui.qmier.qmiermanager.entity.StockStrategyEntity;

public interface StockStrategyDao extends GenericDao<StockStrategyEntity,Long> {
    StockStrategyEntity findByIdentifier(String identifier);
}