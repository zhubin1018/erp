package com.bin.system.controller;

import com.bin.system.common.ActiveUser;
import com.bin.system.common.ResultObj;
import com.bin.system.domain.Notice;
import com.bin.system.service.NoticeService;
import com.bin.system.vo.NoticeVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 朱彬
 * @date 2020/3/19 10:56
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;


    /**
     *查询
     * @params [noticeVo]
     * @return java.lang.Object
     */
    @RequestMapping("/loadAllNotice")
    public Object loadAllNotrice(NoticeVo noticeVo){
        return noticeService.queryAllNotice(noticeVo);
    }

    /**
     *添加
     * @params [notice]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/addNotice")
    public ResultObj addNotice(Notice notice){
        try {
            Subject subject = SecurityUtils.getSubject();
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            notice.setOpername(activeUser.getUser().getName());
            notice.setCreatetime(new Date());
            noticeService.save(notice);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }

    }

    /**
     *修改
     * @params [notice]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("/updateNotice")
    public ResultObj updateNotice(Notice notice){
        try {
            noticeService.updateById(notice);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     *删除
     * @params [id]
     * @return com.bin.system.common.ResultObj
     */
    @RequestMapping("deleteNotice")
    public ResultObj deleteLoginfo(Integer id){
        try {
            noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeleteNotice(Integer[] ids){
        try {
            if (ids!=null&&ids.length>0){
                List<Integer> list = Arrays.asList(ids);
                noticeService.removeByIds(list);
                return ResultObj.DELETE_SUCCESS;
            }else {
                return new ResultObj(-1,"传入ID不能为空");
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_SUCCESS;
        }
    }
}
