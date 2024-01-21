package marketing.support.rule.engine.service;

import marketing.support.rule.engine.model.req.OrderDiscount;
import marketing.support.rule.engine.model.req.OrderRequest;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDiscountService {

    @Autowired
    private KieContainer kieContainer;

    public OrderDiscount getDiscount(OrderRequest orderRequest) {
        OrderDiscount orderDiscount = new OrderDiscount();
        // 开启会话
        KieSession kieSession = kieContainer.newKieSession();
        // 设置折扣对象
        kieSession.setGlobal("orderDiscount", orderDiscount);
        // 设置订单对象
        kieSession.insert(orderRequest);
        // 触发规则
        kieSession.fireAllRules();
        //或者  通过规则过滤器实现只执行指定规则
        //kieSession.fireAllRules(new RuleNameEqualsAgendaFilter("Age based discount"));
        // 中止会话
        kieSession.dispose();
        return orderDiscount;
    }
}
