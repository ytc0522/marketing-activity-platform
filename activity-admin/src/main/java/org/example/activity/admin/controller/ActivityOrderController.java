package org.example.activity.admin.controller;

import org.example.activity.admin.vo.ActivityOrderVo;
import org.example.marketing.common.ActionResult;
import org.example.marketing.common.dto.ActivityOrderDto;
import org.example.marketing.common.rpc.order.ActivityOrderServiceFacade;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/activity/order")
public class ActivityOrderController {

    @Resource
    private ActivityOrderServiceFacade orderServiceFacade;

    @PostMapping("/{orderId}")
    public ActionResult<ActivityOrderVo> getByOrderId(@PathVariable("orderId") String orderId) {
        ActivityOrderDto orderDto = orderServiceFacade.getByOrderId(orderId);
        return ActionResult.success(orderDto);
    }


}
