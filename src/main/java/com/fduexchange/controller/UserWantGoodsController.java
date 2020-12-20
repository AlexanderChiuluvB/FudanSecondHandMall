package com.fduexchange.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.fduexchange.bean.GoodsCarBean;
import com.fduexchange.bean.OrderListBean;
import com.fduexchange.bean.ShopInformationBean;
import com.fduexchange.bean.UserWantBean;
import com.fduexchange.pojo.*;
import com.fduexchange.utils.response.BaseResponse;
import com.fduexchange.service.*;
import com.fduexchange.utils.token.TokenProccessor;
import com.fduexchange.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
public class UserWantGoodsController {
    public static final String uploadPath = "/usr/local/tomcat/webapps/";

    @Resource
    private AllSalesService allSalesService;
    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private ThirdClassService thirdClassService;
    @Resource
    private SecondClassService secondClassService;
    @Resource
    private FirstClassService firstClassService;
    @Resource
    private ShopMessageService shopMessageService;
    @Resource
    private UserReleaseService userReleaseService;
    @Resource
    private UserWantService userWantService;
    @Resource
    private OrderTableService orderTableService;

    @RequestMapping(value = "/insert_order.do")
    @ResponseBody
    public BaseResponse InsertOrder(HttpServletRequest request, Model model,
                              @RequestParam String price,
                              @RequestParam String address,
                              @RequestParam String contactInfo,
                              @RequestParam String name,
                              @RequestParam int salesId,
                              @RequestParam int shoppingCarId,
                              @RequestParam int quantity) throws ParseException {
        Random rand = new Random();
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return BaseResponse.fail();
        }
        model.addAttribute("userInformation", userInformation);
        String error = request.getParameter("error");
        if (!StringUtils.getInstance().isNullOrEmpty(error)) {
            model.addAttribute("error", "error");
        }
        int purchaserId = userInformation.getId();
        int sellerId = userReleaseService.selectSellerIdByGoodsId(salesId);

        List<AllSales> salesList = allSalesService.selectByName(name);
        int originQuantity = salesList.get(0).getQuantity();

        if (originQuantity >= quantity) {

            //减库存
            AllSales allSales = new AllSales();
            allSales.setId(salesId);
            allSales.setQuantity(originQuantity-quantity);
            if (originQuantity == quantity) {
                allSales.setDisplay(0);
            }
            allSalesService.updateByPrimaryKeySelective(allSales);

            //插入订单表
            OrderTable order = new OrderTable();
            order.setOrder_id(rand.nextInt(100000));
            order.setSeller_id(sellerId);
            order.setSales_id(salesId);
            order.setPurchaser_id(purchaserId);
            order.setAddress(address);
            order.setContact_info(contactInfo);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(format.format(date));
            //order.setModified(date);
            order.setPrice(new BigDecimal(100));
            order.setQuantity(quantity);
            order.setSales_name(name);
            order.setState(1);
            order.setPurchaser_name("test_name");
            System.out.println(order.toString());
            int insertResult = orderTableService.insertSelective(order);
            if (insertResult !=1) {
                System.out.println("插入订单表失败");
            } else {
                System.out.println("插入订单表success");
            }

            // 更新购物车
            ShoppingCart shoppingCart = new ShoppingCart();
            if (originQuantity == quantity) {
                shoppingCart.setDisplay(0);
            }
            shoppingCart.setSid(shoppingCarId);
            shoppingCart.setSid(salesId);
            shoppingCart.setUid(purchaserId);
            shoppingCartService.updateByPrimaryKeySelective(shoppingCart);
        } else {
            return BaseResponse.fail();
        }
        return BaseResponse.success();
    }

    //确认收货
    @RequestMapping(value = "/modify_order.do")
    public BaseResponse modifyOrderStatus(HttpServletRequest request, Model model,
                                    @RequestParam int orderId) {
        OrderTable order = new OrderTable();
        order.setState(0);
        order.setOrder_id(orderId);
        orderTableService.updateState(order);
        return BaseResponse.success();
    }

    //进入求购页面
    @RequestMapping(value = "/require_product.do")
    public String enterPublishWantGoods(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String error = request.getParameter("error");
        if (!StringUtils.getInstance().isNullOrEmpty(error)) {
            model.addAttribute("error", "error");
        }
        String publishUserWantToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("publishUserWantToken", publishUserWantToken);
        model.addAttribute("token", publishUserWantToken);
        model.addAttribute("userInformation", userInformation);
        return "page/require_product";
    }

    @RequestMapping(value = "/modified_require_product.do")
    public String modifiedWantGoods(HttpServletRequest request, Model model,
                                         @RequestParam int id) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String publishUserWantToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("publishUserWantToken", publishUserWantToken);
        model.addAttribute("token", publishUserWantToken);
        model.addAttribute("userInformation", userInformation);
        UserWant userWant = userWantService.selectByPrimaryKey(id);
        model.addAttribute("userWant", userWant);
        String sort = getSort(userWant.getSort());
        model.addAttribute("sort", sort);
        return "page/modified_require_product";
    }

    @RequestMapping(value = "/publishUserWant.do")
    public String publishUserWantInfo(HttpServletRequest request,
                                  @RequestParam String name,
                                  @RequestParam int sort, @RequestParam int quantity,
                                  @RequestParam double price, @RequestParam String remark,
                                  @RequestParam String token) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String publishUserWantToke = (String) request.getSession().getAttribute("publishUserWantToken");
        if (StringUtils.getInstance().isNullOrEmpty(publishUserWantToke) || !publishUserWantToke.equals(token)) {
            return "redirect:require_product.do?error=3";
        } else {
            request.getSession().removeAttribute("publishUserWantToken");
        }
        try {
            if (name.length() < 1 || remark.length() < 1 || name.length() > 25 || remark.length() > 25) {
                return "redirect:require_product.do";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:require_product.do?error=1";
        }
        UserWant userWant = new UserWant();
        userWant.setModified(new Date());
        userWant.setName(name);
        userWant.setPrice(new BigDecimal(price));
        userWant.setQuantity(quantity);
        userWant.setRemark(remark);
        userWant.setUid((Integer) request.getSession().getAttribute("uid"));
        userWant.setSort(sort);
        int result;
        try {
            result = userWantService.insertSelective(userWant);
            if (result != 1) {
                return "redirect:/require_product.do?error=2";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/require_product.do?error=2";
        }
        return "redirect:/my_require_product.do";
    }

    /***
     * 求购商品信息
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = {"/my_require_product.do", "/my_require_product_page.do"})
    public String getUserWant(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        try {
            int uid = (int) request.getSession().getAttribute("uid");
            List<UserWant> userWantList = selectUserWantByUid(uid);
            List<UserWantBean> userWantBeans = new ArrayList<>();
            for (UserWant userWant : userWantList) {
                UserWantBean userWantBean = new UserWantBean();
                userWantBean.setId(userWant.getId());
                userWantBean.setModified(userWant.getModified());
                userWantBean.setName(userWant.getName());
                userWantBean.setPrice(userWant.getPrice().doubleValue());
                userWantBean.setUid(uid);
                userWantBean.setQuantity(userWant.getQuantity());
                userWantBean.setRemark(userWant.getRemark());
                userWantBean.setSort(getSort(userWant.getSort()));
                userWantBeans.add(userWantBean);
            }
            model.addAttribute("userWant", userWantBeans);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
        model.addAttribute("userInformation", userInformation);
        return "page/personal/my_require_product_page";
    }

    /**
     * 求购信息总数
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getUserWantCounts.do")
    @ResponseBody
    public Map getUserWantNum(HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();
        if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
            map.put("counts", -1);
            return map;
        }
        try {
            int counts = getUserWantCounts((Integer) request.getSession().getAttribute("uid"));
            map.put("counts", counts);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("counts", -1);
        }
        return map;
    }

    /***
     * 删除求购信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUserWant.do")
    public String deleteUserWant(HttpServletRequest request, @RequestParam int id) {
        if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
            return "redirect:/login.do";
        }
        UserWant userWant = new UserWant();
        userWant.setId(id);
        userWant.setDisplay(0);
        try {
            int result = userWantService.updateByPrimaryKeySelective(userWant);
            if (result != 1) {
                return "redirect:my_require_product.do";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:my_require_product.do";
    }

    /***
     * 查看购物车功能
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/shopping_cart.do")
    public String selectGoodsCar(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        int uid = userInformation.getId();
        List<ShoppingCart> shoppingCarts = shoppingCartService.selectByUid(uid);
        List<GoodsCarBean> goodsCarBeans = new ArrayList<>();
        for (ShoppingCart goodsCar : shoppingCarts) {
            GoodsCarBean goodsCarBean = new GoodsCarBean();
            goodsCarBean.setUid(goodsCar.getUid());
            goodsCarBean.setSid(goodsCar.getSid());
            goodsCarBean.setModified(goodsCar.getModified());
            goodsCarBean.setId(goodsCar.getId());
            goodsCarBean.setQuantity(goodsCar.getQuantity());
            AllSales allSales = allSalesService.selectByPrimaryKey(goodsCar.getSid());
            goodsCarBean.setName(allSales.getName());
            goodsCarBean.setRemark(allSales.getRemark());
            goodsCarBean.setImage(allSales.getImage());
            goodsCarBean.setPrice(allSales.getPrice().doubleValue());
            goodsCarBean.setSort(getSort(allSales.getSort()));
            goodsCarBeans.add(goodsCarBean);
        }
        model.addAttribute("list", goodsCarBeans);
        return "page/shopping_cart";
    }

    /***
     * 查看订单列表功能
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/order_list.do")
    public String getOrderList(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            userInformation = new UserInformation();
            model.addAttribute("userInformation", userInformation);
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        int uid=userInformation.getId();
        //根据id分别获取订单列表
        List<OrderTable> orderLists_purchaser = orderTableService.selectByPurchaserId(uid);
        List<OrderTable> orderLists_seller = orderTableService.selectBySellerId(uid);

        List<OrderListBean> orderListBeans_pur = new ArrayList<>();
        for (OrderTable order:orderLists_purchaser) {
            OrderListBean orderListBean_pur = new OrderListBean();
            orderListBean_pur.setOrder_id(order.getOrder_id());
            orderListBean_pur.setState(order.getState());
            orderListBean_pur.setSales_name(order.getSales_name());
            orderListBean_pur.setAddress(order.getAddress());
            orderListBean_pur.setPrice(order.getPrice());
            orderListBean_pur.setQuantity(order.getQuantity());
            orderListBean_pur.setContact_info(order.getContact_info());
            orderListBeans_pur.add(orderListBean_pur);
        }
        List<OrderListBean> orderListBeans_sel = new ArrayList<>();
        for (OrderTable order:orderLists_seller) {
            OrderListBean orderListBean_sel = new OrderListBean();
            orderListBean_sel.setOrder_id(order.getOrder_id());
            orderListBean_sel.setState(order.getState());
            orderListBean_sel.setSales_name(order.getSales_name());
            orderListBean_sel.setAddress(order.getAddress());
            orderListBean_sel.setPrice(order.getPrice());
            orderListBean_sel.setQuantity(order.getQuantity());
            orderListBean_sel.setContact_info(order.getContact_info());
            orderListBeans_sel.add(orderListBean_sel);
        }
        model.addAttribute("order_pur", orderListBeans_pur);
        model.addAttribute("order_sel", orderListBeans_sel);
        return "page/myOrderList";
    }

    /***
     * 添加商品到购物车
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/insertGoodsCar.do")
    @ResponseBody
    public BaseResponse insertGoodsCar(HttpServletRequest request, @RequestParam int id) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return BaseResponse.fail();
        }
        int uid = userInformation.getId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDisplay(1);
        shoppingCart.setModified(new Date());
        shoppingCart.setQuantity(1);
        shoppingCart.setUid(uid);
        shoppingCart.setSid(id);
        shoppingCartService.insertSelective(shoppingCart);
        return BaseResponse.success();
    }

    /***
     * 删除购物车商品
     * @param request
     * @param id
     * @param sid
     * @return
     */
    @RequestMapping(value = "/deleteGoodsCar.do")
    @ResponseBody
    public BaseResponse deleteShopCar(HttpServletRequest request, @RequestParam int id, @RequestParam int sid) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return BaseResponse.fail();
        }
        int uid = userInformation.getId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setDisplay(0);
        shoppingCart.setId(id);
        shoppingCart.setSid(sid);
        shoppingCart.setUid(uid);
        int result = shoppingCartService.updateByPrimaryKeySelective(shoppingCart);
        if (result != 1) {
            return BaseResponse.fail();
        }
        return BaseResponse.success();
    }

    private String generateUploadImageName() {
        return  "images/" + StringUtils.getInstance().getRandomChar() + System.currentTimeMillis() + ".jpg";
    }

    private String generateThumbnailName() {
        return "images/thumbnails/" +
                StringUtils.getInstance().getRandomChar() + System.currentTimeMillis();
    }

    @RequestMapping(value = "/insertGoods.do", method = RequestMethod.POST)
    public String insertGoods(@RequestParam String name,
                              @RequestParam int level,
                              @RequestParam String remark,
                              @RequestParam double price,
                              @RequestParam int sort,
                              @RequestParam int quantity,
                              @RequestParam String token,
                              @RequestParam(required = false) MultipartFile image,
                              @RequestParam int action,
                              @RequestParam(required = false) int id,
                              HttpServletRequest request, Model model) {
        String goodsToken = (String) request.getSession().getAttribute("goodsToken");
        if (StringUtils.getInstance().isNullOrEmpty(goodsToken) || !goodsToken.equals(token)) {
            return "redirect:publish_product.do?error=1";
        } else {
            request.getSession().removeAttribute("goodsToken");
        }

        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        model.addAttribute("userInformation", userInformation);
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }

        name = StringUtils.getInstance().replaceBlank(name);
        remark = StringUtils.getInstance().replaceBlank(remark);

        if (StringUtils.getInstance().isNullOrEmpty(name) || StringUtils.getInstance().isNullOrEmpty(level) || StringUtils.getInstance().isNullOrEmpty(remark) || StringUtils.getInstance().isNullOrEmpty(price)
                || StringUtils.getInstance().isNullOrEmpty(sort) || StringUtils.getInstance().isNullOrEmpty(quantity) || name.length() > 25 || remark.length() > 122) {
            model.addAttribute("message", "格式错误");
            model.addAttribute("token", goodsToken);
            request.getSession().setAttribute("goodsToken", goodsToken);
            return "page/publish_product";
        }

        //插入
        if (action == 1) {
            if (StringUtils.getInstance().isNullOrEmpty(image)) {
                model.addAttribute("message", "请选择图片");
                model.addAttribute("token", goodsToken);
                request.getSession().setAttribute("goodsToken", goodsToken);
                return "redirect:publish_product.do?error=请插入图片";
            }
            //generate image path and thumbnail path
            String imagePath, thumbnailPath;
            imagePath = generateUploadImageName();
            thumbnailPath = generateThumbnailName();
            File file = new File(uploadPath, imagePath);
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                image.transferTo(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //创建缩略图文件夹
            File thumbnailsFile = new File(uploadPath + thumbnailPath);
            if (!thumbnailsFile.exists()) {
                thumbnailsFile.mkdir();
            }

            AllSales allSales = new AllSales();
            allSales.setName(name);
            allSales.setLevel(level);
            allSales.setRemark(remark);
            allSales.setPrice(new BigDecimal(price));
            allSales.setSort(sort);
            allSales.setQuantity(quantity);
            allSales.setModified(new Date());
            allSales.setImage(imagePath);
            allSales.setThumbnails(thumbnailPath);
            int uid = (int) request.getSession().getAttribute("uid");
            allSales.setUid(uid);

            try {
                int result = allSalesService.insertSelective(allSales);
                if (result != 1) {
                    model.addAttribute("message", "请输入正确的格式");
                    model.addAttribute("token", goodsToken);
                    request.getSession().setAttribute("goodsToken", goodsToken);
                    return "page/publish_product";
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("token", goodsToken);
                model.addAttribute("message", "请输入正确的格式");
                request.getSession().setAttribute("goodsToken", goodsToken);
                return "page/publish_product";
            }
            int sid = allSalesService.selectIdByImage(imagePath);

            //更新商品的发布表
            UserRelease userRelease = new UserRelease();
            userRelease.setModified(new Date());
            userRelease.setSid(sid);
            userRelease.setUid(uid);
            try {
                int result = userReleaseService.insertSelective(userRelease);
                if (result != 1) {
                    allSalesService.deleteByPrimaryKey(sid);
                    model.addAttribute("token", goodsToken);
                    model.addAttribute("message", "输入格式错误");
                    request.getSession().setAttribute("goodsToken", goodsToken);
                    return "page/publish_product";
                }
            } catch (Exception e) {
                allSalesService.deleteByPrimaryKey(sid);
                e.printStackTrace();
                model.addAttribute("token", goodsToken);
                model.addAttribute("message", "输入格式错误");
                request.getSession().setAttribute("goodsToken", goodsToken);
                return "page/publish_product";
            }
            allSales.setId(sid);
            goodsToken = TokenProccessor.getInstance().makeToken();
            request.getSession().setAttribute("goodsToken", goodsToken);
            model.addAttribute("token", goodsToken);
            model.addAttribute("shopInformation", allSales);
            model.addAttribute("userInformation", userInformation);
            String sbSorted = getSort(sort);
            model.addAttribute("sort", sbSorted);
            model.addAttribute("action", 2);
            return "redirect:/my_publish_product_page.do";

        } else if (action == 2) {//更新商品
            AllSales allSales = new AllSales();
            allSales.setModified(new Date());
            allSales.setQuantity(quantity);
            allSales.setSort(sort);
            allSales.setPrice(new BigDecimal(price));
            allSales.setRemark(remark);
            allSales.setLevel(level);
            allSales.setName(name);
            allSales.setId(id);
            try {
                int result = allSalesService.updateByPrimaryKeySelective(allSales);
                if (result != 1) {
                    return "redirect:publish_product.do";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:publish_product.do";
            }
            goodsToken = TokenProccessor.getInstance().makeToken();
            request.getSession().setAttribute("goodsToken", goodsToken);
            model.addAttribute("token", goodsToken);
            allSales = allSalesService.selectByPrimaryKey(id);
            model.addAttribute("userInformation", userInformation);
            model.addAttribute("shopInformation", allSales);
            model.addAttribute("action", 2);
            model.addAttribute("sort", getSort(sort));
        }
        return "redirect:/my_publish_product_page.do";
    }

    //从发布的商品直接跳转到修改商品
    @RequestMapping(value = "/modifiedMyPublishProduct.do")
    public String modifiedMyPublishProduct(HttpServletRequest request, Model model,
                                           @RequestParam int id) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        }
        String goodsToken = TokenProccessor.getInstance().makeToken();
        request.getSession().setAttribute("goodsToken", goodsToken);
        model.addAttribute("token", goodsToken);
        AllSales allSales = allSalesService.selectByPrimaryKey(id);
        model.addAttribute("userInformation", userInformation);
        model.addAttribute("shopInformation", allSales);
        model.addAttribute("action", 2);
        model.addAttribute("sort", getSort(allSales.getSort()));
        return "page/publish_product";
    }

    /***
     * 发布商品留言
     * @param id
     * @param context
     * @param token
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertShopContext.do")
    @ResponseBody
    public Map<String, String> insertShopContext(@RequestParam int id,
                                 @RequestParam String context,
                                 @RequestParam String token,
                                 HttpServletRequest request) {
        String goodsToken = (String) request.getSession().getAttribute("goodsToken");
        Map<String, String> map = new HashMap<>();
        map.put("result", "1");
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            map.put("result", "2");
            return map;
        }
        if (StringUtils.getInstance().isNullOrEmpty(goodsToken) || !token.equals(goodsToken)) {
            return map;
        }
        ShopMessage shopMessage = new ShopMessage();
        shopMessage.setContext(context);
        Date date = new Date();
        shopMessage.setModified(date);
        shopMessage.setSid(id);
        int uid = (int) request.getSession().getAttribute("uid");
        shopMessage.setUid(uid);
        try {
            int result = shopMessageService.insertSelective(shopMessage);
            if (result != 1) {
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        map.put("result", "1");
        map.put("username", userInformation.getUsername());
        map.put("context", context);
        map.put("time", StringUtils.getInstance().DateToString(date));
        return map;
    }

    //下架商品
    @RequestMapping(value = "/deleteShop.do")
    public String deleteGoods(HttpServletRequest request,
                              Model model,
                              @RequestParam int id) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        AllSales allSales = new AllSales();
        allSales.setModified(new Date());
        allSales.setDisplay(0);
        allSales.setId(id);
        try {
            int result = allSalesService.updateByPrimaryKeySelective(allSales);
            if (result != 1) {
                return "redirect:my_publish_product_page.do";
            }
            return "redirect:my_publish_product_page.do";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:my_publish_product_page.do";
        }
    }

    //查看发布的所有商品总数
    @RequestMapping(value = "/getReleaseShopCounts.do")
    @ResponseBody
    public Map<String, Integer> getReleaseShopCounts(HttpServletRequest request) {
        Map<String, Integer> map = new HashMap<>();
        if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
            map.put("counts", -1);
            return map;
        }
        int counts = getReleaseCounts((Integer) request.getSession().getAttribute("uid"));
        map.put("counts", counts);
        return map;
    }

    //查看我的发布的商品
    @RequestMapping(value = "/my_publish_product_page.do")
    public String getReleaseShop(HttpServletRequest request, Model model) {
        UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
        if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
            return "redirect:/login.do";
        } else {
            model.addAttribute("userInformation", userInformation);
        }
        int uid = (int) request.getSession().getAttribute("uid");
        List<AllSales> allsales = allSalesService.selectUserReleaseByUid(uid);
        List<ShopInformationBean> list = new ArrayList<>();
        String stringBuffer;
        for (AllSales allSales : allsales) {
            stringBuffer = getSort(allSales.getSort());
            ShopInformationBean shopInformationBean = new ShopInformationBean();
            shopInformationBean.setId(allSales.getId());
            shopInformationBean.setName(allSales.getName());
            shopInformationBean.setLevel(allSales.getLevel());
            shopInformationBean.setPrice(allSales.getPrice().doubleValue());
            shopInformationBean.setRemark(allSales.getRemark());
            shopInformationBean.setSort(stringBuffer);
            shopInformationBean.setQuantity(allSales.getQuantity());
            shopInformationBean.setTransaction(allSales.getTransaction());
            shopInformationBean.setUid(allSales.getUid());
            shopInformationBean.setImage(allSales.getImage());
            list.add(shopInformationBean);
        }
        model.addAttribute("shopInformationBean", list);
        return "page/personal/my_publish_product_page";
    }

    //更新商品信息
    private String getSort(int sort) {
        StringBuilder sb = new StringBuilder();
        ThirdClass thirdClass = selectThirdClassBySort(sort);
        int cid = thirdClass.getCid();
        SecondClass secondClass = selectSecondClassByCid(cid);
        int aid = secondClass.getAid();
        FirstClass firstClass = selectFirstClassByAid(aid);
        sb.append(firstClass.getName());
        sb.append("-");
        sb.append(secondClass.getName());
        sb.append("-");
        sb.append(thirdClass.getName());
        return sb.toString();
    }

    //查看用户发布的货物的总数
    private int getReleaseCounts(int uid) {
        try {
            return userReleaseService.getCounts(uid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //查看用户的求购总个数
    private int getUserWantCounts(int uid) {
        try {
            return userWantService.getCounts(uid);
        } catch (Exception e) {
            return -1;
        }
    }

    //求购列表10
    private List<UserWant> selectUserWantByUid(int uid) {
        try {
            return userWantService.selectMineByUid(uid);
        } catch (Exception e) {
            e.printStackTrace();
            List<UserWant> list = new ArrayList<>();
            list.add(new UserWant());
            return list;
        }
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

    public void save(AllSales shopInformation, UserRelease userRelease) {
        allSalesService.insertSelective(shopInformation);
        userReleaseService.insertSelective(userRelease);
    }
}
