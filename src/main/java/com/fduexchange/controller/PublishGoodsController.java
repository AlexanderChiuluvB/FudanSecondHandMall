package com.fduexchange.controller;

import com.fduexchange.bean.ShopContextBean;
import com.fduexchange.bean.ShopInformationBean;
import com.fduexchange.bean.UserWantBean;
import com.fduexchange.pojo.*;
import com.fduexchange.service.*;
import com.fduexchange.utils.token.TokenProccessor;
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
public class PublishGoodsController {
    @Resource
    private AllSalesService allSalesService;
    @Resource
    private ShopMessageService shopMessageService;
    @Resource
    private UserInformationService userInformationService;
    @Resource
    private SpecificeService specificeService;
    @Resource
    private SecondClassService secondClassService;
    @Resource
    private FirstClassService firstClassService;
    @Resource
    private UserWantService userWantService;


    /***
     * 查看商品详情
     * @param id
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectById.do")
    public String selectById(@RequestParam int id,
                             HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
        }
        try {
            AllSales allSales = allSalesService.selectByPrimaryKey(id);
            model.addAttribute("shopInformation", allSales);
            List<ShopMessage> shopMessages = shopMessageService.selectById(id);
            List<ShopContextBean> shopContextBeans = new ArrayList<>();
            for (ShopMessage s : shopMessages) {
                ShopContextBean shopContextBean = new ShopContextBean();
                UserInformation u = userInformationService.selectByPrimaryKey(s.getUid());
                shopContextBean.setContext(s.getContext());
                shopContextBean.setId(s.getId());
                shopContextBean.setModified(s.getModified());
                shopContextBean.setUid(u.getId());
                shopContextBean.setUsername(u.getUsername());
                shopContextBeans.add(shopContextBean);
            }
            String sort = getSort(allSales.getSort());
            String goodsToken = TokenProccessor.getInstance().makeToken();
            request.getSession().setAttribute("goodsToken", goodsToken);
            model.addAttribute("token", goodsToken);
            model.addAttribute("sort", sort);
            model.addAttribute("userInformation", userInformation);
            model.addAttribute("shopContextBeans", shopContextBeans);
            return "page/product_info";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
    }

    /***
     * 发布商品接口
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/publish_product.do", method = RequestMethod.GET)
    public String publish(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            //如果没有登录
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        try {
            String realName = userInformation.getRealname();
            String sno = userInformation.getSno();
            String dormitory = userInformation.getDormitory();
            if (StringUtils.getInstance().isNullOrEmpty(realName) || StringUtils.getInstance().isNullOrEmpty(sno) || StringUtils.getInstance().isNullOrEmpty(dormitory)) {
                model.addAttribute("message", "请先认证真实信息");
                return "redirect:personal_info.do";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login.do";
        }
        String goodsToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("goodsToken", goodsToken);
        model.addAttribute("shopInformation", new AllSales());
        model.addAttribute("action", 1);
        model.addAttribute("token", goodsToken);
        return "page/publish_product";
    }

    /***
     * 模糊查询商品
     * @param request
     * @param model
     * @param name
     * @return
     */
    @RequestMapping(value = "/findShopByName.do")
    public String findByName(HttpServletRequest request, Model model,
                             @RequestParam String name) {
        try {
            List<AllSales> allsales = allSalesService.selectByName(name);
            UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
            if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
                userInformation = new UserInformation();
                model.addAttribute("userInformation", userInformation);
            } else {
                model.addAttribute("userInformation", userInformation);
            }
            List<ShopInformationBean> shopInformationBeans = new ArrayList<>();
            String sortName;
            for (AllSales allSales : allsales) {
                int sort = allSales.getSort();
                sortName = getSort(sort);
                ShopInformationBean shopInformationBean = new ShopInformationBean();
                shopInformationBean.setId(allSales.getId());
                shopInformationBean.setName(allSales.getName());
                shopInformationBean.setLevel(allSales.getLevel());
                shopInformationBean.setRemark(allSales.getRemark());
                shopInformationBean.setPrice(allSales.getPrice().doubleValue());
                shopInformationBean.setQuantity(allSales.getQuantity());
                shopInformationBean.setTransaction(allSales.getTransaction());
                shopInformationBean.setSort(sortName);
                shopInformationBean.setUid(allSales.getUid());
                shopInformationBean.setImage(allSales.getImage());
                shopInformationBeans.add(shopInformationBean);
            }
            model.addAttribute("shopInformationBean", shopInformationBeans);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:mall_page.do";
        }
        return "page/mall_page";
    }

    /***
     * 进入求购商城
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/require_mall.do")
    public String requireMall(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        List<UserWant> userWants = userWantService.selectAll();
        List<UserWantBean> list = new ArrayList<>();
        for (UserWant userWant : userWants) {
            UserWantBean u = new UserWantBean();
            u.setSort(getSort(userWant.getSort()));
            u.setRemark(userWant.getRemark());
            u.setQuantity(userWant.getQuantity());
            u.setPrice(userWant.getPrice().doubleValue());
            u.setUid(userWant.getUid());
            u.setId(userWant.getId());
            u.setModified(userWant.getModified());
            u.setName(userWant.getName());
            list.add(u);
        }
        model.addAttribute("list", list);
        return "page/require_mall";
    }

    //通过id查看商品的详情
    @RequestMapping(value = "/findShopById.do")
    @ResponseBody
    public AllSales findShopById(@RequestParam int id) {
        return allSalesService.selectByPrimaryKey(id);
    }

    //通过分类选择商品
    @RequestMapping(value = "/selectBySort.do")
    @ResponseBody
    public List<AllSales> selectBySort(@RequestParam int sort) {
        return allSalesService.selectBySort(sort);
    }

    //分页查询
    @RequestMapping(value = "/selectByCounts.do")
    @ResponseBody
    public List<AllSales> selectByCounts(@RequestParam int counts) {
        Map<String, Integer> map = new HashMap<>();
        map.put("start", (counts - 1) * 12);
        map.put("end", 12);
        return allSalesService.selectTen(map);
    }

    //获取最详细的分类，第三层
    private Specific selectSpecificBySort(int sort) {
        return specificeService.selectByPrimaryKey(sort);
    }

    //获得第二层分类
    private SecondClass selectSecondClassByCid(int cid) {
        return secondClassService.selectByPrimaryKey(cid);
    }

    //
    private FirstClass selectFirstClassByAid(int aid) {
        return firstClassService.selectByPrimaryKey(aid);
    }

    private String getSort(int sort) {
        StringBuilder sb = new StringBuilder();
        Specific specific = selectSpecificBySort(sort);
        int cid = specific.getCid();
        SecondClass secondClass = selectSecondClassByCid(cid);
        int aid = secondClass.getAid();
        FirstClass firstClass = selectFirstClassByAid(aid);
        String allName = firstClass.getName();
        sb.append(allName);
        sb.append("-");
        sb.append(secondClass.getName());
        sb.append("-");
        sb.append(specific.getName());
        return sb.toString();
    }

}
