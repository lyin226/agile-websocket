agile-websocket
==
本项目旨在为大家，提供一个快速应用Netty技术栈的demo，让大家熟悉netty的常用使用方法，主要是使用webSocket实现了聊天的功能。

项目启动步骤：

1、下载项目源码

2、安装mysql，初始化数据库，sql脚本在sql文件夹下，启动redis服务

3、安装nginx，nginx的部分配置示例，分别指向到前端文件路径和后端的服务地址

location / {
            root   /Users/IdeaProjects/agile-webSocket/agile-webSocket-ui;
            index  login.html;
        }

location ^~// {
            proxy_pass   http://127.0.0.1:8001;
        }
        
4、项目中，主要是使用了spring boot与mybatis-plus进行业务处理，
登录只有简单的账号校验，实际生产环境需要整合进类似shiro的权限系统，
主要是应用netty实现聊天功能，分为单聊和群聊，前端页面是找的开源代码，略微简单。

5、本地访问:localhost:9000,使用聊天功能的时候，需要同时登录两个账号，
![登录页面](https://github.com/lyin226/agile-websocket/blob/master/img/login.jpg)
![test账号登录](https://github.com/lyin226/agile-websocket/blob/master/img/test.jpg)
![test1账号登录](https://github.com/lyin226/agile-websocket/blob/master/img/test1.jpg)
