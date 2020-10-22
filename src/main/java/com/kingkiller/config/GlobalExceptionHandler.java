package com.kingkiller.config;

import com.kingkiller.exception.CustomerServiceException;
import com.kingkiller.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 全局异常处理类
 * @author kingkiller
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = CustomerServiceException.class)
    public String serviceExceptionHandler(CustomerServiceException e, RedirectAttributes attributes) {
        log.info("全局异常捕获 {}", e.getErrorCode().getCode());
        String extraMsg = e.getExtraMsg();
        // 判断是否有附加信息
        if (extraMsg != null){
            attributes.addFlashAttribute("msg",extraMsg);
        }else {
            attributes.addFlashAttribute("msg",e.getErrorCode().getDesc());
        }
        return "redirect:/err";
    }


}
