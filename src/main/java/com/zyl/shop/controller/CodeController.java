package com.zyl.shop.controller;

import com.zyl.shop.util.CodeUtil;
import com.zyl.shop.util.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/code")
public class CodeController {
    @Autowired
    CodeUtil codeUtil;
    @GetMapping()
    public void code(HttpSession session, HttpServletResponse resp){
        try {
            String code = codeUtil.getRamdomCode();
            session.setAttribute(SessionKey.VALIDATE_CODE,code);
            resp.setHeader("Pragma", "no-cache");
            resp.setHeader("Cache-Control", "no-cache");
            resp.setDateHeader("Expires", 0);
            BufferedImage bi = codeUtil.getCodeImage(code, 110, 38);
            ImageIO.write(bi, "JPEG", resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
