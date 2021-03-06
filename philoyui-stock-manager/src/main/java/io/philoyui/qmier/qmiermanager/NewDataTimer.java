package io.philoyui.qmier.qmiermanager;

import cn.com.gome.cloud.openplatform.common.PageObject;
import cn.com.gome.cloud.openplatform.common.SearchFilter;
import io.philoyui.qmier.qmiermanager.entity.StockEntity;
import io.philoyui.qmier.qmiermanager.service.StockService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取股票最近数据
 */
@Component
public class NewDataTimer {

    @Autowired
    private StockService stockService;

    /**
     * 获取关注股票的最新价格，每隔5秒钟获取最新数据，记录到数据库中，同时发送时间
     *
     * 如果抓取时间为9:00,9:15,9:30,9:45,10:00，记录15分钟数据库中
     *
     * 如果抓取时间为9:00,9:30,10:00
     *
     * 每天15:00
     *
     * 如何选股，季报
     *
     * @param args
     */
    public void main(String[] args){

        SearchFilter searchFilter = SearchFilter.getPagedSearchFilter(0, 80);

        PageObject<StockEntity> financialProductEntities = stockService.paged(searchFilter);

        List<String> idList = new ArrayList<>();
        for (StockEntity stockEntity : financialProductEntities.getContent()) {
            idList.add(stockEntity.getSymbol());
        }

        String fetchUrl = "";

        Connection.Response response = null;
        try {
            response = Jsoup.connect(fetchUrl)
                    .header("Content-Type", "application/json")
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        response.body();

    }


}
