package com.okyun.models.mapper;

import java.util.List;
import com.okyun.models.domain.Modelo349Declarante;
import com.okyun.models.domain.Modelo349OperadorIntra;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 349模型申报人主Mapper接口
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@Mapper
public interface Modelo349DeclaranteMapper 
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
    public int insertModelo349Declarante(Modelo349Declarante modelo349Declarante);

    /**
     * 修改349模型申报人主
     * 
     * @param modelo349Declarante 349模型申报人主
     * @return 结果
     */
    public int updateModelo349Declarante(Modelo349Declarante modelo349Declarante);

    /**
     * 删除349模型申报人主
     * 
     * @param id 349模型申报人主主键
     * @return 结果
     */
    public int deleteModelo349DeclaranteById(Long id);

    /**
     * 批量删除349模型申报人主
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteModelo349DeclaranteByIds(Long[] ids);

    /**
     * 批量删除349模型欧盟内经营者记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteModelo349OperadorIntraByDeclaranteIds(Long[] ids);
    
    /**
     * 批量新增349模型欧盟内经营者记录
     * 
     * @param modelo349OperadorIntraList 349模型欧盟内经营者记录列表
     * @return 结果
     */
    public int batchModelo349OperadorIntra(List<Modelo349OperadorIntra> modelo349OperadorIntraList);
    

    /**
     * 通过349模型申报人主主键删除349模型欧盟内经营者记录信息
     * 
     * @param id 349模型申报人主ID
     * @return 结果
     */
    public int deleteModelo349OperadorIntraByDeclaranteId(Long id);

    /**
     * 检查税务识别的唯一
     * @param modelo349Declarante
     * @return
     */
    public boolean numeroIdentificativo(Modelo349Declarante modelo349Declarante);

    /**
     * 根据申报的识别号修改状态
     * @param numeroIdentificativo
     * @param estado
     */
    public void updateStatusByNumeroDeclaracion(@Param("numeroIdentificativo") String numeroIdentificativo, @Param("estado") String estado);
}
