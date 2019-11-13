package io.philoyui.qmier.qmiermanager;

import cn.com.gome.cloud.openplatform.generator.CodeTemplate;
import cn.com.gome.cloud.openplatform.generator.PageProjectInitializer;
import cn.com.gome.cloud.openplatform.generator.request.PageCodeRequest;
import com.google.common.collect.Lists;
import io.philoyui.qmier.qmiermanager.domain.DayData;

public class Main {


    /**
     * Dao,Service,
     * @param args
     */
    public static void main(String[] args){
        PageCodeRequest request = new PageCodeRequest();
        request.setAppName("gmos");
        request.setBasePackage("io.philoyui.qmier.qmiermanager");
        request.setCodeTemplates(CodeTemplate.Page);
        request.setEntityClasses(Lists.newArrayList(DayData.class));
        request.setBasePath("E:\\opencloud\\philoyui-stock\\philoyui-stock-manager\\src\\main\\java\\io\\philoyui\\qmier\\qmiermanager");
        new PageProjectInitializer().generateCode(request);

    }

}
