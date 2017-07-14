<p align="center">
  微信小程序+微信管理后台+微信用户前台
</p>


**项目结构** 
```
hqc_mini_app 微信小程序相关文件

hqc_mp:
├─sql  项目SQL语句
│
├─config 配置信息
│
├─controller 控制器
│  ├─map 3D地图
│  ├─wx 微信前台控制器
│  ├─octopus 后台管理员控制器
│ 
├─service 业务逻辑接口
│  ├─impl 业务逻辑接口实现类
│
├─dao 数据访问接口
├
├─entity 数据持久化实体类
│
├─datasources 多数据源工具类
│
├─shiro Shiro验证框架
│ 
├─task Quartz定时任务
│ 
├─util 项目所用的的所有工具类
│  ├─FreeMarker 自定义FreeMarker标签
│ 
├─payforparking 停车付费相关接口WebService
│ 
├─weather 第三方天气查询相关接口WebService
│ 
├─ws 提供给小程序的相关接口WebService
│  
├──resources 
│  ├─mapper SQL对应的XML文件
│  
├──webapp
│  ├─map 3D地图
│  ├─statics 静态资源
│  ├─upload 上传文件
│  ├─WEB-INF
│	├─templates 页面FreeMarker模版

```
<br> 


**技术选型：** 
- 核心框架：Spring
- 安全框架：Apache Shiro
- 视图框架：Spring MVC
- 持久层框架：MyBatis MyBatis通用Mapper
- 缓存技术：Redis
- 定时器：Quartz
- 数据库连接池：Druid
- 日志管理：SLF4J、Log4j
- 模版技术：FreeMarker
- 页面交互：BootStrap、Layer等
<br> 


 **本地部署**
- 通过git下载源码
- 创建数据库hqc_mp，数据库编码为UTF-8
- 执行sql/hqc_mp.sql文件，初始化数据
- 修改db.properties，更新MySQL账号和密码

- 修改redis.properties,更改Redis连接信息

- Eclipse、IDEA部署Tomcat，则可启动项目
- 项目访问路径：http://localhost:8080/hqc_mp
- 账号密码：admin/admin
- 前台登录用户: 13647910412 123


启动说明:
	项目依赖mysql、Redis服务。