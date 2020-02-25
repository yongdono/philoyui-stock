package io.philoyui.qmier.qmiermanager.service;

import cn.com.gome.cloud.openplatform.service.GenericService;
import io.philoyui.qmier.qmiermanager.entity.StockStrategyEntity;
import io.philoyui.qmier.qmiermanager.entity.enu.IntervalType;

import java.util.List;

public interface StockStrategyService extends GenericService<StockStrategyEntity,Long> {

    void tagStock(StockStrategyEntity stockStrategyEntity);

    void enable(long id);

    void disable(long id);

    List<StockStrategyEntity> findAdd();

    List<StockStrategyEntity> findReduce();

    void dropStock(StockStrategyEntity stockStrategyEntity);

    List<StockStrategyEntity> findByIntervalType(IntervalType intervalType);

    void processWithMonthTimer();

    void processWithDayTimer();

    void processWithWeekTimer();
}