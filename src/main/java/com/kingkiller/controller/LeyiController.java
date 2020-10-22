package com.kingkiller.controller;

import com.kingkiller.exception.CustomerErrorCode;
import com.kingkiller.exception.CustomerServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LeyiController {

    @RequestMapping(value = "/leyi")
    public String leyi(){
        throw new CustomerServiceException(CustomerErrorCode.SYSTEM_INNER_ERROR);
    }

}
