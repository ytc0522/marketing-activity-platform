package org.example.marketing.activity.consumer.service;


import marketing.lottery.rpc.dto.ActionResult;
import org.example.marketing.activity.consumer.model.dto.TakeActivityReq;

public interface ActivityService {


    public ActionResult takeActivity(TakeActivityReq req);


}
