package com.greathack.homlin.controller.system;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author zhanghb
 * @date 2019-10-09 10:49
 */
public abstract class BaseController {

    /**
     * 添加Flash消息
     * @param messages
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }

}
