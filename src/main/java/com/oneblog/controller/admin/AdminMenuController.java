package com.oneblog.controller.admin;

import com.oneblog.common.Result;
import com.oneblog.entity.Menu;
import com.oneblog.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台菜单管理控制器
 */
@RestController
@RequestMapping("/admin/menu")
public class AdminMenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Result<List<Menu>> getMenuList() {
        List<Menu> menus = menuService.list();
        return Result.success(menus);
    }

    @PostMapping
    public Result<Void> saveMenu(@RequestBody Menu menu) {
        if (menu.getId() == null) {
            menu.setCreateTime(LocalDateTime.now());
        }
        menu.setUpdateTime(LocalDateTime.now());
        menuService.saveOrUpdate(menu);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.success(null);
    }
}

