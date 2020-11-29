package com.fduexchange.utils.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class OnlyOneUser {
    @Before(value = "execution(* com.fduexchange.controller.UserWantGoodsController.login(..))")
    public void isExit(){

    }
}
