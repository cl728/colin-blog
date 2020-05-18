## colin-blog
### 个人博客网 1.0.0（SpringBoot + Vue）
本站是基于b站开源教程 [SpringBoot开发一个小而美的个人博客详细教程](https://www.bilibili.com/video/BV1nE411r7TF?p=1) 的二次开发。
前台展示页面**新增留言板块**，后台管理页面**新增图片管理和消息管理**，并于自己的服务器上搭建 **FastDFS 文件管理系统**，用于存储发布博客上传的图片。

### 所用技术栈
- 前端：
	- semantic-ui框架：页面布局
	- JQuery：插件集成、动画效果
	- **Vue**：后台数据渲染
	- **axios**：前后端数据交互
- 后端：
	- SpringBoot 2.2.5.RELEASE
	- **MyBatis**
	- **通用 Mapper**：简化开发
	- **PageHelper**：分页插件
	- **FastDFS**：文件系统，存储发布博客上传的图片
- 数据库：
	- MySQL
