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
  - 轻量级，动态加载外部脚本
  - 为了解决频繁load groovy脚本为Class从而导致方法区OOM问题。
  - 同时通过缓存脚本信息来避免每次执行脚本都需要重新编译而带来的性能消耗，保证脚本执行的高效。
  - 通过caffeine缓存脚本，定时拉去脚本并进行指纹比对，及时动态刷新本地缓存脚本。缓存中并且可以淘汰不常用的脚本。淘汰算法是什么？

> ` Groovy+Redis+Caffie 实现可自动刷新规则的规则引擎、通过缓存编译后的Groovy脚本提高性能。
> `

- 分库分表：
  - 如何划分？多少个分片
  -
- 分布式事务：
  - 抽奖活动中订单和库存的数据一致性如何保证？
    - 先生成用户活动订单到数据库中，然后发送MQ消息，消息中携带订单号。
    - 如果发送MQ失败了，直接抛出异常，回滚订单。
    - 如果发送MQ超时了，订单数据回滚了，但是MQ发送成功了，此时可以通过消费方根据订单号查询校验。
    - 消费者根据订单号查询订单，确认订单已成功生成，然后扣减数据库库存。
    - 扣减库存如果失败，重试即可，直到扣减库存成功。
    - 扣减成功的话，还需要发送一个延迟消息。

  - 秒杀活动和拼团活动中订单和库存的数据一致性如何保证？
    - 先生成用户活动订单到数据库中，然后发送MQ消息，消息中携带订单号。
    - 如果发送MQ失败了，直接抛出异常，回滚订单。
    - 如果发送MQ超时了，订单数据回滚了，但是MQ发送成功了，此时可以通过消费方根据订单号查询校验。
    - 消费者有两个，第一个是用来扣减活动库存，第二个是用来生成总的订单。
    - 消费者根据订单号查询订单，确认订单已成功生成，然后扣减数据库库存。
    - 扣减库存如果失败，重试即可，直到扣减库存成功。
    - 扣减成功的话，还需要发送一个延迟消息。

## 其他考点

- 缓存
  - 数据库和缓存的一致性如何保证？
  - 缓存穿透、击穿、雪崩问题如何解决？
- 性能
  - 性能监控：
    - 链路追踪
  - 性能测试结果：
- 高可用性
  - Redis高可用性：
  - RabbitMq的高可用性：

## 技术栈：

- 框架：SpringBoot、SpringCloud、Nacos、Dubbo、RabbitMQ、Redis、Mysql、Sharding-jdbc、Mybatis-Plus、Docker
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

主要对基本通用的的活动信息进行操作，比如用户活动资格校验、用户活动日志记录等、对活动的CRUD。

### 抽奖活动服务

抽奖流程：

- 用户参加活动，根据规则引擎校验用户是否具备参加活动资格。
- 根据抽奖活动计算用户的奖品。
- 扣减Redis库存，
- 抢库存成功的话，直接生成数据库订单 MQ异步扣减数据库库存。
- 因为为了保证重复消费下，库存不会被重复扣减，所以需要将当前库存放入到MQ消息内。利用CAS扣减数据库库存。

### 秒杀活动

秒杀流程：

- 用户参加活动，根据规则引擎校验用户是否具备参加活动资格。
- 扣减Redis库存
- 抢库存成功的话，直接生成数据库订单 MQ异步扣减数据库库存，发送的MQ消息中有 订单号、和当前Redis可用库存
- 因为为了保证重复消费下，库存不会被重复扣减，所以需要将当前库存放入到MQ消息内。利用CAS扣减数据库库存。
- 这里要判断订单是否超时未支付取消，如果是，那么这里就需要使用定时任务去扫描超时未支付的订单，利用CAS去恢复库存。

### 拼团活动服务

拼团流程：

- 商家创建拼团活动，用户发起拼团活动，生成一个拼团订单，又一个tuan_id
- 用户分享该拼团，其他用户点击链接，查看
- 用户下单购买商品，也生成一个拼团订单，相同的tuan_id。更新相同团id订单的数据。
- 当达到拼团人数时，发货。

## 风控服务

？？？

