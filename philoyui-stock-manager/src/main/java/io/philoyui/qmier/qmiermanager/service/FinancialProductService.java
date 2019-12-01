package io.philoyui.qmier.qmiermanager.service;

import cn.com.gome.cloud.openplatform.service.GenericService;
import io.philoyui.qmier.qmiermanager.entity.FinancialProductEntity;

public interface FinancialProductService extends GenericService<FinancialProductEntity,Long> {

    boolean existsBySymbol(String symbol);

    Object findBySymbol(String symbol);

    void enable(Long id);

    void disable(Long id);
}
