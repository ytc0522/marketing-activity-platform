package org.example.marketing.common.rpc.order;

import org.example.marketing.common.dto.ActivityOrderDto;

public interface ActivityOrderServiceFacade {

    ActivityOrderDto getByOrderId(String orderId);


}
