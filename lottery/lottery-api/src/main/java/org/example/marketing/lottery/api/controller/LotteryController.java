package org.example.marketing.lottery.api.controller;


import org.example.marketing.common.ActionResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lottery")
@RestController
public class LotteryController {


    @PostMapping("/draw")
    public ActionResult draw() {

        return null;
    }






}
