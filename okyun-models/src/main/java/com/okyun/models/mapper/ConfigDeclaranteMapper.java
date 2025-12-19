package com.okyun.models.mapper;

import java.util.List;
import com.okyun.models.domain.ConfigDeclarante;
import org.apache.ibatis.annotations.Mapper;

/**
 * 申报人档案Mapper接口
 * 
 * @author hubiao
 * @date 2025-12-15
 */
@Mapper
public interface ConfigDeclaranteMapper 
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
     * 删除申报人档案
     * 
     * @param declaracionId 申报人档案主键
     * @return 结果
     */
    public int deleteConfigDeclaranteByDeclaracionId(Long declaracionId);

    /**
     * 批量删除申报人档案
     * 
     * @param declaracionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConfigDeclaranteByDeclaracionIds(Long[] declaracionIds);

    /**
     * 校验nif
     * @param configDeclarante
     * @return
     */
    public boolean checkNifDeclaranteUnique(ConfigDeclarante configDeclarante);

    /**
     * 查询所有申报人
     * @return
     */
    public List<ConfigDeclarante> selectDeclarantes();
}
