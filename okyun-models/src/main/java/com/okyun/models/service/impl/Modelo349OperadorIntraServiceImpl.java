package com.okyun.models.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.okyun.models.mapper.Modelo349OperadorIntraMapper;
import com.okyun.models.domain.Modelo349OperadorIntra;
import com.okyun.models.service.IModelo349OperadorIntraService;

/**
 * 349经营者记录明细Service业务层处理
 * 
 * @author hubiao
 * @date 2025-12-18
 */
@Service
public class Modelo349OperadorIntraServiceImpl implements IModelo349OperadorIntraService 
{
    @Autowired
    private Modelo349OperadorIntraMapper modelo349OperadorIntraMapper;

    /**
     * 查询349经营者记录明细
     * 
     * @param id 349经营者记录明细主键
     * @return 349经营者记录明细
     */
    @Override
    public Modelo349OperadorIntra selectModelo349OperadorIntraById(Long id)
    {
        return modelo349OperadorIntraMapper.selectModelo349OperadorIntraById(id);
    }

    /**
     * 查询349经营者记录明细列表
     * 
     * @param modelo349OperadorIntra 349经营者记录明细
     * @return 349经营者记录明细
     */
    @Override
    public List<Modelo349OperadorIntra> selectModelo349OperadorIntraList(Modelo349OperadorIntra modelo349OperadorIntra)
    {
        return modelo349OperadorIntraMapper.selectModelo349OperadorIntraList(modelo349OperadorIntra);
    }

    /**
     * 新增349经营者记录明细
     * 
     * @param modelo349OperadorIntra 349经营者记录明细
     * @return 结果
     */
    @Override
    public int insertModelo349OperadorIntra(Modelo349OperadorIntra modelo349OperadorIntra)
    {
        return modelo349OperadorIntraMapper.insertModelo349OperadorIntra(modelo349OperadorIntra);
    }

    /**
     * 修改349经营者记录明细
     * 
     * @param modelo349OperadorIntra 349经营者记录明细
     * @return 结果
     */
    @Override
    public int updateModelo349OperadorIntra(Modelo349OperadorIntra modelo349OperadorIntra)
    {
        return modelo349OperadorIntraMapper.updateModelo349OperadorIntra(modelo349OperadorIntra);
    }

    /**
     * 批量删除349经营者记录明细
     * 
     * @param ids 需要删除的349经营者记录明细主键
     * @return 结果
     */
    @Override
    public int deleteModelo349OperadorIntraByIds(Long[] ids)
    {
        return modelo349OperadorIntraMapper.deleteModelo349OperadorIntraByIds(ids);
    }

    /**
     * 删除349经营者记录明细信息
     * 
     * @param id 349经营者记录明细主键
     * @return 结果
     */
    @Override
    public int deleteModelo349OperadorIntraById(Long id)
    {
        return modelo349OperadorIntraMapper.deleteModelo349OperadorIntraById(id);
    }
}
