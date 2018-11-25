package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse<String> addCategory(HttpServletRequest request, String categoryName,
                                              @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByError("用户未登录，无法获取用户信息");
//        }
//        String userInfo = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userInfo, User.class);
//        ;
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            // 添加品类
//            return iCategoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }
        // 全部通过拦截器验证是否登录以及权限
        return iCategoryService.addCategory(categoryName, parentId);

    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse<String> setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByError("用户未登录，无法获取用户信息");
//        }
//        String userInfo = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userInfo, User.class);
//
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            // 更新品类名称
//            return iCategoryService.setCategoryName(categoryId, categoryName);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iCategoryService.setCategoryName(categoryId, categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByError("用户未登录，无法获取用户信息");
//        }
//        String userInfo = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userInfo, User.class);
//
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            // 查询子节点category信息，不递归。
//            return iCategoryService.getChildrenParallelCategory(categoryId);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse selectCategoryAndChildrenById(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByError("用户未登录，无法获取用户信息");
//        }
//        String userInfo = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userInfo, User.class);
//
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
//        }
//
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            // 查询子节点category信息，不递归。
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}











