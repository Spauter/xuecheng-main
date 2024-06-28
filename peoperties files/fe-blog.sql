create database FE_BLOG;

create table blog
(
    blog_id     int auto_increment comment '博客主键'
        primary key,
    title       varchar(20)                         null comment '标题',
    content     text                                null comment '内容',
    description varchar(255)                        null comment '描述',
    type        varchar(10)                         null comment '创造类型（原创，转载，二创）',
    deleted     int       default 0                 not null comment '逻辑删除字段 0-未删除 1-删除',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    status      int                                 null comment '状态(1,草稿，2已发布，3已过时（已删除）)',
    user_id     int                                 null comment '作者id',
    field_id    int                                 null comment '领域id',
    media_id    int                                 null comment '封面',
    author      varchar(20)                         null comment '作者名字',
    clicks      int                                 null comment '点击数',
    audited     char(3)                             null comment '审核状态',
    key_words   varchar(120)                        null comment '关键字',
    constraint index_id
        unique (blog_id),
    constraint FIELD_ID
        foreign key (field_id) references field (field_id),
    constraint MEDIA_ID
        foreign key (media_id) references media (media_id)
)
    charset = utf8mb3
    row_format = DYNAMIC;

create index fieldID
    on blog (field_id);

INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('博客系统更新说明', '<h1 id="h1-fe-blog-system"><a name="fe-blog-system" class="reference-link"></a><span class="header-link octicon octicon-link"></span>fe-blog-system</h1><h2 id="h2--v1-0"><a name="博客系统更说明   -V1.0" class="reference-link"></a><span class="header-link octicon octicon-link"></span>博客系统更说明   -V1.0</h2><p>时间: 2023年12月16日20点32分</p>
<p>为了更好的提升用户体验，对该博客系统进行了一次更新。</p>
<h3 id="h3--"><a name="后端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>后端更改：</h3><ul>
<li>利用SpringBoot整合了该项目的后端，解决使用当前项目用Tomcat部署时由于war包命名导致找不到网页的问题</li><li>利用Mybatis Plus简化了该项目后端的部分Mapper层和Services层</li><li>整合了所有控制层代码，并简化了部分控制层代码</li><li>改写了有关控制层代码</li><li>将部分项目配置文件上传至Nacos配置中心统一管理</li><li>将部分Session存进Redis</li><li>此项目不再需要打成war包。</li><li>增加部分控制层异常处理部分</li></ul>
<h3 id="h3--"><a name="前端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>前端更改：</h3><ul>
<li>增加了用户注册功能，采用邮箱发送验证码的方式</li><li>解决了个人用户信息界面中显示的信息为admin的问题</li><li>解决了用户上传图像后，只能在个人用户信息界面显示图像而在其它后台只能显示默认头像的问题</li><li>解决了系统重启后用户头像重置为默认图像的问题</li><li>开放了图像上传大小的限制，上传的文件大小从原来的1MB扩大到现在的4MB</li><li>现在用户发布博客的作者不再是“admin”，而是自己的名字</li><li>现在用户只能编辑由自己发布的博客</li><li>在未登录的情况下将无法进入后台页面</li><li>用户无法删除部分默认图片</li></ul>
<p>———————————————————————————————————————————————————</p>
<h2 id="h2--v1-1"><a name="博客系统更说明   -V1.1" class="reference-link"></a><span class="header-link octicon octicon-link"></span>博客系统更说明   -V1.1</h2><p>时间: 2023年12月18日15点36分</p>
<p>此次更新，仅对博客评论部分做出修改</p>
<h3 id="h3--"><a name="后端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>后端更改：</h3><ul>
<li>更改了所有控制层的返回数据，并简化代码</li></ul>
<h3 id="h3--"><a name="前端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>前端更改：</h3><ul>
<li><p>主页博客每页博客数量由原来的8个减少为5个</p>
</li><li><p>现在评论博客必须登录后才能品论</p>
</li><li><p>现在评论后名字不再是游客名字而是用户名</p>
</li></ul>
<p>———————————————————————————————————————————————————</p>
<h2 id="h2--v1-2"><a name="博客系统更说明   -V1.2" class="reference-link"></a><span class="header-link octicon octicon-link"></span>博客系统更说明   -V1.2</h2><p>时间：2023年12月19日15点42分</p>
<p>此次更新修复了博客“分类”页面中已知问题</p>
<h3 id="h3--"><a name="后端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>后端更改：</h3><ul>
<li>无</li></ul>
<h3 id="h3--"><a name="前端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>前端更改：</h3><ul>
<li><p>修复了分类页面图片无法显示的问题</p>
</li><li><p>修复了分类页面显示的博客只能显示admin的问题，现在任何人发布的博客都能显示</p>
</li></ul>
<p>———————————————————————————————————————————————————</p>
<h2 id="h2--v1-3"><a name="博客系统更说明   -V1.3" class="reference-link"></a><span class="header-link octicon octicon-link"></span>博客系统更说明   -V1.3</h2><p>时间：2023年12月23日<br>此次更新，把该系统后端改成了微服务架构</p>
<h3 id="h3--"><a name="后端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>后端更改：</h3><ul>
<li>拆分了服务.blog和homepage合成blog;user和backuser统称user；field和tag合成category;media和comment合成ornament;页面暂时放在gayeway服务中<h3 id="h3--"><a name="前端更改：" class="reference-link"></a><span class="header-link octicon octicon-link"></span>前端更改：</h3></li><li>无<br>———————————————————————————————————————————————————<h2 id="h2-u524Du7AEFu6280u672Fu6808"><a name="前端技术栈" class="reference-link"></a><span class="header-link octicon octicon-link"></span>前端技术栈</h2></li></ul>
<p>HTML、CSS、JavaScript、sass、Less、Layui</p>
<h2 id="h2-u540Eu7AEFu6280u672Fu6808"><a name="后端技术栈" class="reference-link"></a><span class="header-link octicon octicon-link"></span>后端技术栈</h2><p>Java11+Maven3.6.3+Spring Boot+MyBatis Plus+DevTools+Jakarta Mail+MySQL8+STDOUT_LOGGING(Mybatis日志工具)+Nacos+Redis+Lombok+Junit+fastjson</p>
<h2 id="h2-u6D4Bu8BD5u5DE5u5177"><a name="测试工具" class="reference-link"></a><span class="header-link octicon octicon-link"></span>测试工具</h2><p>Apache Jmeter(<del>没有流量就自己创造流量</del>),Cpolar(内网穿透方便其他同学测试，访问速度比较慢。<del>毕竟是免费的东西，没钱整校园网和云服务器</del>。<br>)</p>
<h2 id="h2-u73AFu5883u914Du7F6E"><a name="环境配置" class="reference-link"></a><span class="header-link octicon octicon-link"></span>环境配置</h2><p>本次更新，需要使用java11运行环境，除此之外还需要Redis和Nacos而外的配置。</p>
<p>由于SpringBoot内置一个Tomcat服务器，故不需要而外配置Tomcat服务器</p>
<h2 id="h2-u4F7Fu7528u8BF4u660E"><a name="使用说明" class="reference-link"></a><span class="header-link octicon octicon-link"></span>使用说明</h2><p>除配置数据库外，还需要而外以下操作:</p>
<ol>
<li>Redis(下载链接：<a href="https://github.com/tporadowski/redis/releases" title="Redis Windos版下载">Redis Windos版下载</a>),下载完毕后即可打开</li><li>Nacos(使用文档<a href="https://nacos.io/zh-cn/docs/quick-start.html" title="Nacos 使用文档">Nacos 快速开始</a>)，下载完毕后将配置导入即可（记得修改数据库配置）</li></ol>
￥# fe-blog-system



## 博客系统更说明   -V1.0

时间: 2023年12月16日20点32分

为了更好的提升用户体验，对该博客系统进行了一次更新。

### 后端更改：

- 利用SpringBoot整合了该项目的后端，解决使用当前项目用Tomcat部署时由于war包命名导致找不到网页的问题
- 利用Mybatis Plus简化了该项目后端的部分Mapper层和Services层
- 整合了所有控制层代码，并简化了部分控制层代码
- 改写了有关控制层代码
- 将部分项目配置文件上传至Nacos配置中心统一管理
- 将部分Session存进Redis
- 此项目不再需要打成war包。
- 增加部分控制层异常处理部分

### 前端更改：

- 增加了用户注册功能，采用邮箱发送验证码的方式
- 解决了个人用户信息界面中显示的信息为admin的问题
- 解决了用户上传图像后，只能在个人用户信息界面显示图像而在其它后台只能显示默认头像的问题
- 解决了系统重启后用户头像重置为默认图像的问题
- 开放了图像上传大小的限制，上传的文件大小从原来的1MB扩大到现在的4MB
- 现在用户发布博客的作者不再是“admin"，而是自己的名字
- 现在用户只能编辑由自己发布的博客
- 在未登录的情况下将无法进入后台页面
- 用户无法删除部分默认图片



———————————————————————————————————————————————————

## 博客系统更说明   -V1.1

时间: 2023年12月18日15点36分

此次更新，仅对博客评论部分做出修改

### 后端更改：

- 更改了所有控制层的返回数据，并简化代码

### 前端更改：

- 主页博客每页博客数量由原来的8个减少为5个

- 现在评论博客必须登录后才能品论

- 现在评论后名字不再是游客名字而是用户名

———————————————————————————————————————————————————

## 博客系统更说明   -V1.2

时间：2023年12月19日15点42分

此次更新修复了博客“分类”页面中已知问题

### 后端更改：

- 无

### 前端更改：

- 修复了分类页面图片无法显示的问题

- 修复了分类页面显示的博客只能显示admin的问题，现在任何人发布的博客都能显示

———————————————————————————————————————————————————
## 博客系统更说明   -V1.3
时间：2023年12月23日
此次更新，把该系统后端改成了微服务架构
### 后端更改：
- 拆分了服务.blog和homepage合成blog;user和backuser统称user；field和tag合成category;media和comment合成ornament;页面暂时放在gayeway服务中
### 前端更改：
- 无
———————————————————————————————————————————————————
## 前端技术栈

HTML、CSS、JavaScript、sass、Less、Layui

## 后端技术栈

Java11+Maven3.6.3+Spring Boot+MyBatis Plus+DevTools+Jakarta Mail+MySQL8+STDOUT_LOGGING(Mybatis日志工具)+Nacos+Redis+Lombok+Junit+fastjson

## 测试工具

Apache Jmeter(~~没有流量就自己创造流量~~),Cpolar(内网穿透方便其他同学测试，访问速度比较慢。~~毕竟是免费的东西，没钱整校园网和云服务器~~。
)

## 环境配置

本次更新，需要使用java11运行环境，除此之外还需要Redis和Nacos而外的配置。

由于SpringBoot内置一个Tomcat服务器，故不需要而外配置Tomcat服务器

## 使用说明

除配置数据库外，还需要而外以下操作:

1. Redis(下载链接：[Redis Windos版下载](https://github.com/tporadowski/redis/releases "Redis Windos版下载")),下载完毕后即可打开
2. Nacos(使用文档[Nacos 快速开始](https://nacos.io/zh-cn/docs/quick-start.html "Nacos 使用文档"))，下载完毕后将配置导入即可（记得修改数据库配置）









', '这是关于此博客的更新说明 ', '原创', 0, '2023-12-12 16:55:58', '2024-01-17 14:23:18', 0, 11, 61, 64, 'Spauter', 131, '已通过', null);
INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('韬韬博客系统介绍', '<h1 id="h1-fe-blog-system-"><a name="fe-blog-system 博客系统" class="reference-link"></a><span class="header-link octicon octicon-link"></span>fe-blog-system 博客系统</h1><h4 id="h4-u4ECBu7ECD"><a name="介绍" class="reference-link"></a><span class="header-link octicon octicon-link"></span>介绍</h4><p>FE个人博客管理系统，后端结合Mybatis框架，前端结合Layui的一个JavaWeb项目</p>
<h3 id="h3-u8F6Fu4EF6u67B6u6784"><a name="软件架构" class="reference-link"></a><span class="header-link octicon octicon-link"></span>软件架构</h3><p>MVC三层架构</p>
<h3 id="h3-u524Du7AEFu6280u672Fu6808"><a name="前端技术栈" class="reference-link"></a><span class="header-link octicon octicon-link"></span>前端技术栈</h3><p>HTML、CSS、JavaScript、sass、Less、Layui</p>
<h3 id="h3-u540Eu7AEFu6280u672Fu6808"><a name="后端技术栈" class="reference-link"></a><span class="header-link octicon octicon-link"></span>后端技术栈</h3><p>Java11+Maven+Tomcat+MyBatis+MySQL8+STDOUT_LOGGING+git/gitee+Lombok+Junit+fastjson</p>
<h3 id="h3-u73AFu5883u914Du7F6E"><a name="环境配置" class="reference-link"></a><span class="header-link octicon octicon-link"></span>环境配置</h3><p>需要本地有Java8或Java11的JDK环境和maven3.5及以上的版本的环境，Tomcat7、8、9均可使用</p>
<h3 id="h3-u4F7Fu7528u8BF4u660E"><a name="使用说明" class="reference-link"></a><span class="header-link octicon octicon-link"></span>使用说明</h3><p>克隆或下载项目到本地后，配置Tomcat和数据库和数据库的连接源即可使用本博客系统。</p>
<p>本地数据库运行需要新建一个库，然后在其中执行本项目中的.sql的数据库脚本文件让数据表中存在在本地中</p>
<p>数据库的连接源配置在resource目录下的database.properties中</p>
<h3 id="h3-u53C2u4E0Eu8D21u732E"><a name="参与贡献" class="reference-link"></a><span class="header-link octicon octicon-link"></span>参与贡献</h3>￥#fe-blog-system 博客系统
####介绍
FE个人博客管理系统，后端结合Mybatis框架，前端结合Layui的一个JavaWeb项目


###软件架构
MVC三层架构

###前端技术栈

HTML、CSS、JavaScript、sass、Less、Layui


###后端技术栈

Java11+Maven+Tomcat+MyBatis+MySQL8+STDOUT_LOGGING+git/gitee+Lombok+Junit+fastjson


###环境配置

需要本地有Java8或Java11的JDK环境和maven3.5及以上的版本的环境，Tomcat7、8、9均可使用

###使用说明

克隆或下载项目到本地后，配置Tomcat和数据库和数据库的连接源即可使用本博客系统。

本地数据库运行需要新建一个库，然后在其中执行本项目中的.sql的数据库脚本文件让数据表中存在在本地中

数据库的连接源配置在resource目录下的database.properties中

###参与贡献



', '韬韬个人博客管理系统，后端结合Mybatis框架，前端结合Layui的一个JavaWeb项目', '原创', 0, '2021-08-14 11:29:17', '2024-01-17 14:23:18', 0, 11, 1, 64, 'Spauter', 333, '已通过', null);
INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('CSS+JS实现打字特效', '<h1 id="h1-u7279u6548u9884u89C8"><a name="特效预览" class="reference-link"></a><span class="header-link octicon octicon-link"></span>特效预览</h1><p><img src="images/media/dztx.gif" alt=""></p>
<h1 id="h1-u5236u4F5Cu8981u9886"><a name="制作要领" class="reference-link"></a><span class="header-link octicon octicon-link"></span>制作要领</h1><p>（1）光标闪烁动画的制作</p>
<p>（2）文字不停变换</p>
<p>（3）循环播放</p>
<h1 id="h1-u5F00u59CBu5236u4F5C"><a name="开始制作" class="reference-link"></a><span class="header-link octicon octicon-link"></span>开始制作</h1><h3 id="h3-1-"><a name="1.网页布局" class="reference-link"></a><span class="header-link octicon octicon-link"></span>1.网页布局</h3><p>首先，完成页面的基本结构和布局</p>
<pre><code class="lang-html">&lt;body&gt;
    &lt;div&gt;
        &lt;!-- 文本内容 --&gt;
        &lt;span class=&quot;text&quot;&gt;&lt;/span&gt;
        &lt;!-- 显示光标 --&gt;
        &lt;span class=&quot;line&quot;&gt;|&lt;/span&gt;
    &lt;/div&gt;
&lt;/body&gt;
```22

``` css
        * {
            margin: 0;
            padding: 0;
        }

        body {
            width: 100vw;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: radial-gradient(rgb(77, 193, 197), rgb(1, 141, 255))
        }

        .text {
            font-size: 40px;
            color: rgb(231, 216, 79);
        }

        .line {
            font-size: 40px;
        }
</code></pre>
<h3 id="h3-2-css-"><a name="2.css实现闪烁的光标" class="reference-link"></a><span class="header-link octicon octicon-link"></span>2.css实现闪烁的光标</h3><p>使用CSS3 <a href="https://github.com/keyframes" title="&#64;keyframes" class="at-link">@keyframes</a> 制作一个不断循环的闪烁动画来实现这个效果。</p>
<pre><code class="lang-html">        @keyframes twinkle {

            /* 注意不要使用display */
            from {
                opacity: 0;
            }

            to {
                opacity: 1;
            }
        }
</code></pre>
<p>然后为光标设置该动画</p>
<pre><code class="lang-css">        .line {
            font-size: 40px;
            animation: twinkle 0.6s linear infinite;
        }
</code></pre>
<h3 id="h3-3-js-"><a name="3.js实现文字变换的基本原理" class="reference-link"></a><span class="header-link octicon octicon-link"></span>3.js实现文字变换的基本原理</h3><p>​        如何实现文字的变换呢？它的基本原理实际上非常简单，利用定时器，我们让0.1s时出现第一个字，让0.2s时出现第二个字…依次类推~~~</p>
<pre><code class="lang-javascript">//获取相关元素节点
var text = document.getElementsByClassName(&#39;text&#39;)[0];
//设置文本内容
var word = &quot;每个不曾起舞的日子，都是对生命的辜负！---Kxs（误）&quot;;
var t = true;
//基本思路
text.innerHTML = &quot;我&quot;;
setTimeout(function(){
text.innerHTML = &quot;我是&quot;;
},300);
setTimeout(function(){
text.innerHTML = &quot;我是一&quot;;
},600);
</code></pre>
<h3 id="h3-4-"><a name="4.嵌套循环函数实现" class="reference-link"></a><span class="header-link octicon octicon-link"></span>4.嵌套循环函数实现</h3><p>明白了它实现的基本原理后，我们就可以使用循环来实现它了</p>
<pre><code class="lang-javascript">for(let i=0;i&lt;=word.length;i++){
    setTimeout(function(){
        text.innerHTML=word.substr(0,i);
},i*100)
}
</code></pre>
<p>以上就已经实现了一个一次性的打字效果了！</p>
<h3 id="h3-5-"><a name="5.如何循环播放？" class="reference-link"></a><span class="header-link octicon octicon-link"></span>5.如何循环播放？</h3><p>基本效果已经完成了，但是还不够完美，我们想让它实现无限循环！</p>
<p>起初我使用了递归，但却因为造成了无限递归而导致了js栈内存溢出，从而使网页完全无法加载了。因为还没有学视频中的async函数，我决定寻找另一种方法，在思考过后，最终决定使用定时器来解决无限循环播放！</p>
<p>具体思路为：</p>
<p>引入一个temp变量为ture，每调用一次就对temp进行一次取反。</p>
<p>接着将正着的效果代码和反着的效果代码封装到一个函数中，加入if判断，当temp为true时执行正着的代码，当temp为false执行反着的代码。</p>
<p>最后我们需要每隔一定的时间反复调用这个方法！</p>
<h3 id="h3-6-"><a name="6.完成升级" class="reference-link"></a><span class="header-link octicon octicon-link"></span>6.完成升级</h3><p>了解了如何实现循环播放后，我们使用js将它实现！</p>
<pre><code class="lang-javascript">        let temp=true;
        let setText = function (temp) {
            if (temp) {
                for (let i = 0; i &lt;= word.length; i++) {
                    setTimeout(function () {
                        text.innerHTML = word.substr(0, i);
                    }, i*100);
                }
            } else {
                for (let t = word.length ; t &gt;=0; t--) {
                    setTimeout(function () {
                        text.innerHTML = word.substr(0, t);
                    }, (L-t)*100);

                }
            }
        }
        setText(temp);
         setInterval(function(){
             setText(temp=!temp);
        },L*100+1000);
</code></pre>
<blockquote>
<p>引用视频：[『JS特效』15分钟两种方式实现不一样的打字机效果_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](</p>
</blockquote>
￥# 特效预览
![](images/media/dztx.gif)
# 制作要领

（1）光标闪烁动画的制作

（2）文字不停变换

（3）循环播放

# 开始制作

### 1.网页布局

首先，完成页面的基本结构和布局

``` html
<body>
    <div>
        <!-- 文本内容 -->
        <span class="text"></span>
        <!-- 显示光标 -->
        <span class="line">|</span>
    </div>
</body>
```22

``` css
        * {
            margin: 0;
            padding: 0;
        }

        body {
            width: 100vw;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background: radial-gradient(rgb(77, 193, 197), rgb(1, 141, 255))
        }

        .text {
            font-size: 40px;
            color: rgb(231, 216, 79);
        }

        .line {
            font-size: 40px;
        }
```



### 2.css实现闪烁的光标

使用CSS3 @keyframes 制作一个不断循环的闪烁动画来实现这个效果。

``` html
        @keyframes twinkle {

            /* 注意不要使用display */
            from {
                opacity: 0;
            }

            to {
                opacity: 1;
            }
        }
```

然后为光标设置该动画

```css
        .line {
            font-size: 40px;
            animation: twinkle 0.6s linear infinite;
        }
```



### 3.js实现文字变换的基本原理

​		如何实现文字的变换呢？它的基本原理实际上非常简单，利用定时器，我们让0.1s时出现第一个字，让0.2s时出现第二个字...依次类推~~~

``` javascript
//获取相关元素节点
var text = document.getElementsByClassName(\'text\')[0];
//设置文本内容
var word = "每个不曾起舞的日子，都是对生命的辜负！---Kxs（误）";
var t = true;
//基本思路
text.innerHTML = "我";
setTimeout(function(){
text.innerHTML = "我是";
},300);
setTimeout(function(){
text.innerHTML = "我是一";
},600);
```



### 4.嵌套循环函数实现

明白了它实现的基本原理后，我们就可以使用循环来实现它了

``` javascript
for(let i=0;i<=word.length;i++){
	setTimeout(function(){
		text.innerHTML=word.substr(0,i);
},i*100)
}
```



以上就已经实现了一个一次性的打字效果了！

### 5.如何循环播放？

基本效果已经完成了，但是还不够完美，我们想让它实现无限循环！

起初我使用了递归，但却因为造成了无限递归而导致了js栈内存溢出，从而使网页完全无法加载了。因为还没有学视频中的async函数，我决定寻找另一种方法，在思考过后，最终决定使用定时器来解决无限循环播放！

具体思路为：

引入一个temp变量为ture，每调用一次就对temp进行一次取反。

接着将正着的效果代码和反着的效果代码封装到一个函数中，加入if判断，当temp为true时执行正着的代码，当temp为false执行反着的代码。

最后我们需要每隔一定的时间反复调用这个方法！

### 6.完成升级

了解了如何实现循环播放后，我们使用js将它实现！

``` javascript
        let temp=true;
		let setText = function (temp) {
            if (temp) {
                for (let i = 0; i <= word.length; i++) {
                    setTimeout(function () {
                        text.innerHTML = word.substr(0, i);
                    }, i*100);
                }
            } else {
                for (let t = word.length ; t >=0; t--) {
                    setTimeout(function () {
                        text.innerHTML = word.substr(0, t);
                    }, (L-t)*100);

                }
            }
        }
        setText(temp);
         setInterval(function(){
             setText(temp=!temp);
        },L*100+1000);
```



> 引用视频：[『JS特效』15分钟两种方式实现不一样的打字机效果_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili](', '使用css+js制作炫酷的打字特效，涉及到了css3 @keyframe的动画效果，以及js操控文字的显示，并且可支持无限循环，但是由于本人还没有学过async，用自己简陋的思路实现，可能会有未知得到bug', '原创', 0, '2021-08-15 10:09:39', '2024-01-17 14:23:18', 3, 1, 60, 64, 'admin', 46, '已通过', null);
INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('Linux使用Yum安装Java开发环境', '<h1 id="h1-linux-yum-java-"><a name="Linux使用Yum安装Java开发环境" class="reference-link"></a><span class="header-link octicon octicon-link"></span>Linux使用Yum安装Java开发环境</h1><h2 id="h2-u524Du8A00"><a name="前言" class="reference-link"></a><span class="header-link octicon octicon-link"></span>前言</h2><p>linux上安装软件，可以用yum非常方便，不需要下载解压，也不需要配置环境变量,一个指令就能用yum安装java。</p>
<p>一般项目部署的时候,是一个.jar后缀的文件，此文件是是依赖于java环境的，所以本篇文章来介绍一下如何使用Yum在Linux系统(此文用CentOS7来举例)中安装Java开发环境.</p>
<h4 id="h4-u8D44u6E90u51C6u5907"><a name="资源准备" class="reference-link"></a><span class="header-link octicon octicon-link"></span>资源准备</h4><pre><code>Linux系统(CentOS7)
Yum 3.4.3
</code></pre><h2 id="h2-yum-"><a name="yum环境的安装" class="reference-link"></a><span class="header-link octicon octicon-link"></span>yum环境的安装</h2><h4 id="h4-1-yum-"><a name="1.yum介绍" class="reference-link"></a><span class="header-link octicon octicon-link"></span>1.yum介绍</h4><p>yum（全称为 Yellow dog Updater, Modified）是一个在Fedora和RedHat以及CentOS中的Shell前端软件包管理器。<br>基于RPM包管理，能够从指定的服务器自动下载RPM包并且安装，可以自动处理依赖性关系，并且一次安装所有依赖的软件包，无须繁琐地一次次下载、安装。</p>
<h4 id="h4-2-java"><a name="2.检查是否已经安装Java" class="reference-link"></a><span class="header-link octicon octicon-link"></span>2.检查是否已经安装Java</h4><pre><code class="lang-shell">rpm -qa | grep java
</code></pre>
<p>如果没有java环境的话，接着就去查找java-1.8.0的可以使用的安装包：</p>
<pre><code class="lang-shell">yum list | grep java-1.8.0-openjdk
</code></pre>
<p>结果</p>
<pre><code class="lang-shell">[root@yoyo ~]# yum list | grep java-1.8.0-openjdk
java-1.8.0-openjdk.i686                  1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk.x86_64                1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-accessibility.i686    1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-accessibility.x86_64  1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-accessibility-debug.i686
java-1.8.0-openjdk-accessibility-debug.x86_64
java-1.8.0-openjdk-debug.i686            1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-debug.x86_64          1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo.i686             1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo.x86_64           1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo-debug.i686       1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo-debug.x86_64     1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel.i686            1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel.x86_64          1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel-debug.i686      1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel-debug.x86_64    1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless.i686         1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless.x86_64       1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless-debug.i686   1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless-debug.x86_64 1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc.noarch        1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc-debug.noarch  1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc-zip.noarch    1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc-zip-debug.noarch
java-1.8.0-openjdk-src.i686              1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-src.x86_64            1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-src-debug.i686        1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-src-debug.x86_64      1:1.8.0.191.b12-1.el7_6       updates
</code></pre>
<p>安装java-1.8.0-openjdk所有的文件</p>
<pre><code class="lang-shell">yum -y install java-1.8.0-openjdk*
</code></pre>
<p>安装过程需要1-2分钟…,安装好之后会自动配置环境变量.<br>查看版本号：</p>
<pre><code class="lang-shell">java -version
</code></pre>
<p>结果</p>
<pre><code class="lang-shell">[root@yoyo ~]# java -version
openjdk version &quot;1.8.0_191&quot;
OpenJDK Runtime Environment (build 1.8.0_191-b12)
OpenJDK 64-Bit Server VM (build 25.191-b12, mixed mode)
[root@yoyo ~]#
</code></pre>
<p>如果出现版本号,如上所示,即Java环境安装完毕.</p>
<p><img src="images/media/bg.jpg" alt=""></p>
￥# Linux使用Yum安装Java开发环境

##前言
linux上安装软件，可以用yum非常方便，不需要下载解压，也不需要配置环境变量,一个指令就能用yum安装java。

一般项目部署的时候,是一个.jar后缀的文件，此文件是是依赖于java环境的，所以本篇文章来介绍一下如何使用Yum在Linux系统(此文用CentOS7来举例)中安装Java开发环境.
#### 资源准备
	Linux系统(CentOS7)
	Yum 3.4.3
##yum环境的安装
####1.yum介绍
yum（全称为 Yellow dog Updater, Modified）是一个在Fedora和RedHat以及CentOS中的Shell前端软件包管理器。
基于RPM包管理，能够从指定的服务器自动下载RPM包并且安装，可以自动处理依赖性关系，并且一次安装所有依赖的软件包，无须繁琐地一次次下载、安装。
#### 2.检查是否已经安装Java
```shell
rpm -qa | grep java
```
如果没有java环境的话，接着就去查找java-1.8.0的可以使用的安装包：
```shell
yum list | grep java-1.8.0-openjdk
```
结果
```shell
[root@yoyo ~]# yum list | grep java-1.8.0-openjdk
java-1.8.0-openjdk.i686                  1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk.x86_64                1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-accessibility.i686    1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-accessibility.x86_64  1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-accessibility-debug.i686
java-1.8.0-openjdk-accessibility-debug.x86_64
java-1.8.0-openjdk-debug.i686            1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-debug.x86_64          1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo.i686             1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo.x86_64           1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo-debug.i686       1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-demo-debug.x86_64     1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel.i686            1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel.x86_64          1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel-debug.i686      1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-devel-debug.x86_64    1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless.i686         1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless.x86_64       1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless-debug.i686   1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-headless-debug.x86_64 1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc.noarch        1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc-debug.noarch  1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc-zip.noarch    1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-javadoc-zip-debug.noarch
java-1.8.0-openjdk-src.i686              1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-src.x86_64            1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-src-debug.i686        1:1.8.0.191.b12-1.el7_6       updates  
java-1.8.0-openjdk-src-debug.x86_64      1:1.8.0.191.b12-1.el7_6       updates  
```
安装java-1.8.0-openjdk所有的文件
```shell
yum -y install java-1.8.0-openjdk*
```
安装过程需要1-2分钟...,安装好之后会自动配置环境变量.
查看版本号：
```shell
java -version
```
结果
```shell
[root@yoyo ~]# java -version
openjdk version "1.8.0_191"
OpenJDK Runtime Environment (build 1.8.0_191-b12)
OpenJDK 64-Bit Server VM (build 25.191-b12, mixed mode)
[root@yoyo ~]# 
```
如果出现版本号,如上所示,即Java环境安装完毕.

![](images/media/bg.jpg)



















', '在centos下配置Java环境', '原创', 0, '2021-08-16 10:07:44', '2024-01-17 14:23:18', 2, 1, 61, 64, 'admin', 8, '已通过', null);
INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('CSS实现毛玻璃效果', '<h2 id="h2-u6BDBu73BBu7483u6548u679Cu5236u4F5Cu603Bu7ED3"><a name="毛玻璃效果制作总结" class="reference-link"></a><span class="header-link octicon octicon-link"></span>毛玻璃效果制作总结</h2><h4 id="h4-1-"><a name="1.效果展示" class="reference-link"></a><span class="header-link octicon octicon-link"></span>1.效果展示</h4><p><img src="images/media/glass00.jpg" alt=""></p>
<h4 id="h4-2-"><a name="2.网页结构" class="reference-link"></a><span class="header-link octicon octicon-link"></span>2.网页结构</h4><pre><code class="lang-ht"> &lt;div class=&quot;banner&quot;&gt;
        &lt;div class=&quot;drop-shadow&quot;&gt;
            &lt;div class=&quot;glass&quot;&gt;&lt;/div&gt;
            &lt;span&gt;毛玻璃效果GLASS&lt;/span&gt;
            &lt;input type=&quot;file&quot;&gt;
        &lt;/div&gt;
    &lt;/div&gt;
</code></pre>
<p>banner,drop-shadow,glass为大小相同的div<br><img src="images/media/glass01.jpg" alt=""></p>
<p>banner层用来添加总的背景，drop-shadow用来添加毛玻璃的阴影滤镜，glass用来做毛玻璃</p>
<h4 id="h4-3-"><a name="3.原理与制作" class="reference-link"></a><span class="header-link octicon octicon-link"></span>3.原理与制作</h4><p>为banner于glass添加相同的背景图片，glass区域要保证于与banner区域的背景图相等，需要先将两张背景图叠在一起然后在进行裁切，所以glass与banner等大且具有相同的背景图，之后使用clip-path将glass进行裁切，然后为glass设置模糊滤镜，这样就形成了一个毛玻璃效果</p>
<pre><code class="lang-css">*{
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    html,body{
        width: 100vw;
        height: 100vh;

    }
    .banner{
        width: 100vw;
        height: 100vh;
        background: url(./02.jpg);
        background-position: center;
        background-size: cover;
        display: flex;
        justify-content: center;
        align-items: center;

    }


    .glass{
        width: 100%;
        height: 100%;
        background: url(./02.jpg);
        background-size: cover;
        background-position:center;
        clip-path: inset(200px 200px);
        filter: blur(20px);
        display: flex;
        justify-content: center;
        align-items: center; 

    } 
    span{
        position: absolute;
        color: white;
        font-size: 40px;
        letter-spacing: 0.75em;
        padding-left: 0.375em;
    }
</code></pre>
<p>最后为drop-shadow添加drop-shadow滤镜为毛玻璃添加阴影效果</p>
<pre><code class="lang-css">  .drop-shadow{
        height: 100%;
        width: 100%;
        filter:  drop-shadow(0px 20px 10px rgba(0, 0, 0, 0.5));  
        display: flex;
        justify-content: center;
        align-items: center;
    }
</code></pre>
<h4 id="h4-4-"><a name="4.总结" class="reference-link"></a><span class="header-link octicon octicon-link"></span>4.总结</h4><p>之前看到别人制作的毛玻璃效果非常漂亮，因此自己也想学习一下，毛玻璃效果中采用了clip-path裁切与filter滤镜，对我来说还比较陌生，没想到还可以使用滤镜添加阴影效果，以上就是毛玻璃效果的一种实现思路了</p>
￥## 毛玻璃效果制作总结

#### 1.效果展示
![](images/media/glass00.jpg)
#### 2.网页结构

``` ht
 <div class="banner">
        <div class="drop-shadow">
            <div class="glass"></div>
            <span>毛玻璃效果GLASS</span>
            <input type="file">
        </div>
    </div>
```

banner,drop-shadow,glass为大小相同的div
![](images/media/glass01.jpg)

banner层用来添加总的背景，drop-shadow用来添加毛玻璃的阴影滤镜，glass用来做毛玻璃

#### 3.原理与制作

为banner于glass添加相同的背景图片，glass区域要保证于与banner区域的背景图相等，需要先将两张背景图叠在一起然后在进行裁切，所以glass与banner等大且具有相同的背景图，之后使用clip-path将glass进行裁切，然后为glass设置模糊滤镜，这样就形成了一个毛玻璃效果

``` css
*{
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    html,body{
        width: 100vw;
        height: 100vh;
       
    }
    .banner{
        width: 100vw;
        height: 100vh;
        background: url(./02.jpg);
        background-position: center;
        background-size: cover;
        display: flex;
        justify-content: center;
        align-items: center;
        
    }


    .glass{
        width: 100%;
        height: 100%;
        background: url(./02.jpg);
        background-size: cover;
        background-position:center;
        clip-path: inset(200px 200px);
        filter: blur(20px);
        display: flex;
        justify-content: center;
        align-items: center; 
        
    } 
    span{
        position: absolute;
        color: white;
        font-size: 40px;
        letter-spacing: 0.75em;
        padding-left: 0.375em;
    }
```

最后为drop-shadow添加drop-shadow滤镜为毛玻璃添加阴影效果

```  css
  .drop-shadow{
        height: 100%;
        width: 100%;
        filter:  drop-shadow(0px 20px 10px rgba(0, 0, 0, 0.5));  
        display: flex;
        justify-content: center;
        align-items: center;
    }
```

#### 4.总结

之前看到别人制作的毛玻璃效果非常漂亮，因此自己也想学习一下，毛玻璃效果中采用了clip-path裁切与filter滤镜，对我来说还比较陌生，没想到还可以使用滤镜添加阴影效果，以上就是毛玻璃效果的一种实现思路了', '之前看到别人制作的毛玻璃效果非常漂亮，因此自己也想学习一下，毛玻璃效果中采用了clip-path裁切与filter滤镜，对我来说还比较陌生，没想到还可以使用滤镜添加阴影效果！', '原创', 0, '2021-08-16 12:37:03', '2024-01-17 14:23:18', 2, 1, 60, 64, 'admin', 4, '已通过', null);
INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('Java', '<p>Java是一门面向对象的编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程 [1]。<br>Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点 [2]。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等 [3]。</p>
￥Java是一门面向对象的编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程 [1]。
Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点 [2]。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等 [3]。', '这是java的介绍', '原创', 0, '2024-01-04 19:34:52', '2024-01-17 14:23:18', null, 11, 61, 64, 'Spauter', 1, '未通过', '语言,面向,编程,java,健壮性,移植性,应用,动态性,简单性,程序员,');
INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('1', '<p>Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。</p>
<p>Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程。</p>
<p>Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点 。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等 。</p>
￥Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。

Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程。

Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点 。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等 。', '1', '原创', 0, '2024-01-05 14:08:43', '2024-01-17 14:23:18', null, 63, 4, 64, 'Lin', 0, '未通过', '草泥马,编程,应用,泛型,面向,语言,特性,开发,平台,计算机,广泛,语言,编程,面向,java,健壮性,移植性,应用,动态性,简单性,程序员,');
INSERT INTO FE_BLOG.blog (title, content, description, type, deleted, create_time, update_time, status, user_id, field_id, media_id, author, clicks, audited, key_words) VALUES ('1', '<p>Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。</p>
<p>Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程。</p>
<p>Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点 。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等 。</p>
￥Java是一门面向对象编程语言，不仅吸收了C++语言的各种优点，还摒弃了C++里难以理解的多继承、指针等概念，因此Java语言具有功能强大和简单易用两个特征。

Java语言作为静态面向对象编程语言的代表，极好地实现了面向对象理论，允许程序员以优雅的思维方式进行复杂的编程。

Java具有简单性、面向对象、分布式、健壮性、安全性、平台独立与可移植性、多线程、动态性等特点 。Java可以编写桌面应用程序、Web应用程序、分布式系统和嵌入式系统应用程序等 。', '1', '原创', 0, '2024-01-05 15:37:51', '2024-01-17 14:23:18', null, 63, 1, 64, 'Lin', 0, '未通过', '语言,编程,面向,java,健壮性,移植性,应用,动态性,简单性,程序员,');


create table comment
(
    id          int auto_increment comment '主键'
        primary key,
    blog_id     int                                not null comment '博客id',
    content     varchar(120)                       null comment '评论内容',
    create_time datetime default CURRENT_TIMESTAMP null comment '评论时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '评论更新时间',
    account     varchar(120)                       not null comment '评论人员'
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (44, '登录后的测试评论', '2023-12-18 17:02:16', '2023-12-18 17:02:16', 'Spauter');
INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (44, '韬韬无敌', '2023-12-26 16:15:21', '2023-12-26 16:15:00', '123');
INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (1, '测试评论', '2023-12-18 18:16:28', '2023-12-18 18:16:28', 'Spauter');
INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (44, '韬韬永远爱着主人', '2023-12-19 11:54:44', '2023-12-19 11:54:44', '123');
INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (44, '测试评论', '2023-12-19 18:34:33', '2023-12-19 18:34:33', 'Spauter');
INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (56, '好酷炫', '2024-01-02 15:13:43', '2024-01-02 15:13:43', 'Bloduc');
INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (44, '评论测试zzz', '2024-01-04 15:58:26', '2024-01-04 15:58:26', '123');
INSERT INTO FE_BLOG.comment (blog_id, content, create_time, update_time, account) VALUES (44, '沙发', '2024-01-05 15:28:25', '2024-01-05 15:28:25', 'admin');

create table fe_media
(
    id          varchar(32) charset utf8mb4  not null comment '文件MD5值'
        primary key,
    file_name   varchar(120) charset utf8mb4 not null comment '文件名称',
    bucket      varchar(12) charset utf8mb4  not null comment '存储目录',
    file_path   varchar(120) charset utf8mb4 not null comment '存储路径',
    upload_time datetime                     not null comment '上传时间',
    user_id     int                          not null comment '账户id',
    update_time int                          not null comment '修改时间',
    file_size   bigint                       null comment '文件大小',
    constraint fe_media_user_user_id_fk
        foreign key (user_id) references user (user_id)
)
    comment '博客资源数据库' charset = utf8mb3
                             row_format = DYNAMIC;

create table field
(
    field_id int auto_increment
        primary key,
    name     varchar(10) not null,
    constraint field_field_id_uindex
        unique (field_id)
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO FE_BLOG.field (name) VALUES ('其它');
INSERT INTO FE_BLOG.field (name) VALUES ('javaSE');
INSERT INTO FE_BLOG.field (name) VALUES ('前端');
INSERT INTO FE_BLOG.field (name) VALUES ('后端');

create table homepage
(
    homepageid   int          not null
        primary key,
    description  varchar(255) null comment '描述',
    title        varchar(255) null comment '首页标题',
    welcome      varchar(255) null comment '欢迎语',
    banner       varchar(255) null comment '封面图片地址',
    announcement varchar(255) null,
    constraint homepage_homepageid_uindex
        unique (homepageid)
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO FE_BLOG.homepage (homepageid, description, title, welcome, banner, announcement) VALUES (1, '欢迎来到韬韬的个人博客', '韬韬的博客', '欢迎光临！', 'fm8.jpg', '大家好，本博客是由韬韬小组开发的可定制化个人博客系统');

create table media
(
    image    varchar(500) null,
    music    varchar(500) null,
    media_id int auto_increment
        primary key,
    constraint MEDIA_ID
        unique (media_id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

INSERT INTO FE_BLOG.media (image, music) VALUES ('default-banner.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm1.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm2.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm3.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm4.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm5.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm6.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm7.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm8.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm9.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm10.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('fm11.jpg', null);
INSERT INTO FE_BLOG.media (image, music) VALUES ('HT.jpg', null);

create table media_relation
(
    blog_id  int not null,
    media_id int not null,
    constraint MEDIA_ID
        unique (blog_id),
    constraint id3
        foreign key (blog_id) references blog (blog_id),
    constraint id4
        foreign key (media_id) references media (media_id)
)
    charset = utf8mb3
    row_format = DYNAMIC;

create table reply
(
    rid     int auto_increment comment '主键'
        primary key,
    id      int          null comment '对应的评论id',
    account varchar(15)  null comment '回复的账号',
    rdate   datetime     not null on update CURRENT_TIMESTAMP comment '回复时间',
    content varchar(120) null,
    constraint rc_cid
        foreign key (id) references comment (id)
            on update cascade on delete cascade,
    constraint reply_user_account_fk
        foreign key (account) references user (account)
)
    charset = utf8mb3
    row_format = DYNAMIC;

create index rc_account
    on reply (account);

INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (68, 'Bloduc', '2024-01-01 14:44:09', '韬韬的大名岂是你能叫的');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (68, '123', '2024-01-01 14:45:11', '韬韬是我儿子，咋不能叫');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (68, 'Spauter', '2024-01-01 14:45:51', '吃瓜');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (68, '123', '2024-01-01 14:46:31', '金韬测试');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (68, '123', '2024-01-01 14:46:43', '沙发');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (67, 'Bloduc', '2024-01-02 07:04:17', '沙发');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (67, 'admin', '2024-01-02 12:51:15', '沙发');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (79, '123', '2024-01-04 08:00:22', '评论测试zzz');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (68, 'Lin', '2024-01-05 06:24:24', '11
');
INSERT INTO FE_BLOG.reply (id, account, rdate, content) VALUES (68, 'admin', '2024-01-05 07:29:38', '沙发。');

create table tag
(
    tag_id int auto_increment comment '标签id'
        primary key,
    name   varchar(10) not null comment '标签名'
)
    charset = utf8mb3
    row_format = DYNAMIC;

INSERT INTO FE_BLOG.tag (name) VALUES ('javascript');
INSERT INTO FE_BLOG.tag (name) VALUES ('java');
INSERT INTO FE_BLOG.tag (name) VALUES ('后端');
INSERT INTO FE_BLOG.tag (name) VALUES ('css');
INSERT INTO FE_BLOG.tag (name) VALUES ('html');

create table tag_relation
(
    blog_id int           not null,
    tag_id  int           not null,
    deleted int default 0 not null,
    constraint id2
        foreign key (tag_id) references tag (tag_id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

create index id1
    on tag_relation (blog_id);

INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (58, 24, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (58, 25, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (1, 5, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (1, 5, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (44, 24, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (44, 24, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (44, 24, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (57, 5, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (100, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (102, 1, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (1, 5, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (1, 5, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (1, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (1, 5, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 1);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (104, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (105, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (106, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (107, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (108, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 1, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (103, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (109, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (110, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (111, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (112, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (44, 3, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (44, 5, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (44, 24, 0);
INSERT INTO FE_BLOG.tag_relation (blog_id, tag_id, deleted) VALUES (113, 3, 0);

create table user
(
    user_id     int auto_increment
        primary key,
    account     varchar(15)                         null comment '账户',
    password    varchar(255)                        null comment '密码',
    email       varchar(255)                        null comment '邮箱',
    nick        varchar(255)                        null comment '昵称',
    profile     varchar(255)                        null comment '简介',
    avatar      varchar(120)                        null comment '头像地址',
    deleted     int       default 0                 not null comment '逻辑删除字段 逻辑删除字段 0-未删除 1-删除',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default CURRENT_TIMESTAMP null comment '更新时间',
    sex         varchar(9)                          null comment '性别',
    birthday    timestamp                           null comment '生日',
    status      varchar(15)                         null comment '身份'
)
    charset = utf8mb3
    row_format = DYNAMIC;

create index account
    on user (account);

INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('admin', 'a', null, 'Fantastic Electricity', '生命不息，编码不止！', '/avatar.png', 0, '2021-08-06 19:19:05', '2023-12-07 15:13:21', '女', '2001-09-19 00:00:00', '超级管理');
INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('藤树test', '1234567', '', null, null, '/tao.jpg', 0, '2021-08-07 17:01:06', '2021-08-07 17:01:06', null, null, '管理');
INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('世纪糕Test1', '123321', null, '世纪糕', '111111', '/tao.jpg', 0, '2021-08-09 15:03:59', '2021-08-15 21:36:26', '男', '2001-09-20 00:00:00', '管理');
INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('123', '123', null, '韬韬无敌', '韬韬永远爱着主人', '/tao.jpg', 0, '2021-08-15 21:51:12', '2021-08-15 21:51:12', '女', '2023-12-01 00:00:00', '管理');
INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('Spauter', 'Spauter', '3230695439@qq.com', 'Bloduc Spauter', 'Touch fish long live!!!!', '/HT.jpg', 0, '2023-12-13 16:57:34', '2023-12-13 16:57:34', '女', '2013-07-17 00:00:00', '管理');
INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('Bloduc', 'Bloduc', null, 'Bloduc', 'null', '/tao.jpg', 0, '2023-12-29 15:29:29', '2023-12-29 15:29:29', '男', null, '注册用户');
INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('OldFu', '1234567', '3230695439@qq.com', 'OldFu', '系统的默认签名，送给每一个小可爱', '/tao.jpg', 0, '2024-01-03 23:08:11', '2024-01-03 23:08:11', null, null, '注册用户');
INSERT INTO FE_BLOG.user (account, password, email, nick, profile, avatar, deleted, create_time, update_time, sex, birthday, status) VALUES ('Lin', '123', '2209507443@qq.com', 'Lin', '系统的默认签名，送给每一个小可爱', '/tao.jpg', 0, '2024-01-05 14:06:14', '2024-01-05 14:06:14', null, null, '注册用户');
