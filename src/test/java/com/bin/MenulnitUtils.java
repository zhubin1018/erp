package com.bin;

import com.bin.system.domain.Menu;
import com.bin.system.service.MenuService;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class MenulnitUtils {
    @Autowired
    private MenuService menuService;

    @Test
    void initMenu() {
        menuService.save(new Menu(1, 0, "topmenu", "business", "业务管理", "fa fa-fax", "", null, 0, 1, 1));
        menuService.save(new Menu(2, 0, "topmenu", "system", "系统管理", "fa fa-cog", "", null, 0, 2, 1));

        menuService.save(new Menu(3, 1, "leftmenu", "business", "基础数据管理", "fa fa-cog", "", null, 0, 3, 1));
        menuService.save(new Menu(4, 1, "leftmenu", "business", "进货管理", "fa fa-cog", "", null, 0, 4, 1));
        menuService.save(new Menu(5, 1, "leftmenu", "business", "销售管理", "fa fa-cog", "", null, 0, 5, 1));

        menuService.save(new Menu(6, 3, "leftmenu", "business", "客户管理", "fa fa-cog", "", null, 0, 6, 1));
        menuService.save(new Menu(7, 3, "leftmenu", "business", "供应商管理", "fa fa-cog", "", null, 0, 7, 1));
        menuService.save(new Menu(8, 3, "leftmenu", "business", "商品管理", "fa fa-cog", "", null, 0, 8, 1));

        menuService.save(new Menu(9, 4, "leftmenu", "business", "商品进货", "fa fa-cog", "", null, 0, 9, 1));
        menuService.save(new Menu(10, 4, "leftmenu", "business", "商品退货", "fa fa-cog", "", null, 0, 10, 1));

        menuService.save(new Menu(11, 5, "leftmenu", "business", "商品销售", "fa fa-cog", "", null, 0, 11, 1));
        menuService.save(new Menu(12, 5, "leftmenu", "business", "销售退货", "fa fa-cog", "", null, 0, 12, 1));


        menuService.save(new Menu(13, 2, "leftmenu", "system", "系统管理", "fa fa-cog", "", null, 0, 13, 1));
        menuService.save(new Menu(14, 2, "leftmenu", "system", "其它管理", "fa fa-cog", "", null, 0, 14, 1));

        menuService.save(new Menu(15, 13, "leftmenu", "system", "部门管理", "fa fa-cog", "", null, 0, 15, 1));
        menuService.save(new Menu(16, 13, "leftmenu", "system", "菜单管理", "fa fa-cog", "", null, 0, 16, 1));
        menuService.save(new Menu(17, 13, "leftmenu", "system", "角色管理", "fa fa-cog", "", null, 0, 17, 1));
        menuService.save(new Menu(18, 13, "leftmenu", "system", "用户管理", "fa fa-cog", "", null, 0, 18, 1));

        menuService.save(new Menu(19, 14, "leftmenu", "system", "登录日志", "fa fa-cog", "", null, 0, 19, 1));
        menuService.save(new Menu(20, 14, "leftmenu", "system", "数据源监控", "fa fa-cog", "", null, 0, 20, 1));
        menuService.save(new Menu(21, 14, "leftmenu", "system", "系统公告", "fa fa-cog", "", null, 0, 21, 1));
        menuService.save(new Menu(22, 14, "leftmenu", "system", "图标管理", "fa fa-cog", "", null, 0, 22, 1));

        System.out.println("初始化成功");
    }

    //权限
    @Test
    void initPermisstion() {
        menuService.save(new Menu(23, 6, "permission", "customer:query", "客户查询", 23, 1));
        menuService.save(new Menu(24, 6, "permission", "customer:add", "客户添加", 24, 1));
        menuService.save(new Menu(25, 6, "permission", "customer:delete", "客户删除", 25, 1));
        menuService.save(new Menu(26, 6, "permission", "customer:update", "客户更新", 26, 1));
    }


    @Test
    void test() {
        String[] strs = {"dbg", "dev", "dbv"};
        int len = strs.length;
        StringBuilder res = new StringBuilder();
        Arrays.sort(strs);
        for (String str : strs) {
            System.out.println(str);
        }
        int m = strs[0].length();
        int n = strs[len - 1].length();
        int num = Math.min(m, n);
        for (int i = 0; i < num; i++) {
            if (strs[0].charAt(i) == strs[len - 1].charAt(i)) {
                res.append(strs[0].charAt(i));
            } else break;
        }
        System.out.println("================"+res.toString());

    }

}
