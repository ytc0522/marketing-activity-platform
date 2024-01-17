package marketing.activity.pintuan.consumer.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pintuan/consumer")
public class PintuanConsumerController {


    // 发起拼团
    /**
     * 根据用户ID、商品ID、活动ID 发起，支付完成后生成一个拼团订单
     */


    /**
     * 查看拼团详情
     * 根据tuanID 去找到对应的拼团订单
     *
     */

    // 参加拼团
    /**
     * 根据用户ID、tuanId，判断活动状态、团状态、用户是否已经参与过了，决定用户是否参与
     * 用户参加，就是生成一个团订单，只有支付成功后，订单才会生效，并更新其他订单的状态。
     * 如果过期未支付，更新团订单的人数，团订单的分片键是tuanId。
     */


}
