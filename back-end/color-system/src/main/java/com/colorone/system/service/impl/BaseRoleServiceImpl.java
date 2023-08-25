package com.colorone.system.service.impl;

import com.colorone.common.utils.SecurityUtils;
import com.colorone.system.domain.entity.BaseRole;
import com.colorone.system.domain.entity.BaseRoleMenu;
import com.colorone.system.mapper.BaseRoleMapper;
import com.colorone.system.mapper.BaseRoleMenuMapper;
import com.colorone.system.service.BaseRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author： lee
 * @email：miracleren@gmail.com
 * @date：2023/8/24
 * @备注：
 */
@Service
public class BaseRoleServiceImpl implements BaseRoleService {
    @Autowired
    private BaseRoleMapper baseRoleMapper;

    @Autowired
    private BaseRoleMenuMapper baseRoleMenuMapper;

    @Override
    public List<BaseRole> getBaseRoleList(BaseRole role) {
        return baseRoleMapper.selectBaseRoleList(role);
    }

    @Override
    public Integer addBaseRole(BaseRole role) {
        int i = baseRoleMapper.insert(role);
        if (i > 0 && role.getParams().containsKey("permits")) {
            List<Long> permits = (List<Long>) role.getParams().get("permits");
            for (Long p : permits) {
                BaseRoleMenu roleMenu = new BaseRoleMenu();
                roleMenu.setRoleId(role.getRoleId());
                roleMenu.setMenuId(p);
                i += baseRoleMenuMapper.insert(roleMenu);
            }
        }
        return i;
    }

    @Override
    public Integer editBaseRole(BaseRole role) {
        int i = baseRoleMapper.updateById(role);
        //清除角色关联菜单权限数据
        baseRoleMenuMapper.deleteMenuByRoleId(role.getRoleId(), SecurityUtils.getUsername());
        //新增或更新存在的关联数据
        if (i > 0 && role.getParams().containsKey("permits")) {
            List<Long> permits = (List<Long>) role.getParams().get("permits");

            for (Long p : permits) {
                BaseRoleMenu roleMenu = new BaseRoleMenu();
                roleMenu.setRoleId(role.getRoleId());
                roleMenu.setMenuId(p);
                roleMenu.setUpdateBy(SecurityUtils.getUsername());
                int u = baseRoleMenuMapper.updateDelRoleMenuExist(roleMenu);
                if (u == 0)
                    baseRoleMenuMapper.insert(roleMenu);
            }
        }
        return i;
    }

    @Override
    public Integer deleteBaseRole(Long roleId) {
        int i = baseRoleMapper.deleteById(roleId);
        if (i > 0) {
            baseRoleMenuMapper.deleteMenuByRoleId(roleId, SecurityUtils.getUsername());
        }
        return i;
    }

    @Override
    public List<Integer> getRoleMenuIds(Long roleId) {
        return baseRoleMenuMapper.selectRoleMenuIds(roleId);
    }
}
