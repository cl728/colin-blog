## colin-blog
### 个人博客网（SpringBoot + Vue）
本站是基于b站开源教程 [SpringBoot开发一个小而美的个人博客](https://www.bilibili.com/video/BV1nE411r7TF?p=1) 详细教程的二次开发。
前台展示页面**新增留言板块、置顶博客、热门博客**，后台管理页面**新增图片管理和消息管理**，并于自己的服务器上搭建 **FastDFS 文件管理系统**，用于存储发布博客上传的图片。

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

### 版本迭代
- **1.0.0**
	- 前台：
		- 首页全部博客展示
		- 按分类展示博客
		- 按标签展示博客
		- 博客详情页、评论
		- 归档页
		- 关于本站页
	- 后台：
		- 博客管理、发布
		- 分类管理、新增
		- 标签管理、新增
		- 图片管理、上传
		- 评论管理
- **1.1.0**
	- 前台：
		- 新增留言板块
		- 将插值表达式的写法替换成了v-text并赋予静态默认值，防止前台数据加载慢时显示插值表达式的情况发生
	- 后台：
		- 新增留言管理（与评论管理合并为消息管理）
		- 管理员登录逻辑优化为前台MD5加密后再post请求，防止请求被拦截密码泄露的情况发生
- **1.2.0**
	- 前台：
		- 优化了日期格式展示，新增了置顶博客
	- 后台：
		- 博客管理页新增置顶按钮
  **1.3.0**
  	- 前台：
		- 增加了热门博客标签，当博客浏览量大于等于100时会标记为热门
		- 分类及标签的查询优化为按其下对应的博客文章数量排序
		- 修复了一些已知 bug 
