package io.philoyui.qmier.qmiermanager.service.tag.processor;

import io.philoyui.qmier.qmiermanager.domain.StockHistoryData;
import io.philoyui.qmier.qmiermanager.entity.StockEntity;
import io.philoyui.qmier.qmiermanager.service.tag.EachTagMarker;
import io.philoyui.qmier.qmiermanager.utils.TalibUtils;
import org.springframework.stereotype.Component;

@Component
public class MaUpTagMarker extends EachTagMarker {

    @Override
    public void processEachStock(StockHistoryData stockHistoryData, StockEntity stockEntity, String prefix) {
        double[] closeArray = stockHistoryData.getCloseArray();

        double[] ma10Array = TalibUtils.ma(closeArray, 10);
        double[] ma20Array = TalibUtils.ma(closeArray, 20);
        double[] ma30Array = TalibUtils.ma(closeArray, 30);

        if ( ma10Array[0] > ma10Array[1] && ma20Array[0] > ma20Array[1] && ma30Array[0] > ma30Array[1]) {
            this.tagStocks(stockEntity.getSymbol(),"均线多头排列");
        }
        if ( ma20Array[0] > ma20Array[1] && ma20Array[1] < ma20Array[2]) {
            this.tagStocks(stockEntity.getSymbol(),"趋势上行");
        }

    }

    @Override
    public void cleanTags(String prefix) {
        this.deleteStocks("均线多头排列");
        this.deleteStocks("趋势上行");
    }

    @Override
    public boolean supportDate() {
        return true;
    }

    @Override
    public boolean supportWeek() {
        return false;
    }

    @Override
    public boolean supportMonth() {
        return false;
    }
}
