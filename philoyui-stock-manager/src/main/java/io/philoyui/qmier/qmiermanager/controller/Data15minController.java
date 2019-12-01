package io.philoyui.qmier.qmiermanager.controller;

import io.philoyui.qmier.qmiermanager.entity.Data15minEntity;
import io.philoyui.qmier.qmiermanager.entity.FinancialProductEntity;
import io.philoyui.qmier.qmiermanager.entity.enu.DataType;
import io.philoyui.qmier.qmiermanager.service.Data15minService;
import io.philoyui.qmier.qmiermanager.service.Data15minService;
import io.philoyui.qmier.qmiermanager.service.DataDownloadInterface;
import io.philoyui.qmier.qmiermanager.service.impl.DataDownloader;
import io.philoyui.qmier.qmiermanager.to.HistoryData;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/data_15min")
public class Data15minController {

    @Autowired
    private Data15minService data15minService;

    @Autowired
    private DataDownloader dataDownloader;

    @RequestMapping("/download_history")
    public ResponseEntity<String> downloadHistory() {
        dataDownloader.process(DataType.Min_15, new DataDownloadInterface() {
            @Override
            public void process(HistoryData[] historyDataArray, FinancialProductEntity financialProductEntity) {
                data15minService.deleteBySymbol(financialProductEntity.getSymbol());
                List<Data15minEntity> data15minEntityList = new ArrayList<>();
                for (HistoryData historyData : historyDataArray) {
                    Data15minEntity data15minEntity = new Data15minEntity();
                    data15minEntity.setSymbol(financialProductEntity.getSymbol());
                    data15minEntity.setDay(historyData.getDay());
                    data15minEntity.setDateString(DateFormatUtils.format(historyData.getDay(),"yyyy-MM-dd HH:mm:ss"));
                    data15minEntity.setOpen(Double.parseDouble(historyData.getOpen()));
                    data15minEntity.setHigh(Double.parseDouble(historyData.getHigh()));
                    data15minEntity.setLow(Double.parseDouble(historyData.getLow()));
                    data15minEntity.setClose(Double.parseDouble(historyData.getClose()));
                    data15minEntity.setVolume(Long.parseLong(historyData.getVolume()));
                    data15minEntityList.add(data15minEntity);
                }
                data15minService.insertAll(data15minEntityList);
            }
        });

        return ResponseEntity.ok("success");
    }

}