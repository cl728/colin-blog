package fun.donglin.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Code;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

public class MarkdownUtils {

    /**
     * markdown格式转换成HTML格式
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse( markdown );
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render( document );
    }

    /**
     * 增加扩展[标题锚点，表格生成]
     * Markdown转换成HTML
     *
     * @param markdown
     * @return
     */
    public static String markdownToHtmlExtensions(String markdown) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton( HeadingAnchorExtension.create() );
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList( TablesExtension.create() );
        Parser parser = Parser.builder()
                .extensions( tableExtension )
                .build();
        Node document = parser.parse( markdown );
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions( headingAnchorExtensions )
                .extensions( tableExtension )
                .attributeProviderFactory( new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider();
                    }
                } )
                .build();
        return renderer.render( document );
    }

    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put( "target", "_blank" );
            }
            if (node instanceof TableBlock) {
                attributes.put( "class", "ui celled table" );
            }
        }
    }


    public static void main(String[] args) {
        String markdown = "### **一、关于本站**\n" +
                "自从今年3月初学习了 **SpringBoot** 和** Vue.js** 以来，很想自己动手做一套项目，以巩固所学。在一次偶然的搜寻中，我在b站发现了全栈大佬 [@S丶m1lence丶](https://space.bilibili.com/155831986) 的一套 [springBoot开发一个小而美的个人博客详细教程](https://www.bilibili.com/video/BV1nE411r7TF?p=1)，便参照教程，开发了此站。在该套教程中，up主带你从0到1搭建一个属于自己的个人博客，喜欢记得**素质三连**哦。\n" +
                "\n" +
                "本站的页面设计与开发基本同教程一致（前端弱鸡在线卑微），但**后端开发与前台数据渲染部分几乎都由自己完成**。后端接口遵循 **Restful** 接口规范，通过 **HTTP Status Code** 描述请求产生的各种结果。\n" +
                "### **二、所用技术栈**\n" +
                "- 前端：\n" +
                "\t- semantic-ui框架：页面布局\n" +
                "\t- JQuery：插件集成、动画效果\n" +
                "\t- **Vue**：后台数据渲染\n" +
                "\t- **axios**：前后端数据交互\n" +
                "- 后端：\n" +
                "\t- SpringBoot 2.2.5.RELEASE\n" +
                "\t- **MyBatis**\n" +
                "\t- **通用 Mapper**：简化开发\n" +
                "\t- **PageHelper**：分页插件\n" +
                "\t- **FastDFS**：文件系统，存储发布博客上传的图片\n" +
                "- 数据库：\n" +
                "\t- MySQL\n" +
                "\n" +
                "### **三、项目源码地址**\n" +
                "目前，本项目已开源至 [GitHub](https://github.com/cl728/colin-blog)，欢迎 star！\n" +
                "\n" +
                "### **四、一些心得**\n" +
                "从4月末到5月中旬，经历了二十来天，终于把这个项目的1.0.0敲定了。虽然项目结构很小，业务逻辑也不甚复杂，但麻雀虽小，五脏俱全，做这个项目将我之前所学的技术栈很好地串连了起来。对于一些相对来说比较难的地方，比如**评论模块的展示**、**MyBatis 多表关系的配置**，以及 **debug 的过程**，都是比较考验我的耐心的。\n" +
                "本人只是个小白，本站难免会有一些未修复或未发现的 **bug**，还望各位大佬不吝赐教。";
        System.out.println( markdownToHtmlExtensions( markdown ) );
    }
}
