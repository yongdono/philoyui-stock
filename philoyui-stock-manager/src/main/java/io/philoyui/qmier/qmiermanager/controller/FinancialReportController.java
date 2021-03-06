package io.philoyui.qmier.qmiermanager.controller;

import cn.com.gome.cloud.openplatform.common.SearchFilter;
import io.philoyui.qmier.qmiermanager.entity.StockEntity;
import io.philoyui.qmier.qmiermanager.service.StockService;
import io.philoyui.qmier.qmiermanager.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/financial_report")
public class FinancialReportController {

    @Autowired
    private StockService stockService;

    @Autowired
    private FinancialReportService financialReportService;

    @RequestMapping("/download_history")
    public ResponseEntity<String> fetchHistory() throws IOException {

        List<StockEntity> stockLists = stockService.list(SearchFilter.getDefault());

        for (StockEntity stockEntity : stockLists) {

            try {
                financialReportService.downloadHistory(stockEntity);
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        return ResponseEntity.ok("success");
    }
}
