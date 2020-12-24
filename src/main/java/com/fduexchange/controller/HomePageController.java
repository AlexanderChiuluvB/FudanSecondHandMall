package com.fduexchange.controller;

import com.fduexchange.bean.ShopInformationBean;
import com.fduexchange.bean.UserWantBean;
import com.fduexchange.pojo.*;
import com.fduexchange.service.*;
import com.fduexchange.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class HomePageController {
    @Resource
    private AllSalesService allSalesService;
    @Resource
    private ThirdClassService thirdClassService;
    @Resource
    private SecondClassService secondClassService;
    @Resource
    private FirstClassService firstClassService;
    @Resource
    private ShopMessageService shopMessageService;
    @Resource
    private UserWantService userWantService;

    /***
     * 登录首页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/", "/home.do"})
    public String home(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (!StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            model.addAttribute("userInformation", userInformation);
        } else {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
        }
        try {
            List<AllSales> allsales = selectTen(1, 5);
            List<ShopInformationBean> list = new ArrayList<>();
            int counts = getShopCounts();
            model.addAttribute("shopInformationCounts", counts);
            String stringBuffer;
            for (AllSales allSales : allsales) {
                stringBuffer = getSortName(allSales.getSort());
                ShopInformationBean shopInformationBean = new ShopInformationBean();
                shopInformationBean.setId(allSales.getId());
                shopInformationBean.setName(allSales.getName());
                shopInformationBean.setLevel(allSales.getLevel());
                shopInformationBean.setPrice(allSales.getPrice().doubleValue());
                shopInformationBean.setRemark(allSales.getRemark());
                shopInformationBean.setSort(stringBuffer);
                shopInformationBean.setQuantity(allSales.getQuantity());
                shopInformationBean.setUid(allSales.getUid());
                shopInformationBean.setTransaction(allSales.getTransaction());
                shopInformationBean.setImage(allSales.getImage());
                list.add(shopInformationBean);
            }
            model.addAttribute("shopInformationBean", list);

            List<UserWant> userwants = userWantService.selectAll();
            List<UserWantBean> list2 = new ArrayList<>();
            for (UserWant userWant : userwants) {
                UserWantBean u = new UserWantBean();
                u.setSort(getSort(userWant.getSort()));
                u.setRemark(userWant.getRemark());
                u.setQuantity(userWant.getQuantity());
                u.setPrice(userWant.getPrice().doubleValue());
                u.setUid(userWant.getUid());
                u.setId(userWant.getId());
                u.setModified(userWant.getModified());
                u.setName(userWant.getName());
                list2.add(u);
            }
            model.addAttribute("list", list2);

        } catch (Exception e) {
            e.printStackTrace();
            return "page/login_page";
        }
        return "index";
    }

    /**
     * 进入商城页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/mall_page.do")
    public String mallPage(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        try {
            List<AllSales> allsales = selectTen(1, 12);
            List<ShopInformationBean> list = new ArrayList<>();
            int counts = getShopCounts();
            model.addAttribute("shopInformationCounts", counts);
            String sortName;
            for (AllSales allSales : allsales) {
                int sort = allSales.getSort();
                sortName = getSortName(sort);
                ShopInformationBean shopInformationBean = new ShopInformationBean();
                shopInformationBean.setId(allSales.getId());
                shopInformationBean.setName(allSales.getName());
                shopInformationBean.setLevel(allSales.getLevel());
                shopInformationBean.setRemark(allSales.getRemark());
                shopInformationBean.setPrice(allSales.getPrice().doubleValue());
                shopInformationBean.setSort(sortName);
                shopInformationBean.setQuantity(allSales.getQuantity());
                shopInformationBean.setTransaction(allSales.getTransaction());
                shopInformationBean.setUid(allSales.getUid());
                shopInformationBean.setImage(allSales.getImage());
                list.add(shopInformationBean);
            }
            model.addAttribute("shopInformationBean", list);
        } catch (Exception e) {
            e.printStackTrace();
            return "page/login_page";
        }
        return "page/mall_page";
    }

    //通过分类的第三层id获取全名
    private String getSortName(int sort) {
        StringBuilder stringBuffer = new StringBuilder();
        ThirdClass thirdClass = selectThirdClassBySort(sort);
        int cid = thirdClass.getCid();
        SecondClass secondClass = selectSecondClassByCid(cid);
        int aid = secondClass.getAid();
        FirstClass firstClass = selectFirstClassByAid(aid);
        stringBuffer.append(firstClass.getName());
        stringBuffer.append("-");
        stringBuffer.append(secondClass.getName());
        stringBuffer.append("-");
        stringBuffer.append(thirdClass.getName());
//        System.out.println(sort);
        return stringBuffer.toString();
    }

    //获得分类中的第一层
    @RequestMapping(value = "/getFirstClass.do")
    @ResponseBody
    public List<FirstClass> getAllKind() {
        return getFirstClass();
    }

    //获得分类中的第二层，通过第一层的id
    @RequestMapping(value = "/getSecondClass.do", method = RequestMethod.POST)
    @ResponseBody
    public List<SecondClass> getSecondClassByAid(@RequestParam int id) {
        return selectAllSecondClass(id);
    }

    //通过第二层的id获取对应的第三层
    @RequestMapping(value = "/getThirdClass.do")
    @ResponseBody
    public List<ThirdClass> getThirdClassByCid(@RequestParam int id) {
        return selectAllThirdClass(id);
    }

    //get the shops counts
    @RequestMapping(value = "/getShopsCounts.do")
    @ResponseBody
    public Map getShopsCounts() {
        Map<String, Integer> map = new HashMap<>();
        int counts = 0;
        try {
            counts = allSalesService.getCounts();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("counts", counts);
            return map;
        }
        map.put("counts", counts);
        return map;
    }

    @RequestMapping(value = "/getShops.do")
    @ResponseBody
    public List getShops(@RequestParam int start) {
        List<AllSales> list = new ArrayList<>();
        try {
            int end = 12;
            list = selectTen(start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    //获取商品，分页,一次性获取end个
    private List<AllSales> selectTen(int start, int end) {
        Map<String, Integer> map = new HashMap();
        map.put("start", (start - 1) * end);
        map.put("end", end);
        return allSalesService.selectTen(map);
    }

    //获取最详细的分类，第三层
    private ThirdClass selectThirdClassBySort(int sort) {
        return thirdClassService.selectByPrimaryKey(sort);
    }

    //获得第二层分类
    private SecondClass selectSecondClassByCid(int cid) {
        return secondClassService.selectByPrimaryKey(cid);
    }

    //获得第一层分类
    private FirstClass selectFirstClassByAid(int aid) {
        return firstClassService.selectByPrimaryKey(aid);
    }

    //获得第一层所有
    private List<FirstClass> getFirstClass() {
        return firstClassService.selectAll();
    }

    //根据第一层的id获取该层下的第二层
    private List<SecondClass> selectAllSecondClass(int aid) {
        return secondClassService.selectByAid(aid);
    }

    //根据第二层的id获取其对应的第三层所有id
    private List<ThirdClass> selectAllThirdClass(int cid) {
        return thirdClassService.selectByCid(cid);
    }

    //获得商品总页数
    private int getShopCounts() {
        return allSalesService.getCounts();
    }

    private String getSort(int sort) {
        StringBuilder sb = new StringBuilder();
        ThirdClass thirdClass = selectThirdClassBySort(sort);
        int cid = thirdClass.getCid();
        SecondClass secondClass = selectSecondClassByCid(cid);
        int aid = secondClass.getAid();
        FirstClass firstClass = selectFirstClassByAid(aid);
        String allName = firstClass.getName();
        sb.append(allName);
        sb.append("-");
        sb.append(secondClass.getName());
        sb.append("-");
        sb.append(thirdClass.getName());
        return sb.toString();
    }

}
