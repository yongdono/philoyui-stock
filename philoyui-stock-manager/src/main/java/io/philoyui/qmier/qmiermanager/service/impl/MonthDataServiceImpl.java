package io.philoyui.qmier.qmiermanager.service.impl;

import cn.com.gome.cloud.openplatform.common.Order;
import cn.com.gome.cloud.openplatform.common.PageObject;
import cn.com.gome.cloud.openplatform.common.Restrictions;
import cn.com.gome.cloud.openplatform.common.SearchFilter;
import cn.com.gome.cloud.openplatform.repository.GenericDao;
import cn.com.gome.cloud.openplatform.service.impl.GenericServiceImpl;
import io.philoyui.qmier.qmiermanager.dao.DataMonthDao;
import io.philoyui.qmier.qmiermanager.domain.StockHistoryData;
import io.philoyui.qmier.qmiermanager.entity.DataDayEntity;
import io.philoyui.qmier.qmiermanager.entity.DataMonthEntity;
import io.philoyui.qmier.qmiermanager.entity.StockEntity;
import io.philoyui.qmier.qmiermanager.entity.enu.TaskType;
import io.philoyui.qmier.qmiermanager.service.MonthDataService;
import io.philoyui.qmier.qmiermanager.to.KLineData;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MonthDataServiceImpl extends GenericServiceImpl<DataMonthEntity,Long> implements MonthDataService {

    @Autowired
    private DataMonthDao dataMonthDao;

    @Autowired
    private KLineDataDownloaderImpl dataDownloaderImpl;

    @Autowired
    private TaskTracerImpl taskTracerImpl;

    @Override
    protected GenericDao<DataMonthEntity, Long> getDao() {
        return dataMonthDao;
    }

    @Transactional
    @Override
    public void deleteBySymbol(String symbol) {
        dataMonthDao.deleteBySymbol(symbol);
    }

    @Override
    public void insertAll(List<DataMonthEntity> dataMonthEntityList) {
        dataMonthDao.saveAll(dataMonthEntityList);
    }

    @Override
    public void downloadHistory() {
        dataMonthDao.deleteAll();
        taskTracerImpl.trace(TaskType.Month, taskCounter -> dataDownloaderImpl.download(TaskType.Month, (financialProductEntity, historyDataArray) -> {
            List<DataMonthEntity> dataMonthEntityList = new ArrayList<>();
            for (KLineData KLineData : historyDataArray) {
                DataMonthEntity dataMonthEntity = new DataMonthEntity();
                dataMonthEntity.setSymbol(financialProductEntity.getSymbol());
                dataMonthEntity.setName(financialProductEntity.getName());

                dataMonthEntity.setDay(KLineData.getDay());
                dataMonthEntity.setDateString(DateFormatUtils.format(KLineData.getDay(),"yyyy-MM-dd HH:mm:ss"));
                dataMonthEntity.setOpen(Double.parseDouble(KLineData.getOpen()));
                dataMonthEntity.setHigh(Double.parseDouble(KLineData.getHigh()));
                dataMonthEntity.setLow(Double.parseDouble(KLineData.getLow()));
                dataMonthEntity.setClose(Double.parseDouble(KLineData.getClose()));
                dataMonthEntity.setVolume(Long.parseLong(KLineData.getVolume()));
                dataMonthEntity.setRecordTime(new Date());
                dataMonthEntityList.add(dataMonthEntity);
            }
            insertAll(dataMonthEntityList);
            taskCounter.increase();
        }));

    }

    @Override
    public StockHistoryData findStockHistoryData(StockEntity stockEntity) {
        SearchFilter pagedSearchFilter = SearchFilter.getPagedSearchFilter(0, 160);
        pagedSearchFilter.add(Restrictions.eq("symbol",stockEntity.getSymbol()));
        pagedSearchFilter.add(Order.desc("day"));
        PageObject<DataMonthEntity> pageObjects = this.paged(pagedSearchFilter);

        StockHistoryData stockHistoryData = new StockHistoryData();
        stockHistoryData.setLowArray(pageObjects.getContent().stream().mapToDouble(DataMonthEntity::getLow).toArray());
        stockHistoryData.setHighArray(pageObjects.getContent().stream().mapToDouble(DataMonthEntity::getHigh).toArray());
        stockHistoryData.setCloseArray(pageObjects.getContent().stream().mapToDouble(DataMonthEntity::getClose).toArray());
        stockHistoryData.setOpenArray(pageObjects.getContent().stream().mapToDouble(DataMonthEntity::getOpen).toArray());
        stockHistoryData.setVolumeArray(pageObjects.getContent().stream().mapToDouble(DataMonthEntity::getVolume).toArray());
        stockHistoryData.setStockData(pageObjects.getContent().toArray(new DataMonthEntity[0]));
        return stockHistoryData;
    }
}