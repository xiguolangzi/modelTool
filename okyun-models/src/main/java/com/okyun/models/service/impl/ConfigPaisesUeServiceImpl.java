package com.okyun.models.service.impl;

import java.util.List;

import com.okyun.common.exception.ServiceException;
import com.okyun.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.okyun.models.mapper.ConfigPaisesUeMapper;
import com.okyun.models.domain.ConfigPaisesUe;
import com.okyun.models.service.IConfigPaisesUeService;

/**
 * 欧盟国家代码配置Service业务层处理
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@Service
public class ConfigPaisesUeServiceImpl implements IConfigPaisesUeService 
{
    @Autowired
    private ConfigPaisesUeMapper configPaisesUeMapper;

    /**
     * 查询欧盟国家代码配置
     * 
     * @param id 欧盟国家代码配置主键
     * @return 欧盟国家代码配置
     */
    @Override
    public ConfigPaisesUe selectConfigPaisesUeById(Long id)
    {
        return configPaisesUeMapper.selectConfigPaisesUeById(id);
    }

    /**
     * 查询欧盟国家代码配置列表
     * 
     * @param configPaisesUe 欧盟国家代码配置
     * @return 欧盟国家代码配置
     */
    @Override
    public List<ConfigPaisesUe> selectConfigPaisesUeList(ConfigPaisesUe configPaisesUe)
    {
        return configPaisesUeMapper.selectConfigPaisesUeList(configPaisesUe);
    }

    /**
     * 新增欧盟国家代码配置
     * 
     * @param configPaisesUe 欧盟国家代码配置
     * @return 结果
     */
    @Override
    public int insertConfigPaisesUe(ConfigPaisesUe configPaisesUe)
    {
        // 检查国家编码唯一
        checkUnique(configPaisesUe);
        // 时间
        configPaisesUe.setFechaActualizacion(DateUtils.getNowDate());
        return configPaisesUeMapper.insertConfigPaisesUe(configPaisesUe);
    }

    /**
     * 检查国家编码唯一
     * @param configPaisesUe
     */
    private void checkUnique(ConfigPaisesUe configPaisesUe) {
        if (configPaisesUeMapper.checkCodigoPaisUnique(configPaisesUe)){
            throw new ServiceException("国家编码已存在");
        }
    }

    /**
     * 修改欧盟国家代码配置
     * 
     * @param configPaisesUe 欧盟国家代码配置
     * @return 结果
     */
    @Override
    public int updateConfigPaisesUe(ConfigPaisesUe configPaisesUe)
    {
        checkUnique(configPaisesUe);
        // 时间
        configPaisesUe.setFechaActualizacion(DateUtils.getNowDate());
        return configPaisesUeMapper.updateConfigPaisesUe(configPaisesUe);
    }

    /**
     * 批量删除欧盟国家代码配置
     * 
     * @param ids 需要删除的欧盟国家代码配置主键
     * @return 结果
     */
    @Override
    public int deleteConfigPaisesUeByIds(Long[] ids)
    {
        if (ids == null || ids.length == 0){
            return 1;
        }
        List<Long> idList = java.util.Arrays.asList(ids);
        return configPaisesUeMapper.deleteConfigPaisesUeByIds(idList);
    }

    /**
     * 删除欧盟国家代码配置信息
     * 
     * @param id 欧盟国家代码配置主键
     * @return 结果
     */
    @Override
    public int deleteConfigPaisesUeById(Long id)
    {
        if (id == null){
            return 1;
        }
        return configPaisesUeMapper.deleteConfigPaisesUeById(id);
    }

    @Override
    public ConfigPaisesUe selectConfigPaisesUeByCodigoPais(String codigoPais) {
        return configPaisesUeMapper.selectConfigPaisesUeByCodigoPais(codigoPais);
    }
}
