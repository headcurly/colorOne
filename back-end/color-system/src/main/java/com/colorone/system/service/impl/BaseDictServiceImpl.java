package com.colorone.system.service.impl;

import com.colorone.common.utils.TreeBuildUtils;
import com.colorone.system.domain.entity.BaseDict;
import com.colorone.system.mapper.BaseDictMapper;
import com.colorone.system.service.BaseDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author： lee
 * @email：miracleren@gmail.com
 * @date：2023/8/1
 * @备注：
 */
@Service
public class BaseDictServiceImpl implements BaseDictService {
    @Autowired
    private BaseDictMapper baseDictMapper;

    @Override
    public List<BaseDict> getDictList(BaseDict dict) {
        return baseDictMapper.selectDictList(dict);
    }

    @Override
    public Integer addBaseDict(BaseDict dict) {
        return baseDictMapper.insert(dict);
    }

    @Override
    public Integer editBaseDict(BaseDict dict) {
        return baseDictMapper.updateById(dict);
    }

    @Override
    public Integer deleteBaseDict(Long dictId) {
        int res = baseDictMapper.deleteById(dictId);
        //删除字典子表数据
        if (res > 0)
            baseDictMapper.deleteChildrenById(dictId);
        return res;
    }

    @Override
    public List<Map<String, Object>> getSelectDictByType(String type) {
        return baseDictMapper.selectComDictByType(type);
    }

    @Override
    public List<BaseDict> getBaseDictChildren(Long dictId) {
        return baseDictMapper.selectBaseDictChildren(dictId);
    }

    @Override
    public List<Map> getDictTreeList(BaseDict dict) {
        List<BaseDict> dicts = baseDictMapper.selectDictList(dict);
        dicts.addAll(baseDictMapper.selectDictChildrenList(dicts));

        List<Map> dictTree = TreeBuildUtils.init().
                setKey("dictId", "parentId", null).
                toListMap(dicts).build();

        return dictTree;
    }

}
