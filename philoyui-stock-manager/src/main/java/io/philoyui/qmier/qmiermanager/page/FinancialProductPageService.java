package io.philoyui.qmier.qmiermanager.page;

import cn.com.gome.cloud.openplatform.common.PageObject;
import cn.com.gome.cloud.openplatform.common.SearchFilter;
import cn.com.gome.page.button.batch.CreateOperation;
import cn.com.gome.page.button.column.DeleteOperation;
import cn.com.gome.page.button.column.EditOperation;
import cn.com.gome.page.core.PageConfig;
import cn.com.gome.page.core.PageContext;
import cn.com.gome.page.core.PageService;
import cn.com.gome.page.field.DateFieldDefinition;
import cn.com.gome.page.field.DomainLongFieldDefinition;
import cn.com.gome.page.field.LongFieldDefinition;
import cn.com.gome.page.field.StringFieldDefinition;
import io.philoyui.qmier.qmiermanager.entity.FinancialProductEntity;
import io.philoyui.qmier.qmiermanager.service.FinancialProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialProductPageService extends PageService<FinancialProductEntity,Long> {

    @Autowired
    private FinancialProductService financialProductService;

    @Autowired
    private FinancialMarketPageService financialMarketPageService;

    @Override
    public PageObject<FinancialProductEntity> paged(SearchFilter searchFilter) {
        return financialProductService.paged(searchFilter);
    }

    @Override
    protected PageConfig initializePageConfig(PageContext pageContext) {
        PageConfig pageConfig = new PageConfig(pageContext)
                .withDomainName("financial_product")
                .withDomainClass(FinancialProductEntity.class)
                .withDomainChineseName("金融产品")
                .withFieldDefinitions(
                        new LongFieldDefinition("id", "ID"),
                        new StringFieldDefinition("symbol", "标识码"),
                        new DomainLongFieldDefinition("marketId", "交易所",financialMarketPageService),
                        new StringFieldDefinition("code", "代码"),
                        new StringFieldDefinition("name", "名称"),
                        new DateFieldDefinition("modifyTime", "修改时间")
                )
                .withTableColumnDefinitions(
                        "symbol_15",
                        "code_15",
                        "name_15",
                        "marketId_15",
                        "modifyTime_15",
                        "#operation_25"
                )
                .withFilterDefinitions(
                    "symbol",
                    "code",
                    "name",
                    "marketId"
                )
                .withSortDefinitions(
                    "modifyTime_desc","modifyTime_asc"
                )
                .withTableAction(
                        new CreateOperation()
                )
                .withColumnAction(
                        new EditOperation(),
                        new DeleteOperation()
                )
                .withFormItemDefinition(
                        "symbol_rw",
                        "code_rw",
                        "name_rw",
                        "marketId_rw",
                        "modifyTime_rw"
                );
        return pageConfig;
    }

    @Override
    public FinancialProductEntity get(String id) {
        return financialProductService.get(Long.parseLong(id));
    }

    @Override
    public FinancialProductEntity get(SearchFilter searchFilter) {
        return financialProductService.get(searchFilter);
    }

    @Override
    public void saveOrUpdate(FinancialProductEntity financialProduct) {
        financialProductService.insert(financialProduct);
    }

    @Override
    public void delete(FinancialProductEntity financialProduct) {
        financialProductService.delete(financialProduct.getId());
    }
}
