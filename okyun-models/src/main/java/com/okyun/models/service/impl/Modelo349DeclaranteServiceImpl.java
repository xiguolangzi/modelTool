package com.okyun.models.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.okyun.common.exception.ServiceException;
import com.okyun.common.utils.BigDecimalUtils;
import com.okyun.common.utils.DateUtils;
import com.okyun.common.utils.SecurityUtils;
import com.okyun.models.constants.Model349Constants;
import com.okyun.models.domain.ConfigPaisesUe;
import com.okyun.models.service.IConfigPaisesUeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import com.okyun.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.okyun.models.domain.Modelo349OperadorIntra;
import com.okyun.models.mapper.Modelo349DeclaranteMapper;
import com.okyun.models.domain.Modelo349Declarante;
import com.okyun.models.service.IModelo349DeclaranteService;
import org.springframework.util.CollectionUtils;

/**
 * 349模型申报人主Service业务层处理
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@Service
@Slf4j
public class Modelo349DeclaranteServiceImpl implements IModelo349DeclaranteService 
{
    @Autowired
    private Modelo349DeclaranteMapper modelo349DeclaranteMapper;

    @Autowired
    private IConfigPaisesUeService configPaisesUeService;

    /**
     * 查询349模型申报人主
     * 
     * @param id 349模型申报人主主键
     * @return 349模型申报人主
     */
    @Override
    public Modelo349Declarante selectModelo349DeclaranteById(Long id)
    {
        return modelo349DeclaranteMapper.selectModelo349DeclaranteById(id);
    }

    /**
     * 查询349模型申报人主列表
     * 
     * @param modelo349Declarante 349模型申报人主
     * @return 349模型申报人主
     */
    @Override
    public List<Modelo349Declarante> selectModelo349DeclaranteList(Modelo349Declarante modelo349Declarante)
    {
        return modelo349DeclaranteMapper.selectModelo349DeclaranteList(modelo349Declarante);
    }

    /**
     * 新增349模型申报人主
     * 
     * @param modelo349Declarante 349模型申报人主
     * @return 结果
     */
    @Transactional
    @Override
    public Modelo349Declarante insertModelo349Declarante(Modelo349Declarante modelo349Declarante)
    {
        // 处理基础数据的方法
        handleBaseData(modelo349Declarante);

        modelo349Declarante.setCreateTime(DateUtils.getNowDate());
        modelo349Declarante.setCreateBy(SecurityUtils.getUsername());

        // 检查税务识别的唯一
        checkUnique(modelo349Declarante);

        int rows = modelo349DeclaranteMapper.insertModelo349Declarante(modelo349Declarante);
        insertModelo349OperadorIntra(modelo349Declarante);
        if (rows > 0){
            if (Model349Constants.DECLARACION_TIPO_COMPLEMENTARIO.equals(modelo349Declarante.getDeclaracionTipo())){
                // 获取迁移申请识别号
                String numeroDeclaracionAnterior = modelo349Declarante.getNumeroDeclaracionAnterior();
                // 修改迁移识别号的状态为已更正
                modelo349DeclaranteMapper.updateStatusByNumeroDeclaracion(numeroDeclaracionAnterior, Model349Constants.DECLARACION_STATUS_CORRECT);
            }
            if (Model349Constants.DECLARACION_TIPO_SUSTITUTIVO.equals(modelo349Declarante.getDeclaracionTipo())){
                // 获取迁移申请识别号
                String numeroDeclaracionAnterior = modelo349Declarante.getNumeroDeclaracionAnterior();
                // 修改迁移识别号的状态为已替换
                modelo349DeclaranteMapper.updateStatusByNumeroDeclaracion(numeroDeclaracionAnterior, Model349Constants.DECLARACION_STATUS_REPLACE);
            }
            return modelo349DeclaranteMapper.selectModelo349DeclaranteById(modelo349Declarante.getId());
        } else {
            throw new ServiceException("新增349模型申报人失败");
        }
    }

    // 检查税务识别的唯一
    private void checkUnique(Modelo349Declarante modelo349Declarante) {
        if (Model349Constants.DEFAULT_NUMERO_IDENTIFICATIVO.equals(modelo349Declarante.getNumeroIdentificativo())){
            return;
        }
        if (modelo349DeclaranteMapper.numeroIdentificativo(modelo349Declarante)){
            throw new RuntimeException("税务识别号: [" + modelo349Declarante.getNumeroIdentificativo() + "] 已存在！");
        }
    }

    // 处理基础数据
    private void handleBaseData(Modelo349Declarante modelo349Declarante) {
        // 0 记录明细不能为空
        List<Modelo349OperadorIntra> modelo349OperadorIntraList = modelo349Declarante.getModelo349OperadorIntraList();
        if (CollectionUtils.isEmpty(modelo349OperadorIntraList)){
            throw new RuntimeException("349模型申报人主记录明细不能为空");
        }
        // 1 记录类型，固定为1
        modelo349Declarante.setTipoRegistro(Model349Constants.DECLARANTE_DEFAULT_TIPO_REGISTRO);
        // 2 申报模型，固定为349
        modelo349Declarante.setModeloDeclaracion(Model349Constants.DEFAULT_MODELO_349);
        // 3 申报识别号，13位数字(前三位是349)
        if (modelo349Declarante.getNumeroIdentificativo() == null){
            // 初始
            modelo349Declarante.setNumeroIdentificativo(Model349Constants.DEFAULT_NUMERO_IDENTIFICATIVO);
        } else {
            // 检查是否位13位且开头三位是349
            if (modelo349Declarante.getNumeroIdentificativo().length() != 13 || !modelo349Declarante.getNumeroIdentificativo().startsWith("349")){
                throw new RuntimeException("申报识别号，13位数字(前三位是349)");
            }
        }
        // 申报人姓名或公司名称 去除前后空格
        modelo349Declarante.setNombreDeclarante(StringUtils.trim(modelo349Declarante.getNombreDeclarante()));
        // 联系人姓名 去除前后空格
        modelo349Declarante.setPersonaContacto(StringUtils.trim(modelo349Declarante.getPersonaContacto()));
        // 4 如果申报类型不是空，则判断必须填写前一申报识别号
        if (StringUtils.isNotEmpty(modelo349Declarante.getDeclaracionTipo())){
            if (Model349Constants.DECLARACION_TIPO_COMPLEMENTARIO.equals(modelo349Declarante.getDeclaracionTipo()) || Model349Constants.DECLARACION_TIPO_SUSTITUTIVO.equals(modelo349Declarante.getDeclaracionTipo())){
                if (modelo349Declarante.getNumeroDeclaracionAnterior() == null){
                    throw new RuntimeException("需要填写前一申报识别号");
                }
            } else {
                throw new RuntimeException("未知的申报类型");
            }
        }
        // 5 汇总金额判断
        if (Model349Constants.isCorrector(modelo349Declarante.getDeclaracionTipo())){
            // 更正申报
            // 更正申报金额判断
            BigDecimal importeRectificaciones = BigDecimalUtils.safeValue(modelo349Declarante.getImporteRectificaciones()) ;
            if (importeRectificaciones.compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("需要修正的交易金额不能小于0");
            }
            // 更正申报数量判断
            Integer totalOperadoresRectificaciones = modelo349Declarante.getTotalOperadoresRectificaciones();
            if (totalOperadoresRectificaciones <= 0){
                throw new RuntimeException("需要修正的申报数量不能小于0");
            }
            // 更正明细校验
            BigDecimal sumBaseImponibleRectificada = BigDecimal.ZERO;
            for (Modelo349OperadorIntra modelo349OperadorIntra : modelo349OperadorIntraList) {
                BigDecimal baseImponibleRectificada = BigDecimalUtils.safeValue(modelo349OperadorIntra.getBaseImponibleRectificada());
                if (baseImponibleRectificada.compareTo(BigDecimal.ZERO) <= 0){
                    throw new RuntimeException("需要修正的申报金额不能小于0");
                }
                sumBaseImponibleRectificada = sumBaseImponibleRectificada.add(baseImponibleRectificada);
            }
            if (sumBaseImponibleRectificada.compareTo(importeRectificaciones) != 0){
                throw new RuntimeException("需要修正的申报金额与明细金额不一致");
            }

        } else {
            // 正常申报
            // 申报金额判断
            BigDecimal importeOperaciones = BigDecimalUtils.safeValue(modelo349Declarante.getImporteOperaciones()) ;
            if (importeOperaciones.compareTo(BigDecimal.ZERO) <= 0){
                throw new RuntimeException("交易金额不能小于0");
            }
            // 申报数量判断
            Integer totalOperadoresIntracomunitarios = modelo349Declarante.getTotalOperadoresIntracomunitarios();
            if (totalOperadoresIntracomunitarios <= 0){
                throw new RuntimeException("申报明细的数量不能小于0");
            }
            // 明细校验
            BigDecimal sumBaseImponible = BigDecimal.ZERO;
            for (Modelo349OperadorIntra modelo349OperadorIntra : modelo349OperadorIntraList) {
                BigDecimal baseImponible = BigDecimalUtils.safeValue(modelo349OperadorIntra.getBaseImponible());
                if (baseImponible.compareTo(BigDecimal.ZERO) <= 0){
                    throw new RuntimeException("申报记录的交易金额不能小于0");
                }
                sumBaseImponible = sumBaseImponible.add(baseImponible);
            }
            if (sumBaseImponible.compareTo(importeOperaciones) != 0){
                log.info("申报总额：{}", importeOperaciones);
                log.info("明细总金额：{}", sumBaseImponible);
                throw new ServiceException("申报金额与明细总金额不一致");
            }
        }

        // 6 状态
        if (modelo349Declarante.getEstado() == null){
            // 默认初始状态
            modelo349Declarante.setEstado(Model349Constants.DECLARACION_STATUS_INIT);
        }
    }

    /**
     * 修改349模型申报人主
     * 
     * @param modelo349Declarante 349模型申报人主
     * @return 结果
     */
    @Transactional
    @Override
    public Modelo349Declarante updateModelo349Declarante(Modelo349Declarante modelo349Declarante)
    {
        // 处理基础数据的方法
        handleBaseData(modelo349Declarante);

        modelo349Declarante.setUpdateTime(DateUtils.getNowDate());
        modelo349Declarante.setUpdateBy(SecurityUtils.getUsername());

        modelo349DeclaranteMapper.deleteModelo349OperadorIntraByDeclaranteId(modelo349Declarante.getId());
        insertModelo349OperadorIntra(modelo349Declarante);
        int res = modelo349DeclaranteMapper.updateModelo349Declarante(modelo349Declarante);
        if (res > 0){
            if (Model349Constants.DECLARACION_TIPO_COMPLEMENTARIO.equals(modelo349Declarante.getDeclaracionTipo())){
                // 获取迁移申请识别号
                String numeroDeclaracionAnterior = modelo349Declarante.getNumeroDeclaracionAnterior();
                // 修改迁移识别号的状态为已更正
                modelo349DeclaranteMapper.updateStatusByNumeroDeclaracion(numeroDeclaracionAnterior, Model349Constants.DECLARACION_STATUS_CORRECT);
            }
            if (Model349Constants.DECLARACION_TIPO_SUSTITUTIVO.equals(modelo349Declarante.getDeclaracionTipo())){
                // 获取迁移申请识别号
                String numeroDeclaracionAnterior = modelo349Declarante.getNumeroDeclaracionAnterior();
                // 修改迁移识别号的状态为已替换
                modelo349DeclaranteMapper.updateStatusByNumeroDeclaracion(numeroDeclaracionAnterior, Model349Constants.DECLARACION_STATUS_REPLACE);
            }
            return modelo349DeclaranteMapper.selectModelo349DeclaranteById(modelo349Declarante.getId());
        } else {
            throw new ServiceException("修改349模型申报人主失败");
        }
    }

    /**
     * 批量删除349模型申报人主
     * 
     * @param ids 需要删除的349模型申报人主主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteModelo349DeclaranteByIds(Long[] ids)
    {
        modelo349DeclaranteMapper.deleteModelo349OperadorIntraByDeclaranteIds(ids);
        return modelo349DeclaranteMapper.deleteModelo349DeclaranteByIds(ids);
    }

    /**
     * 删除349模型申报人主信息
     * 
     * @param id 349模型申报人主主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteModelo349DeclaranteById(Long id)
    {
        modelo349DeclaranteMapper.deleteModelo349OperadorIntraByDeclaranteId(id);
        return modelo349DeclaranteMapper.deleteModelo349DeclaranteById(id);
    }


    /**
     * 新增349模型欧盟内经营者记录信息
     *
     * @param modelo349Declarante 349模型申报人主对象
     */
    public void insertModelo349OperadorIntra(Modelo349Declarante modelo349Declarante) {
        List<Modelo349OperadorIntra> modelo349OperadorIntraList = modelo349Declarante.getModelo349OperadorIntraList();
        Long id = modelo349Declarante.getId();

        if (CollectionUtils.isEmpty(modelo349OperadorIntraList)) {
            throw new ServiceException("请添加至少一条申请记录明细");
        }

        // 是否更正
        boolean isRectification = Model349Constants.isCorrector(modelo349Declarante.getDeclaracionTipo());

        // 处理并验证每个经营者记录
        List<Modelo349OperadorIntra> processedList = modelo349OperadorIntraList.stream()
                .peek(operadorIntra -> processOperadorIntra(operadorIntra, modelo349Declarante, id, isRectification))
                .toList();

        // 合并重复记录
        Map<OperadorIntraKey, Modelo349OperadorIntra> mergedMap = mergeDuplicateOperadores(processedList);

        if (!mergedMap.isEmpty()) {
            modelo349DeclaranteMapper.batchModelo349OperadorIntra(new ArrayList<>(mergedMap.values()));
        }
    }

    /**
     * 处理单个经营者记录
     */
    private void processOperadorIntra(Modelo349OperadorIntra operadorIntra, Modelo349Declarante declarante, Long declaranteId, boolean isRectification) {
        // 设置基本属性
        operadorIntra.setDeclaranteId(declaranteId);
        operadorIntra.setTipoRegistro(Model349Constants.OPERADOR_INTRA_DEFAULT_TIPO_REGISTRO);
        operadorIntra.setModeloDeclaracion(Model349Constants.DEFAULT_MODELO_349);
        operadorIntra.setEjercicio(declarante.getEjercicio());
        operadorIntra.setNifDeclarante(declarante.getNifDeclarante());

        // 清理字符串字段
        operadorIntra.setNombreOperador(StringUtils.trimToEmpty(operadorIntra.getNombreOperador()));
        operadorIntra.setNombreDestinatarioSustituto(StringUtils.trimToEmpty(operadorIntra.getNombreDestinatarioSustituto()));

        // 金额保证两位小数（是否更正修改）
        if (!isRectification){
            operadorIntra.setBaseImponible(BigDecimalUtils.safeValue(operadorIntra.getBaseImponible()));
            operadorIntra.setBaseImponibleRectificada(BigDecimal.ZERO);
            operadorIntra.setBaseImponibleAnterior(BigDecimal.ZERO);
        } else {
            operadorIntra.setBaseImponible(BigDecimal.ZERO);
            operadorIntra.setBaseImponibleRectificada(BigDecimalUtils.safeValue(operadorIntra.getBaseImponibleRectificada()));
            operadorIntra.setBaseImponibleAnterior(BigDecimalUtils.safeValue(operadorIntra.getBaseImponibleAnterior()));
        }

        // 验证税号
        validateVatNumber(operadorIntra);

        // 验证替代最终接收者税号(操作为C)
        if (Model349Constants.OPERACION_TYPE_C.equals(operadorIntra.getClaveOperacion())){
            validateNifDestinatarioSustituto(operadorIntra);
        } else {
            operadorIntra.setNifDestinatarioSustituto(null);
        }
    }

    /**
     * 验证经营者税号
     */
    private void validateVatNumber(Modelo349OperadorIntra operadorIntra) {
        // 验证国家编码
        String codigoPais = StringUtils.trimToNull(operadorIntra.getCodigoPais());
        if (codigoPais == null) {
            throw new ServiceException("国家编码不能为空");
        }

        codigoPais = codigoPais.toUpperCase();
        operadorIntra.setCodigoPais(codigoPais);

        // 获取国家配置
        ConfigPaisesUe configPaisesUe = configPaisesUeService.selectConfigPaisesUeByCodigoPais(codigoPais);

        if (configPaisesUe == null) {
            throw new ServiceException("国家编码: [" + codigoPais + "] 不存在");
        }

        // 验证税号
        String nifOperador = StringUtils.trimToNull(operadorIntra.getNifOperador());
        if (nifOperador == null) {
            throw new ServiceException("税号编码不能为空");
        }

        nifOperador = normalizeVatNumber(nifOperador, codigoPais);
        operadorIntra.setNifOperador(nifOperador);

        // 使用正则验证
        validateWithRegex(nifOperador, codigoPais, configPaisesUe.getRegexValidacion(), "税号编码");
    }

    /**
     * 验证替代最终接收者税号
     */
    private void validateNifDestinatarioSustituto(Modelo349OperadorIntra operadorIntra) {
        String nifDestinatario = operadorIntra.getNifDestinatarioSustituto();

        if (!StringUtils.hasText(nifDestinatario) || nifDestinatario.length() < 2) {
            throw new ServiceException("最终接收者的欧盟税号格式不正确: " + nifDestinatario);
        }

        String codigoPais = nifDestinatario.substring(0, 2).toUpperCase();

        // 获取国家配置
        ConfigPaisesUe configPaisesUe = configPaisesUeService
                .selectConfigPaisesUeByCodigoPais(codigoPais);

        if (configPaisesUe == null) {
            throw new ServiceException("最终接收者的欧盟税号国家编码: [" + codigoPais + "] 不存在");
        }

        // 提取税号部分
        String nifPart = nifDestinatario.substring(2).trim();
        if (nifPart.isEmpty()) {
            throw new ServiceException("税号编码不能为空");
        }

        // 使用正则验证
        validateWithRegex(nifPart, codigoPais, configPaisesUe.getRegexValidacion(), "最终接收者的欧盟税号");
    }

    /**
     * 使用正则表达式验证
     */
    private void validateWithRegex(String value, String countryCode, String regex, String fieldName) {
        if (!StringUtils.hasText(regex)) {
            throw new ServiceException("国家编码: [" + countryCode + "] 的正则验证规则未配置");
        }

        try {
            Pattern pattern = Pattern.compile(regex);
            if (!pattern.matcher(value).matches()) {
                throw new ServiceException(String.format(
                        "%s: [%s] 不符合国家 [%s] 的验证规则",
                        fieldName, value, countryCode
                ));
            }
        } catch (PatternSyntaxException e) {
            throw new ServiceException("正则表达式语法错误: " + regex);
        }
    }

    /**
     * 标准化税号
     */
    private String normalizeVatNumber(String vatNumber, String countryCode) {
        vatNumber = vatNumber.trim().toUpperCase();

        // 移除国家代码前缀
        if (vatNumber.startsWith(countryCode)) {
            return vatNumber.substring(countryCode.length());
        }

        return vatNumber;
    }

    /**
     * 合并重复的经营者记录
     */
    private Map<OperadorIntraKey, Modelo349OperadorIntra> mergeDuplicateOperadores(List<Modelo349OperadorIntra> operadores) {

        return operadores.stream()
                .collect(Collectors.toMap(
                        OperadorIntraKey::from,
                        Function.identity(),
                        this::mergeOperadorIntraDetails,
                        LinkedHashMap::new
                ));
    }

    /**
     * 合并两个经营者的金额信息
     */
    private Modelo349OperadorIntra mergeOperadorIntraDetails(Modelo349OperadorIntra exist, Modelo349OperadorIntra current) {
        exist.setBaseImponible(exist.getBaseImponible().add(current.getBaseImponible()));
        exist.setBaseImponibleRectificada(exist.getBaseImponibleRectificada().add(current.getBaseImponibleRectificada()));
        exist.setBaseImponibleAnterior(exist.getBaseImponibleAnterior().add(current.getBaseImponibleAnterior()));
        return exist;
    }

    /**
     * 经营者记录的唯一键（使用Record类）
     */
    private record OperadorIntraKey(
            String codigoPais,
            String nifOperador,
            String nombreOperador,
            String claveOperacion
    ) {
        public static OperadorIntraKey from(Modelo349OperadorIntra operadorIntra) {
            return new OperadorIntraKey(
                    StringUtils.defaultString(operadorIntra.getCodigoPais()),
                    StringUtils.defaultString(operadorIntra.getNifOperador()),
                    StringUtils.defaultString(operadorIntra.getNombreOperador()),
                    StringUtils.defaultString(operadorIntra.getClaveOperacion())
            );
        }
    }

}
