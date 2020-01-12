package io.philoyui.qmier.qmiermanager.service.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.philoyui.qmier.qmiermanager.client.east.data.BigBuyData;
import io.philoyui.qmier.qmiermanager.client.east.response.BigBuyResponse;
import io.philoyui.qmier.qmiermanager.entity.MyStockEntity;
import io.philoyui.qmier.qmiermanager.entity.enu.MyStockType;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 大宗交易
 */
@Component
public class BigBuyFilter implements StockFilter{

    private Gson gson = new GsonBuilder().create();

    @Override
    public Set<String> filterSymbol(String param1, String param2, String param3) {

        String endData = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        String startData = DateFormatUtils.format(DateUtils.addDays(new Date(),-2), "yyyy-MM-dd");

        Set<String> stockSet = new HashSet<>();

        String fetchUrl = "http://dcfm.eastmoney.com/em_mutisvcexpandinterface/api/js/get?type=DZJYGGTJ&token=70f12f2f4f091e459a279469fe49eca5&cmd=&st=Cjeltszb&sr=-1&p=1&ps=500&js={pages:(tp),data:(x)}&filter=((TDATE%3E=^" + startData + "^%20and%20TDATE%3C=^" + endData + "^))&rt=52607034";
        try {
            Connection.Response response = Jsoup.connect(fetchUrl)
                    .header("Content-Type", "application/json")
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();
            String result = response.body().replaceAll("pages", "\"pages\"")
                    .replaceAll("data", "\"data\"");

            BigBuyResponse bigBuyResponse = gson.fromJson(result, BigBuyResponse.class);

            for (BigBuyData bigBuyData : bigBuyResponse.getData()) {

                stockSet.add(buildSymbol(bigBuyData.getSymbol()));

                MyStockEntity myStockEntity = new MyStockEntity();
                myStockEntity.setSymbol(bigBuyData.getSymbol());
                myStockEntity.setCreatedTime(new Date());
                myStockEntity.setEnable(true);
                myStockEntity.setMyStockType(MyStockType.BigBuy);

                if(bigBuyData.getDealAmount()>1000){
                    myStockEntity.setMyStockType(MyStockType.BigBuyHighVolume);
                }else if(bigBuyData.getPremiumDiscount()>5){
                    myStockEntity.setMyStockType(MyStockType.BigBuyHighPremium);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return stockSet;
    }

    private String buildSymbol(String code) {
        if(code.startsWith("6")){
            return "sh" + code;
        }else if(code.startsWith("0")){
            return "sz" + code;
        }else {
            return code;
        }
    }
}
