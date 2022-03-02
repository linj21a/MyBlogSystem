/**
 * @author 60417
 * @date 2022/2/24
 * @time 15:34
 * @todo
 */
package com.yuyefanhua.blobsystem.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 遇到两个问题：
 * * 1、md文件里面的图片问题
 *   2、转化的html需要携带样式_现在java自带的工具无法做完善，需要根据语法一一替代
 *   3、存储md文件的位置——放在数据库还是存放为一个文件，需要的时候通过路径获取。（选存放在数据库？）
 */
public class MarkDownUtils {
    public static String readMdFile(String  path) throws IOException {
        //读取md文件，转化为字符串：
        File file = new File(path);
        if(!file.exists()||file.isDirectory()){
            throw new RuntimeException("没有该文件或者这是一个目录");
        }
        //打开文件，读取文件：
        BufferedReader bufferedReader = new BufferedReader
                (new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String temp;
        while((temp = bufferedReader.readLine())!=null){
            sb.append(temp);
            sb.append("\n");
        }
        return sb.toString();
    }
    public static String mardDown2Html(String markdown){
        Parser parser = Parser.builder().build();//创建解析器
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();//创建htlm解析器
        //返回解析的内容：
        return renderer.render(document);
    }

    /**
     *
     * 解析表格：
     * 如果md文件包含表格的内容
     */
    public static String mardDown2Html_Expand(String markdown,ApplicationContext applicationContext){
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Collections.singletonList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    public AttributeProvider create(AttributeProviderContext context) {
                        return new CustomAttributeProvider(applicationContext);
                    }
                })
                .build();
        return renderer.render(document);
    }
    /**
     * 处理标签的属性
     */
    static class CustomAttributeProvider implements AttributeProvider {
        private RequestContext requestContext;
        public CustomAttributeProvider(ApplicationContext requestContext) {
        }

        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            }
            if (node instanceof TableBlock) {//表格  cellspacing="0" cellpadding="0"
                attributes.put("class", "ui celled table");
                attributes.put("border", "1");
                attributes.put("cellspacing", "0");
                attributes.put("cellpadding", "0");
            }
            if(node instanceof Image){
                //图片的话，将其路径设置为我们博客的项目里面。
                //位置：destination
                Image image = (Image) node;
                System.out.println(image);
                String destination = image.getDestination();
                String[] split = destination.split("\\\\");
//                System.out.println(Arrays.asList(split));
                if(requestContext==null){
                    image.setDestination(
                           "/"+split[split.length-1]);//图片路径设置为下面的
                }
                attributes.put("src",image.getDestination());
//                System.out.println(image);

            }
        }
    }


    public static void main(String[] args) throws IOException {
        String table = "| hello | hi   | 哈哈哈   |\n" +
                "| ----- | ---- | ----- |\n" +
                "| 斯维尔多  | 士大夫  | f啊    |\n" +
                "| 阿什顿发  | 非固定杆 | 撒阿什顿发 |\n" +
                "\n";
        String a = "[imCoding 爱编程](http://www.baidu.com)";
        System.out.println(mardDown2Html_Expand(table,null));


        String path = "I:\\Spring项目\\个人博客项目.md";
        String s = readMdFile(path);
//        System.out.println(s);
        String s1 = mardDown2Html_Expand(s,null);
        System.out.println("\n##############html###################\n");
        System.out.println(s1);

    }
}
