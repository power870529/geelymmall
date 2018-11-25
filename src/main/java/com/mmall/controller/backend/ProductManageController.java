package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest request, Product product) {

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
//            // 保存或更新商品信息
//            return iProductService.saveOrUpdateProduct(product);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {

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
//            // 更新上下架状态
//            return iProductService.setSaleStatus(productId, status);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iProductService.setSaleStatus(productId, status);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest request, Integer productId) {

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
//            // 获取产品详情
//            return iProductService.manageProductDetail(productId);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iProductService.manageProductDetail(productId);

    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

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
//            // 业务逻辑
//            return iProductService.getProductList(pageNum, pageSize);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iProductService.getProductList(pageNum, pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest request, String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

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
//            // 业务逻辑
//            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        return iProductService.searchProduct(productName, productId, pageNum, pageSize);
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
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
//            // 业务逻辑
//            // upload文件夹和WEB-INFO同级
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file, path);
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//
//            Map fileMap = Maps.newHashMap();
//            fileMap.put("uri", targetFileName);
//            fileMap.put("url", url);
//            return ServerResponse.createBySuccess(fileMap);
//        } else {
//            return ServerResponse.createBySuccess("无权限，需要管理员权限");
//        }

        // 全部通过拦截器验证是否登录以及权限
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.createBySuccess(fileMap);

    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(@RequestParam(value = "upload_file", required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response) {
//         富文本中对于返回值有自己的要求，这里使用的是simditor，返回值要求为：
//        {
//            "success"   : true/false,
//            "msg"       : "error massage", #optional
//            "file_path" : "[real file path]"
//        }
        Map resultMap = Maps.newHashMap();
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isEmpty(loginToken)) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "用户未登录");
//            return resultMap;
//        }
//        String userInfo = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userInfo, User.class);
//
//        if (user == null) {
//            resultMap.put("success", false);
//            resultMap.put("msg", "用户未登录");
//            return resultMap;
//        }
//
//        // 校验是否是管理员
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            // 业务逻辑
//            // upload文件夹和WEB-INFO同级
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file, path);
//            if (StringUtils.isBlank(targetFileName)) {
//                resultMap.put("success", false);
//                resultMap.put("msg", "上传失败");
//                return resultMap;
//            }
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//
//            resultMap.put("success", true);
//            resultMap.put("msg", "上传成功");
//            resultMap.put("file_path", url);
//            // simditor要求
//            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
//            return resultMap;
//        } else {
//            resultMap.put("success", false);
//            resultMap.put("msg", "无权限，需要管理员权限");
//            return resultMap;
//        }

        // 全部通过拦截器验证是否登录以及权限
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        // simditor要求
        response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
        return resultMap;

    }
}











