package com.okyun.models.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.okyun.common.annotation.Excel;
import com.okyun.common.core.domain.BaseEntity;

import javax.validation.constraints.Size;

/**
 * 欧盟国家代码配置对象 config_paises_ue
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigPaisesUe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 国家代码 */
    @Excel(name = "国家代码")
    @Size(max = 2, message = "国家代码2位")
    private String codigoPais;

    /** 国家名称 */
    @Excel(name = "国家名称")
    @Size(max = 50, message = "国家名称50位")
    private String nombrePais;

    /** 最小长度 */
    @Excel(name = "最小长度")
    private Long lengthMin;

    /** 最大长度 */
    @Excel(name = "最大长度")
    private Long lengthMax;

    /** NIF格式描述 */
    @Excel(name = "NIF格式描述")
    @Size(max = 100, message = "NIF格式描述100位")
    private String formatoNif;

    /** 验证正则表达式 */
    @Excel(name = "验证正则表达式")
    private String regexValidacion;

    /** 是否激活 */
    @Excel(name = "是否激活")
    private Integer activo;

    /** 更新时间 */
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fechaActualizacion;


}
