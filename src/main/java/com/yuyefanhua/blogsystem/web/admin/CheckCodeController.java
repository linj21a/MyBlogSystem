/**
 * @author 60417
 * @date 2022/3/3
 * @time 11:20
 * @todo
 */
package com.yuyefanhua.blogsystem.web.admin;

import com.google.code.kaptcha.Producer;
import com.yuyefanhua.blogsystem.util.CheckCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于生成验证码的控制器
 */
@Controller
@RequestMapping(value = "/kaptcha")
public class CheckCodeController {
    @Autowired
    private Producer captchaProducer;
    //这里生成的验证码会存放在session里面
    /**
     * 使用java开源 kaptcha生成图片验证码，存储到session
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/code")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        request.getSession().setAttribute("checkcode", capText);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    /**
     * 自定义生成验证码和校验的工具类
     */
    private final CheckCodeUtils checkCodeUtils = new CheckCodeUtils();
    /**
     * 使用自定义的校验码生成工具栏来生成图片验证码存储到session里面
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkcode",method = RequestMethod.GET)
    public void getCheckCode(HttpServletRequest request,HttpServletResponse response){
        try {
            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            // getRandomCodeImage方法会直接将生成的验证码图片写入response
            checkCodeUtils.getRandomCodeImage(request, response);//这里的key为checkcode
            System.out.println("session里面存储的验证码为："+request.getSession().getAttribute("checkcode"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 生成验证码,返回的是 base64
    @GetMapping("/getCheckCodeBase64")
    public Object getCaptchaBase64(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> result = new HashMap<>();
        try {

            response.setContentType("image/png");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expire", "0");
            response.setHeader("Pragma", "no-cache");
            // 返回base64
            String base64String = checkCodeUtils.getRandomCodeBase64(request, response);
            result.put("url", "data:image/png;base64," + base64String);
            result.put("message", "created successfull");
            //http://tool.chinaz.com/tools/imgtobase/  base64直接转为图片网站
            ///需要前端转为图片显示
            System.out.println("结果：" + result.get("url"));

        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }
}
