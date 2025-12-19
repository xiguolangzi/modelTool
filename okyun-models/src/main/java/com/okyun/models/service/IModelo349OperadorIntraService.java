package com.okyun.models.service;

import java.util.List;
import com.okyun.models.domain.Modelo349OperadorIntra;

/**
 * 349经营者记录明细Service接口
 * 
 * @author hubiao
 * @date 2025-12-18
 */
public interface IModelo349OperadorIntraService 
{
    /**
     * 查询349经营者记录明细
     * 
     * @param id 349经营者记录明细主键
     * @return 349经营者记录明细
     */
    public Modelo349OperadorIntra selectModelo349OperadorIntraById(Long id);

    /**
     * 查询349经营者记录明细列表
     * 
     * @param modelo349OperadorIntra 349经营者记录明细
     * @return 349经营者记录明细集合
     */
    public List<Modelo349OperadorIntra> selectModelo349OperadorIntraList(Modelo349OperadorIntra modelo349OperadorIntra);

    /**
     * 新增349经营者记录明细
     * 
     * @param modelo349OperadorIntra 349经营者记录明细
     * @return 结果
     */
    public int insertModelo349OperadorIntra(Modelo349OperadorIntra modelo349OperadorIntra);

    /**
     * 修改349经营者记录明细
     * 
     * @param modelo349OperadorIntra 349经营者记录明细
     * @return 结果
     */
    public int updateModelo349OperadorIntra(Modelo349OperadorIntra modelo349OperadorIntra);

    /**
     * 批量删除349经营者记录明细
     * 
     * @param ids 需要删除的349经营者记录明细主键集合
     * @return 结果
     */
    public int deleteModelo349OperadorIntraByIds(Long[] ids);

    /**
     * 删除349经营者记录明细信息
     * 
     * @param id 349经营者记录明细主键
     * @return 结果
     */
    public int deleteModelo349OperadorIntraById(Long id);
}
