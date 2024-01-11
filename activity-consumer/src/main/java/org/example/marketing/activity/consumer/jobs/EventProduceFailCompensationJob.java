package org.example.marketing.activity.consumer.jobs;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

/**
 * 事件发送失败 补偿任务
 */
@Component
public class EventProduceFailCompensationJob {


    @XxlJob(value = "testHandler")
    public ReturnT<String> testHandler(String param) {
        XxlJobLogger.log("testHandler 执行。。。。。");
        return ReturnT.SUCCESS;
    }

}
