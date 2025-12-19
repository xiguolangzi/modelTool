package com.okyun.models.mapper;

import java.util.List;
import com.okyun.models.domain.ConfigPaisesUe;
import org.apache.ibatis.annotations.Mapper;

/**
 * 欧盟国家代码配置Mapper接口
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@Mapper
public interface ConfigPaisesUeMapper 
{
    /**
     * 查询欧盟国家代码配置
     * 
     * @param id 欧盟国家代码配置主键
     * @return 欧盟国家代码配置
     */
    public ConfigPaisesUe selectConfigPaisesUeById(Long id);

    /**
     * 查询欧盟国家代码配置列表
     * 
     * @param configPaisesUe 欧盟国家代码配置
     * @return 欧盟国家代码配置集合
     */
    public List<ConfigPaisesUe> selectConfigPaisesUeList(ConfigPaisesUe configPaisesUe);

    /**
     * 新增欧盟国家代码配置
     * 
     * @param configPaisesUe 欧盟国家代码配置
     * @return 结果
     */
    public int insertConfigPaisesUe(ConfigPaisesUe configPaisesUe);

    /**
     * 修改欧盟国家代码配置
     * 
     * @param configPaisesUe 欧盟国家代码配置
     * @return 结果
     */
    public int updateConfigPaisesUe(ConfigPaisesUe configPaisesUe);

    /**
     * 删除欧盟国家代码配置
     * 
     * @param id 欧盟国家代码配置主键
     * @return 结果
     */
    public int deleteConfigPaisesUeById(Long id);

    /**
     * 批量删除欧盟国家代码配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConfigPaisesUeByIds(List<Long> ids);

    /**
     * 校验国家代码
     * @param configPaisesUe
     * @return
     */
    public boolean checkCodigoPaisUnique(ConfigPaisesUe configPaisesUe);

    /**
     * 根据国家代码查询
     * @param codigoPais
     * @return
     */
    public ConfigPaisesUe selectConfigPaisesUeByCodigoPais(String codigoPais);
}
