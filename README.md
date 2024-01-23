# 营销活动平台 - 基于微服务架构设计的高并发项目实践

> ` 营销活动平台` 项目是一款互联网面向C端人群营销活动类系统，目前支持抽奖、秒杀、拼团等各类营销活动。

## 项目介绍

营销活动平台是从营销平台独立出来的系统，支持运营人员或商家创建、修改活动，用户参加活动、领取活动奖励。

## 项目亮点

- DDD 领域驱动设计：
    - application： 应用层
    - domain： 领域层
    - infrastructure： 基础层
    - api/rpc： 接口层

- 规则引擎：

> ` Goovy+Redis+Caffie 实现可自动刷新规则的规则引擎、通过缓存编译后的Groovy脚本提高性能。
> `
- 分布式事务：

> `
>
> `

- 性能
    - 监控
    -
- 高可用性
  -

## 技术栈：

- 框架：SpringBoot、SpringCloud、Nacos、Dubbo、RabbitMQ、Redis、Mysql、Sharding-jdbc、Mybatis-Plus
- 架构：微服务架构、DDD领域驱动设计

## 项目列表

- activity-base: 基础活动微服务
- activity-lottery: 抽奖活动微服务
- activity-seckill: 秒杀活动微服务
- activity-pintuan: 拼团活动微服务
- marketing-reward: 活动发奖微服务
- marketing-support: 活动工具支持模块
    - risk-control: 风控模块
    - rule-engine:  规则引擎
- marketing-user:    用户
    - user-merchant: 商户系统
    - user-operator: 运营系统
    - user-player:   用户系统

## 项目描述

### 基础活动服务

介绍：

### 抽奖活动服务

介绍：

### 拼团活动服务

介绍：


