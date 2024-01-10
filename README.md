# :gift: 营销活动平台 - 基于微服务架构设计的高并发项目实践



>` 营销活动平台` 项目是一款互联网面向C端人群营销活动类系统，目前支持抽奖、秒杀、好友助力等各类营销活动。


⛳ **目录**

- [《抽奖系统 | 实战开发小册》，Go！](https://gitcode.net/KnowledgePlanet/Lottery/-/wikis/home) - `预计5章37节，更新中`
- [学习说明](https://gitcode.net/KnowledgePlanet/Lottery#-%E5%AD%A6%E4%B9%A0%E8%AF%B4%E6%98%8E)
- [工程目录](https://gitcode.net/KnowledgePlanet/Lottery#-%E5%B7%A5%E7%A8%8B%E5%88%97%E8%A1%A8) - `分布式服务工程`、`前端工程`、`运营后台`、`技术组件`、`测试工程`
- [环境配置 🤔 `重点注意使用SQL按照分支步骤更新，不要用最新的SQL对应最开始的代码`](https://gitcode.net/KnowledgePlanet/Lottery#-%E7%8E%AF%E5%A2%83%E9%85%8D%E7%BD%AE)
- [开发规范(分支、提交)](https://gitcode.net/KnowledgePlanet/Lottery/-/wikis/home#-%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83)
- [学习作业 🍁`(留下你的学习足迹，记录、分享、共同成长)`](https://gitcode.net/KnowledgePlanet/Lottery/-/tree/master#-%E5%AD%A6%E4%B9%A0%E4%BD%9C%E4%B8%9A)
- [联系作者](https://gitcode.net/KnowledgePlanet/Lottery#-%E8%81%94%E7%B3%BB%E4%BD%9C%E8%80%85)
- [感谢支持](https://gitcode.net/KnowledgePlanet/Lottery#-%E6%84%9F%E8%B0%A2%E6%94%AF%E6%8C%81)
- [CreativeAlliance 知识星球 • 技术联盟](https://gitcode.net/CreativeAlliance) - `为星球用户提供工程代码提交空间，方便技术交流。作业项目提交、分享学习代码、问题代码求助，这些你都可以创建仓库提交代码。当然你要有一些代码提交经验。`

## 📝 学习说明

在此项目中你会学习到互联网公司关于C端项目开发时候用到的一些，技术、架构、规范等内容。由于项目为实战类编程项目，在学习的过程中需要上手操作，小傅哥会把系统的搭建拉不同的分支列为每一个章节进行设计和实现并记录到开发日记中，读者在学习的过程中可以结合这部分内容边看文章边写代码实践。

- 技术：SpringBoot、Mybatis、Dubbo、MQ、Redis、Mysql、ELK、分库分表、Otter
- 架构：DDD 领域驱动设计、充血模型、设计模式
- 规范：分支提交规范、代码编写规范

## 💻 工程列表

| 序号 | 图标 | 名称 | 系统 | 作用 |
| :---: | :---: | ----- | ----- | ----- |
| 1 | <img src="https://gitcode.net/uploads/-/system/project/avatar/57512/%E6%88%91%E7%9A%84%E5%A5%96%E5%93%81.png" width="64px"> | 分布式核心功能服务系统 | [Lottery](https://gitcode.net/KnowledgePlanet/Lottery) | 提供抽奖业务领域功能，以分布式部署的方式提供 RPC 服务。 |
| 2 | <img src="https://gitcode.net/uploads/-/system/project/avatar/80935/API%E6%8E%A5%E5%8F%A3.png" width="64px"> | 网关API服务 | [Lottery-API](https://gitcode.net/KnowledgePlanet/Lottery-API) | 网关服务，提供；H5 页面抽奖、公众号开发回复消息抽奖。 |
| 3 | <img src="https://gitcode.net/uploads/-/system/project/avatar/79776/%E7%94%A8%E6%88%B7%E7%BB%84.png" width="64px"> | C端用户系统 | [lottery-front](https://gitcode.net/KnowledgePlanet/lottery-front) | 开发中，vue 前端页面 |
| 4 | <img src="https://gitcode.net/uploads/-/system/project/avatar/79714/%E8%BF%90%E8%90%A5%E9%A2%84%E6%9C%9F.png" width="64px"> | B端运营系统 | [Lottery-ERP](https://gitcode.net/KnowledgePlanet/Lottery-ERP) | 满足运营人员对于活动的查询、配置、修改、审核等操作。 |
| 5 | <img src="https://gitcode.net/uploads/-/system/project/avatar/67564/%E6%95%B0%E6%8D%AE%E5%BA%93.png" width="64px"> | 分库分表路由组件 | [db-router-spring-boot-starter](https://gitcode.net/KnowledgePlanet/db-router-spring-boot-starter) | **本项目依赖自研分库分表组件，需要下载后构建** 开发一个基于 HashMap 核心设计原理，使用哈希散列+扰动函数的方式，把数据散列到多个库表中的组件，并验证使用。 |
| 6 | <img src="https://gitcode.net/uploads/-/system/project/avatar/58903/%E6%B5%8B%E8%AF%95.png" width="64px"> | 测试验证系统 | [Lottery-Test](https://gitcode.net/KnowledgePlanet/Lottery-Test) | 用于测试验证RPC服务、系统功能调用的测试系统。 |

## 🎨 环境配置

- **技术栈项**：JDK1.8、Maven3.6.3、Mysql5.7(可升级配置)，SpringBoot、Mybatis、Dubbo 随POM版本
- **建表语句**：[doc/asserts/sql](https://gitcode.net/KnowledgePlanet/Lottery/-/blob/master/doc/assets/sql/lottery.sql) - `建议随非分支内sql版本走，因为需求不断迭代升级优化，直接使用最新的会遇到在各个分支下的代码运行问题`
- **代码仓库**：`2种使用方式`
    - 密码方式：登录的用户ID为 CSDN 个人中心的用户ID，[https://i.csdn.net/#/user-center/profile](https://i.csdn.net/#/user-center/profile) 密码为 CSDN 登录密码。如果没有密码或者忘记，可以在 CSDN 登录页找回密码。
    - SSH 秘钥免登录方式，设置：[https://gitcode.net/-/profile/keys](https://gitcode.net/-/profile/keys) 文档：[生成 SSH 密钥](https://gitcode.net/codechina/help-docs/-/wikis/docs/ssh#%E7%94%9F%E6%88%90-ssh-%E5%AF%86%E9%92%A5)
- **学习使用**：下载代码库后，切换本地分支到wiki中章节对应的分支，这样代码与章节内容是对应的，否则你在master看到的是全量代码。
- **下载依赖**：[db-router-spring-boot-starter](https://gitcode.net/KnowledgePlanet/db-router-spring-boot-starter) 本项目依赖自研分库分表组件，需要下载后构建

## 📐 开发规范

**分支命名**：日期_姓名首字母缩写_功能单词，如：`210804_xfg_buildFramework`

**提交规范**：`作者，type: desc` 如：`小傅哥，fix：修复查询用户信息逻辑问题` *参考Commit message 规范*

```java
# 主要type
feat:     增加新功能
fix:      修复bug

# 特殊type
docs:     只改动了文档相关的内容
style:    不影响代码含义的改动，例如去掉空格、改变缩进、增删分号
build:    构造工具的或者外部依赖的改动，例如webpack，npm
refactor: 代码重构时使用
revert:   执行git revert打印的message

# 暂不使用type
test:     添加测试或者修改现有测试
perf:     提高性能的改动
ci:       与CI（持续集成服务）有关的改动
chore:    不修改src或者test的其余修改，例如构建过程或辅助工具的变动
```

## 🐾 学习作业

### 1. 工程作业

- 作业空间：[https://gitcode.net/CreativeAlliance](https://gitcode.net/CreativeAlliance)
- 使用说明：为星球用户提供工程代码提交空间，你可以把抽奖系统的学习代码提交到空间中，`按照代码仓库名称标准，项目-星球用户编号-作者ID 例如：Lottery-1-xiaofuge`

|示意|
|---|
| ![](https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/CreativeAlliance.png) |


### 2. 文字作业

- 你可以在星球APP中提交`作业题目` | 网页提交：[https://t.zsxq.com/MvFYJe2](https://t.zsxq.com/MvFYJe2)

1. 今天的你学到了哪个章节？
2. 遇到什么问题？
3. 怎么解决的？
4. 掌握到了什么知识？

基于大家的学习反馈，小傅哥会在后续的直播中统一解决相关学习问题。加油，这趟车人人有收获！

注意️：按照作业提交频次和质量，小傅哥会组织一波奖品。**按照作业提交数量、质量、点赞、留言，综合评分，送技术图书等奖品。**

<img src="https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/job.jpg" width="414" height="891"/>

- 也可以提交 issue：[https://gitcode.net/KnowledgePlanet/Lottery/-/issues](https://gitcode.net/KnowledgePlanet/Lottery/-/issues)

## 💬 联系作者

- **加群交流**

  本群的宗旨是给大家提供一个良好的技术学习交流平台，所以杜绝一切广告！由于微信群人满 100 之后无法加入，请扫描下方二维码先添加作者 “小傅哥” 微信(fustack)，备注：`Spring学习加群`。

    <img src="https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/fustack.png" width="180" height="180"/>

- **公众号(bugstack虫洞栈)**

  沉淀、分享、成长，专注于原创专题案例，以最易学习编程的方式分享知识，让自己和他人都能有所收获。目前已完成的专题有；Netty4.x实战专题案例、用Java实现JVM、基于JavaAgent的全链路监控、手写RPC框架、DDD专题案例、源码分析等。

## 🎉 感谢支持

参与到项目开发学习过程中的小伙伴，可以通过PR提交个人对项目中学习过程中一些关于，代码优化、逻辑完善、问题修复等各项内容。当你的代码完整的提交以后，我会进行 `review` 通过以后进行合并以及记录你的提交信息。

<a href="#小傅哥">
    <img src="https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/xiaofuge.jpeg" style="border-radius:5px" width="50px">
</a>
<a href="#倩倩">
    <img src="https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/h_1.jpeg" style="border-radius:5px" width="50px">
</a>
<a href="#钢球">
    <img src="https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/h_2.jpeg" style="border-radius:5px" width="50px">
</a>
<a href="#豆豆">
    <img src="https://gitcode.net/KnowledgePlanet/Lottery/-/raw/master/doc/_media/h_3.jpeg" style="border-radius:5px" width="50px">
</a>
