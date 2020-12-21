package com.fduexchange.controller;

import com.fduexchange.pojo.UserInformation;
import com.fduexchange.pojo.UserPassword;
import com.fduexchange.utils.SaveSession;
import com.fduexchange.utils.response.BaseResponse;
import com.fduexchange.service.UserInformationService;
import com.fduexchange.service.UserPasswordService;
import com.fduexchange.utils.StringUtils;
import com.fduexchange.utils.token.TokenProccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
public class AdminController {
    @Resource
    private UserPasswordService userPasswordService;
    @Resource
    private UserInformationService userInformationService;

    @RequestMapping("/insertUser.do")
    @ResponseBody
    public BaseResponse insertUser(HttpServletRequest request,@RequestParam String uname,@RequestParam String phone,
                                   @RequestParam String password, @RequestParam String token) {
        String insertUserToken = (String) request.getSession().getAttribute("token");
        //防止重复提交
        if (StringUtils.getInstance().isNullOrEmpty(insertUserToken) || !insertUserToken.equals(token)) {
            return BaseResponse.fail();
        }
        //判断手机号码是否为正确
        if (!StringUtils.getInstance().isPhone(phone)) {
            return BaseResponse.fail();
        }
        //该手机号码已经存在
        int uid = userInformationService.selectIdByPhone(phone);
        if (uid != 0) {
            return BaseResponse.fail();
        }

        //用户信息
        UserInformation userInformation = new UserInformation();
        userInformation.setPhone(phone);
        userInformation.setCreatetime(new Date());
        userInformation.setUsername(uname);
        userInformation.setModified(new Date());
        int result;
        result = userInformationService.insertSelective(userInformation);
        //如果用户基本信息写入成功
        if (result == 1) {
            uid = userInformationService.selectIdByPhone(phone);
            String newPassword = StringUtils.getInstance().getMD5(password);
            UserPassword userPassword = new UserPassword();
            userPassword.setModified(new Date());
            userPassword.setUid(uid);
            userPassword.setPassword(newPassword);
            result = userPasswordService.insertSelective(userPassword);
            //密码写入失败
            if (result != 1) {
                userInformationService.deleteByPrimaryKey(uid);
                return BaseResponse.fail();
            } else {
                //注册成功
                userInformation = userInformationService.selectByPrimaryKey(uid);
                request.getSession().setAttribute("userInformation", userInformation);
                return BaseResponse.success();
            }
        }
        return BaseResponse.fail();
    }

    //进入登录界面
    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        String token = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("token", token);
        model.addAttribute("token", token);
        return "page/login_page";
    }

    //退出
    @RequestMapping(value = "/logout.do")
    public String logout(HttpServletRequest request) {
        try {
            request.getSession().removeAttribute("userInformation");
            request.getSession().removeAttribute("uid");
            System.out.println("logout");
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home.do";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/registered.do", method = RequestMethod.POST)
    public String registered(Model model,
                             @RequestParam String name, @RequestParam String phone, @RequestParam String password) {
        UserInformation userInformation = new UserInformation();
        userInformation.setUsername(name);
        userInformation.setPhone(phone);
        userInformation.setModified(new Date());
        userInformation.setCreatetime(new Date());
        if (userInformationService.insertSelective(userInformation) == 1) {
            int uid = userInformationService.selectIdByPhone(phone);
            UserPassword userPassword = new UserPassword();
            userPassword.setModified(new Date());
            password = StringUtils.getInstance().getMD5(password);
            userPassword.setPassword(password);
            userPassword.setUid(uid);
            int result = userPasswordService.insertSelective(userPassword);
            if (result != 1) {
                model.addAttribute("result", "fail");
                return "success";
            }
            model.addAttribute("result", "success");
            return "success";
        }
        model.addAttribute("result", "fail");
        return "success";
    }

    //验证登录
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(HttpServletRequest request,
                        @RequestParam String phone, @RequestParam String password, @RequestParam String token) {
        String loginToken = (String) request.getSession().getAttribute("token");
        if (StringUtils.getInstance().isNullOrEmpty(phone) || StringUtils.getInstance().isNullOrEmpty(password)) {
            return "redirect:/login.do";
        }
        //防止重复提交
        if (StringUtils.getInstance().isNullOrEmpty(token) || !token.equals(loginToken)) {
            return "redirect:/login.do";
        }
        boolean b = getId(phone, password, request);
        //失败，不存在该手机号码
        if (!b) {
            return "redirect:/login.do?msg=不存在该手机号码";
        }
        return "redirect:/";
    }

    //查看用户基本信息
    @RequestMapping(value = "/personal_info.do")
    public String personalInfo(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String personalInfoToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("personalInfoToken", personalInfoToken);
        model.addAttribute("token", personalInfoToken);
        model.addAttribute("userInformation", userInformation);
        return "page/personal/personal_info";
    }

    //完善用户基本信息，认证
    @RequestMapping(value = "/certification.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> certification(HttpServletRequest request,
                             @RequestParam(required = false) String userName,
                             @RequestParam(required = false) String realName,
                             @RequestParam(required = false) String clazz,
                             @RequestParam(required = false) String sno,
                                              @RequestParam(required = false) String dormitory,
                             @RequestParam(required = false) String gender) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        Map<String, Integer> map = new HashMap<>();
        map.put("result", 0);
        //该用户还没有登录
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return map;
        }
        String certificationToken = (String) request.getSession().getAttribute("personalInfoToken");
        if (StringUtils.getInstance().isNullOrEmpty(certificationToken)) {
            return map;
        } else {
            request.getSession().removeAttribute("certificationToken");
        }
        if (userName != null && userName.length() < 25) {
            userName = StringUtils.getInstance().replaceBlank(userName);
            userInformation.setUsername(userName);
        } else if (userName != null) {
            return map;
        }

        if (realName != null && realName.length() < 25) {
            realName = StringUtils.getInstance().replaceBlank(realName);
            userInformation.setRealname(realName);
        } else if (realName != null) {
            return map;
        }

        if (clazz != null && clazz.length() < 25) {
            clazz = StringUtils.getInstance().replaceBlank(clazz);
            userInformation.setClazz(clazz);
        } else if (clazz != null) {
            return map;
        }

        if (sno != null && sno.length() < 25) {
            sno = StringUtils.getInstance().replaceBlank(sno);
            userInformation.setSno(sno);
        } else if (sno != null) {
            return map;
        }

        if (dormitory != null && dormitory.length() < 25) {
            dormitory = StringUtils.getInstance().replaceBlank(dormitory);
            userInformation.setDormitory(dormitory);
        } else if (dormitory != null) {
            return map;
        }

        if (gender != null && gender.length() <= 2) {
            gender = StringUtils.getInstance().replaceBlank(gender);
            userInformation.setGender(gender);
        } else if (gender != null) {
            return map;
        }

        int result = userInformationService.updateByPrimaryKeySelective(userInformation);
        if (result != 1) {
            return map;
        }
        request.getSession().setAttribute("userInformation", userInformation);
        map.put("result", 1);
        return map;
    }

    private boolean getId(String phone, String password, HttpServletRequest request) {
        int uid = userInformationService.selectIdByPhone(phone);
        if (uid == 0 || StringUtils.getInstance().isNullOrEmpty(uid)) {
            return false;
        }
        UserInformation userInformation = userInformationService.selectByPrimaryKey(uid);
        if (null == userInformation) {
            return false;
        }
        password = StringUtils.getInstance().getMD5(password);
        String secondPassword = userPasswordService.selectByUid(userInformation.getId()).getPassword();
        if (!password.equals(secondPassword)) {
            return false;
        }
        request.getSession().setAttribute("userInformation", userInformation);
        request.getSession().setAttribute("uid", uid);
        SaveSession.getInstance().save(phone, System.currentTimeMillis());
        return true;
    }
}

