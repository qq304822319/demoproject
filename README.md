# 演示项目
   --------------------------------------------------@author yangk
__________________

> * 项目为SpringBoot框架,集成了如下技术:


> > 1. mybatis
	2. tk.mybatis
    3. github.pagehelper
    4. jpa
    5. redis
    6. mysql
    7. shiro
    9. activiti5
    9. lombok
    10. swagger2
    11. POI
    12. Junit + Mockito + Jecoco
                                        
> * 项目已完成功能:

> > * 用户模块:
> > > 1. 登录,注册token
      2. shiro验证token
      3. 注册shiro加密
      
              
> > * 数据字典:
> > > 1. 通用代码
      2. 自动编码

> > * activiti5流程:
> > > 1. 流程图设计

> * 项目运行必要条件

> > 1. mysql数据库实例
    2. redis-server

> * 其他功能
> > > 1. MD5加密
> > > 2. AES加密
> > > 3. 自定义token（json web token）












   Note--------------------------------------------
__________________

> * swagger
> > 1. url  http://localhost:8083/swagger-ui.html


> * redis
> > 1. 启动本地redis 
> > > 1. cd D:\workspace\redis\Redis           ---切换到redis安装目录下
> > > 2. redis-server.exe redis.windows.conf   ---启动redis
> > > 3. redis-cli.exe -h 127.0.0.1 -p 6379    ---启动cli