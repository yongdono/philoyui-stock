package io.philoyui.qmier.qmiermanager.page;

import cn.com.gome.cloud.openplatform.common.PageObject;
import cn.com.gome.cloud.openplatform.common.SearchFilter;
import cn.com.gome.page.button.batch.ButtonStyle;
import cn.com.gome.page.button.batch.CreateOperation;
import cn.com.gome.page.button.batch.TableOperation;
import cn.com.gome.page.button.column.DeleteOperation;
import cn.com.gome.page.button.column.EditOperation;
import cn.com.gome.page.core.PageConfig;
import cn.com.gome.page.core.PageContext;
import cn.com.gome.page.core.PageService;
import cn.com.gome.page.field.*;
import io.philoyui.qmier.qmiermanager.entity.DataDayEntity;
import io.philoyui.qmier.qmiermanager.service.DayDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataDayPageService extends PageService<DataDayEntity,Long> {

    @Autowired
    private DayDataService dayDataService;

    @Autowired
    private StockPageService stockPageService;

    @Override
    public PageObject<DataDayEntity> paged(SearchFilter searchFilter) {
        return dayDataService.paged(searchFilter);
    }

    @Override
    protected PageConfig initializePageConfig(PageContext pageContext) {
        PageConfig pageConfig = new PageConfig(pageContext)
                .withDomainName("data_day")
                .withDomainClass(DataDayEntity.class)
                .withDomainChineseName("日数据")
                .withFieldDefinitions(
                        new LongFieldDefinition("id", "ID"),
                        new StringFieldDefinition("name", "股票名称"),
                        new StringFieldDefinition("symbol", "代码"),
                        new DateFieldDefinition("day", "时间"),
                        new StringFieldDefinition("dateString", "时间格式"),
                        new DoubleFieldDefinition("open", "开盘价"),
                        new DoubleFieldDefinition("high", "最高价"),
                        new DoubleFieldDefinition("low", "最低价"),
                        new DoubleFieldDefinition("close", "收盘价"),
                        new LongFieldDefinition("volume", "成交量"),
                        new DateFieldDefinition("recordTime","记录时间")

                )
                .withTableColumnDefinitions(
                        "name_12",
                        "symbol_12",
                        "day_12",
                        "open_8",
                        "high_8",
                        "low_8",
                        "close_8",
                        "volume_12",
                        "recordTime_10",
                        "#operation_10"
                )
                .withFilterDefinitions(
                    "symbol"
                )
                .withSortDefinitions(
                        "volume_desc","volume_asc",
                        "recordTime_desc","recordTime_asc",
                        "day_desc","day_asc"
                )
                .withTableAction(
                        new CreateOperation(),
                        new TableOperation("下载历史数据","download_history", ButtonStyle.Green),
                        new TableOperation("下载今日数据","download_today", ButtonStyle.Orange)

                )
                .withColumnAction(
                        new EditOperation(),
                        new DeleteOperation()
                )
                .withFormItemDefinition(
                        "symbol_rw",
                        "day_rw",
                        "dateString_rw",
                        "open_rw",
                        "high_rw",
                        "low_rw",
                        "close_rw",
                        "volume_rw"
                );
        return pageConfig;
    }

    @Override
    public DataDayEntity get(String id) {
        return dayDataService.get(Long.parseLong(id));
    }

    @Override
    public DataDayEntity get(SearchFilter searchFilter) {
        return dayDataService.get(searchFilter);
    }

    @Override
    public void saveOrUpdate(DataDayEntity dataDay) {
        dayDataService.insert(dataDay);
    }

    @Override
    public void delete(DataDayEntity dataDay) {
        dayDataService.delete(dataDay.getId());
    }
}

