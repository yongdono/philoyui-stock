package io.philoyui.qmier.qmiermanager.service.tag.processor;

import io.philoyui.qmier.qmiermanager.entity.StockEntity;
import io.philoyui.qmier.qmiermanager.service.tag.EachTagMarker;
import io.philoyui.qmier.qmiermanager.service.tag.ProcessorContext;
import org.springframework.stereotype.Component;

@Component
public class VolumeTagMarker extends EachTagMarker {

    @Override
    public void processEachStock(ProcessorContext processorContext, StockEntity stockEntity, String prefix) {
        double[] volumeArray = processorContext.getVolumeDataArray();
        double[] closeArray = processorContext.getCloseDataArray();
        double[] openArray = processorContext.getOpenDataArray();
        if ( volumeArray[0] > volumeArray[1] * 3 && ((closeArray[0] - openArray[0])/openArray[0]<0.8 || (closeArray[0] - openArray[0])/openArray[0]> -0.8)){
            this.tagStocks(stockEntity.getSymbol(),"三倍量");
        }
    }

    @Override
    public void cleanTags() {
        this.deleteStocks("三倍量");
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
