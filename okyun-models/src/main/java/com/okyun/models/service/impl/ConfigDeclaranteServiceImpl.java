package com.okyun.models.service.impl;

import java.util.List;
import com.okyun.common.utils.DateUtils;
import com.okyun.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.okyun.models.mapper.ConfigDeclaranteMapper;
import com.okyun.models.domain.ConfigDeclarante;
import com.okyun.models.service.IConfigDeclaranteService;

/**
 * 申报人档案Service业务层处理
 * 
 * @author hubiao
 * @date 2025-12-15
 */
@Service
public class ConfigDeclaranteServiceImpl implements IConfigDeclaranteService 
{
    @Autowired
    private ConfigDeclaranteMapper configDeclaranteMapper;

    /**
     * 查询申报人档案
     * 
     * @param declaracionId 申报人档案主键
     * @return 申报人档案
     */
    @Override
    public ConfigDeclarante selectConfigDeclaranteByDeclaracionId(Long declaracionId)
    {
        return configDeclaranteMapper.selectConfigDeclaranteByDeclaracionId(declaracionId);
    }

    /**
     * 查询申报人档案列表
     * 
     * @param configDeclarante 申报人档案
     * @return 申报人档案
     */
    @Override
    public List<ConfigDeclarante> selectConfigDeclaranteList(ConfigDeclarante configDeclarante)
    {
        return configDeclaranteMapper.selectConfigDeclaranteList(configDeclarante);
    }

    /**
     * 新增申报人档案
     * 
     * @param configDeclarante 申报人档案
     * @return 结果
     */
    @Override
    public int insertConfigDeclarante(ConfigDeclarante configDeclarante)
    {
        configDeclarante.setCreateTime(DateUtils.getNowDate());
        configDeclarante.setCreateBy(SecurityUtils.getUsername());
        // 检验唯一
        checkUnique(configDeclarante);
        // 大写空格处理
        uppercaseSpacehandling(configDeclarante);

        return configDeclaranteMapper.insertConfigDeclarante(configDeclarante);
    }

    private void uppercaseSpacehandling(ConfigDeclarante configDeclarante) {
        // 检验NIF
        if (configDeclarante.getNifDeclarante() != null){
            // 去除前后空格
            configDeclarante.setNifDeclarante(configDeclarante.getNifDeclarante().trim());
            // 如果含有小写字母转成大写字母
            configDeclarante.setNifDeclarante(configDeclarante.getNifDeclarante().toUpperCase());
        }
        // 检验名称
        if (configDeclarante.getNombreDeclarante() != null){
            // 去除前后空格
            configDeclarante.setNombreDeclarante(configDeclarante.getNombreDeclarante().trim());
            // 如果含有小写字母转成大写字母
            configDeclarante.setNombreDeclarante(configDeclarante.getNombreDeclarante().toUpperCase());
        }
        // 检验联系电话
        if (configDeclarante.getTelefonoContacto() != null){
            // 去除前后空格
            configDeclarante.setTelefonoContacto(configDeclarante.getTelefonoContacto().trim());
        }
        // 检验联系人
        if (configDeclarante.getPersonaContacto() != null){
            // 去除前后空格
            configDeclarante.setPersonaContacto(configDeclarante.getPersonaContacto().trim());
            // 如果含有小写字母转成大写字母
            configDeclarante.setPersonaContacto(configDeclarante.getPersonaContacto().toUpperCase());
        }
        // 检验代表
        if (configDeclarante.getNifRepresentanteLegal() != null){
            // 去除前后空格
            configDeclarante.setNifRepresentanteLegal(configDeclarante.getNifRepresentanteLegal().trim());
            // 如果含有小写字母转成大写字母
            configDeclarante.setNifRepresentanteLegal(configDeclarante.getNifRepresentanteLegal().toUpperCase());
        }
    }

    // 检验唯一
    private void checkUnique(ConfigDeclarante configDeclarante) {
        if (configDeclaranteMapper.checkNifDeclaranteUnique(configDeclarante)){
            throw new RuntimeException("NIF已经存在");
        }
    }

    /**
     * 修改申报人档案
     * 
     * @param configDeclarante 申报人档案
     * @return 结果
     */
    @Override
    public int updateConfigDeclarante(ConfigDeclarante configDeclarante)
    {
        configDeclarante.setUpdateTime(DateUtils.getNowDate());
        configDeclarante.setUpdateBy(SecurityUtils.getUsername());
        // 检验唯一
        checkUnique(configDeclarante);
        // 大写空格处理
        uppercaseSpacehandling(configDeclarante);
        return configDeclaranteMapper.updateConfigDeclarante(configDeclarante);
    }

    /**
     * 批量删除申报人档案
     * 
     * @param declaracionIds 需要删除的申报人档案主键
     * @return 结果
     */
    @Override
    public int deleteConfigDeclaranteByDeclaracionIds(Long[] declaracionIds)
    {
        if (declaracionIds == null || declaracionIds.length == 0){
            return 1;
        }
        return configDeclaranteMapper.deleteConfigDeclaranteByDeclaracionIds(declaracionIds);
    }

    /**
     * 删除申报人档案信息
     * 
     * @param declaracionId 申报人档案主键
     * @return 结果
     */
    @Override
    public int deleteConfigDeclaranteByDeclaracionId(Long declaracionId)
    {
        if (declaracionId == null){
            return 1;
        }
        return configDeclaranteMapper.deleteConfigDeclaranteByDeclaracionId(declaracionId);
    }

    /**
     * 查询申报人列表
     * @return
     */
    @Override
    public List<ConfigDeclarante> selectDeclarantes() {

        return configDeclaranteMapper.selectDeclarantes();
    }
}
