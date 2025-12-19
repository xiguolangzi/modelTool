package com.okyun.models.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.okyun.common.annotation.Excel;
import com.okyun.common.core.domain.BaseEntity;

import javax.validation.constraints.Size;

/**
 * 申报人档案对象 config_declarante
 * 
 * @author hubiao
 * @date 2025-12-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ConfigDeclarante extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long declaracionId;

    /** 申报人NIF税号 */
    @Excel(name = "申报人NIF税号")
    @Size( min = 9 ,max = 9, message = "申报人NIF税号9位")
    private String nifDeclarante;

    /** 申报人姓名或公司名称 */
    @Excel(name = "申报人姓名或公司名称")
    @Size( min = 1 ,max = 40, message = "申报人姓名或公司名称40位")
    private String nombreDeclarante;

    /** 联系电话（9位数字） */
    @Excel(name = "联系电话", readConverterExp = "9=位数字")
    @Size( max = 9, message = "联系电话最多9位数字")
    private String telefonoContacto;

    /** 联系人姓名 */
    @Excel(name = "联系人姓名")
    @Size( max = 40, message = "联系人姓名40位")
    private String personaContacto;

    /** 法定代表人的NIF税号 */
    @Excel(name = "法定代表人的NIF税号")
    @Size( min = 9 ,max = 9, message = "法定代表人的NIF税号9位")
    private String nifRepresentanteLegal;


}
