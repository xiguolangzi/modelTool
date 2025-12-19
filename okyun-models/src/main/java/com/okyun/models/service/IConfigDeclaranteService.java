package com.okyun.models.service;

import java.util.List;
import com.okyun.models.domain.ConfigDeclarante;

/**
 * 申报人档案Service接口
 * 
 * @author hubiao
 * @date 2025-12-15
 */
public interface IConfigDeclaranteService 
{
    /**
     * 查询申报人档案
     * 
     * @param declaracionId 申报人档案主键
     * @return 申报人档案
     */
    public ConfigDeclarante selectConfigDeclaranteByDeclaracionId(Long declaracionId);

    /**
     * 查询申报人档案列表
     * 
     * @param configDeclarante 申报人档案
     * @return 申报人档案集合
     */
    public List<ConfigDeclarante> selectConfigDeclaranteList(ConfigDeclarante configDeclarante);

    /**
     * 新增申报人档案
     * 
     * @param configDeclarante 申报人档案
     * @return 结果
     */
    public int insertConfigDeclarante(ConfigDeclarante configDeclarante);

    /**
     * 修改申报人档案
     * 
     * @param configDeclarante 申报人档案
     * @return 结果
     */
    public int updateConfigDeclarante(ConfigDeclarante configDeclarante);

    /**
     * 批量删除申报人档案
     * 
     * @param declaracionIds 需要删除的申报人档案主键集合
     * @return 结果
     */
    public int deleteConfigDeclaranteByDeclaracionIds(Long[] declaracionIds);

    /**
     * 删除申报人档案信息
     * 
     * @param declaracionId 申报人档案主键
     * @return 结果
     */
    public int deleteConfigDeclaranteByDeclaracionId(Long declaracionId);

    /**
     * 查询所有申报人
     * @return
     */
    public List<ConfigDeclarante> selectDeclarantes();
}
