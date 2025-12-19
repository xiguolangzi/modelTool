package com.okyun.models.service;

import java.util.List;
import com.okyun.models.domain.Modelo349Declarante;

/**
 * 349模型申报人主Service接口
 * 
 * @author hubiao
 * @date 2025-12-14
 */
public interface IModelo349DeclaranteService 
{
    /**
     * 查询349模型申报人主
     * 
     * @param id 349模型申报人主主键
     * @return 349模型申报人主
     */
    public Modelo349Declarante selectModelo349DeclaranteById(Long id);

    /**
     * 查询349模型申报人主列表
     * 
     * @param modelo349Declarante 349模型申报人主
     * @return 349模型申报人主集合
     */
    public List<Modelo349Declarante> selectModelo349DeclaranteList(Modelo349Declarante modelo349Declarante);

    /**
     * 新增349模型申报人主
     * 
     * @param modelo349Declarante 349模型申报人主
     * @return 结果
     */
    public Modelo349Declarante insertModelo349Declarante(Modelo349Declarante modelo349Declarante);

    /**
     * 修改349模型申报人主
     * 
     * @param modelo349Declarante 349模型申报人主
     * @return 结果
     */
    public Modelo349Declarante updateModelo349Declarante(Modelo349Declarante modelo349Declarante);

    /**
     * 批量删除349模型申报人主
     * 
     * @param ids 需要删除的349模型申报人主主键集合
     * @return 结果
     */
    public int deleteModelo349DeclaranteByIds(Long[] ids);

    /**
     * 删除349模型申报人主信息
     * 
     * @param id 349模型申报人主主键
     * @return 结果
     */
    public int deleteModelo349DeclaranteById(Long id);
}
