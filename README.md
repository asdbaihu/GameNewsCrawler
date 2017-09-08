# 游戏资讯爬虫

------

基于[Gather Platform](https://github.com/gsh199449/spider) 进行个性化重构及二次开发。
<br />
<br />

## 更新日志：
- 20170907
1. 增加52PK网模板
2. 修复时间格式化器的部分bug（转为系统识别）
3. 线上测试

- 20170906
1. 增加优先爬取指定Xpath所在模块的链接
2. 增加批量创建定时及批量删除定时功能
3. 修复部分模板，优化抓取效率及效果

- 20170831
1. 修改爬取格式，添加图像及视频来源
2. 修复一些bug

- 20170830
1. 增加动态配置源
2. 设置抓取资讯最大失效时长

- 20170829
1. 添加游久网（http://uuu9.com ）模板
2. 修改时间抓取方式，默认当前时间作为缺省值
3. 增加工具-去除文本中的HTML标签

## 致谢
- [Gather Platform](https://github.com/gsh199449/spider): 数据采集与分析平台
- [WebMagic](http://webmagic.io/): 国产垂直爬虫框架
- [elasticsearch](https://github.com/elastic/elasticsearch): 开源、分布式、REST风格搜索引擎
- [HangLP](https://github.com/hankcs/HanLP): 优秀的汉语言处理包