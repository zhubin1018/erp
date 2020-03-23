package com.bin.system.controller;

import com.bin.system.common.ActiveUser;
import com.bin.system.common.DataGridView;
import com.bin.system.common.upload.UploadProperties;
import com.bin.system.common.upload.UploadService;
import com.bin.system.domain.User;
import com.bin.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱彬
 * @date 2020/3/22 0:53
 */
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private UploadService uploadService;

    @Autowired
    private UserService userService;

    @Autowired
    private UploadProperties uploadProperties;

    @RequestMapping("/uploadFile")
    public Object uploadFile(MultipartFile mf){
        String path = this.uploadService.uploadImage(mf);
        System.out.println("path = " + path);
        Map<String,String> map = new HashMap<>();
        map.put("src",path);
        //更新数据库
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        User user = activeUser.getUser();
        user.setImgpath(path);
        userService.update(user);
        return new DataGridView(map);
    }

    /**
     *上传文件
     * @params [mf]
     * @return java.lang.Object
     */
    @RequestMapping("/uploadGoodsFile")
    public Object uploadGoodsFile(MultipartFile mf){
        String path = this.uploadService.uploadImage(mf);
        System.out.println("path = " + path);
        Map<String,String> map = new HashMap<>();
        map.put("src",path);

        return new DataGridView(map);
    }
}
