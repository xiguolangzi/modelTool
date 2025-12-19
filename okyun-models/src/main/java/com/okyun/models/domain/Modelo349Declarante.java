package com.okyun.models.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.okyun.common.annotation.Excel;
import com.okyun.common.core.domain.BaseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 349模型申报人主对象 modelo_349_declarante
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Modelo349Declarante extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 位置 1: 记录类型，固定为1 */
    @Excel(name = "记录类型")
    @Size(max = 1, message = "位置 1: 记录类型，固定为1不能为空")
    private String tipoRegistro;

    /** 位置 2-4: 申报模型，固定为349 */
    @Excel(name = "申报模型")
    @Size(max = 3, message = "位置 2-4: 申报模型，固定为349不能为空")
    private String modeloDeclaracion;

    /** 位置 5-8: 财政年度，YYYY格式 */
    @Excel(name = "财政年度")
    @NotNull(message = "财政年度，YYYY格式不能为空")
    @Size(max = 4, message = "位置 5-8: 财政年度，YYYY格式不能为空")
    private String ejercicio;

    /** 位置 9-17: 申报人NIF税号 此字段应右对齐 左侧补零*/
    @Excel(name = "申报人NIF")
    @NotNull(message = "申报人NIF税号不能为空")
    @Size(min = 9,max = 9, message = "位置 9-17: 申报人NIF税号9位")
    private String nifDeclarante;

    /** 位置 18-57: 申报人姓名或公司名称 */
    @Excel(name = "申报人姓名")
    @NotNull(message = "申报人姓名或公司名称不能为空")
    @Size(max = 40, message = "位置 18-57: 申报人姓名或公司名称不能为空")
    private String nombreDeclarante;

    // 位置 58: 空白

    /**  位置 59-67: 联系电话（9位数字） */
    @Excel(name = "联系电话", readConverterExp = "9=位数字")
    @Size( max = 9, message = "  位置 59-67: 联系电话（9位数字）不能为空")
    private String telefonoContacto;

    /** 位置 68-107 : 联系人姓名 */
    // 则填写第一姓氏、一个空格、第二姓氏、一个空格和全名，必须按此顺序。对于法人实体和收入归属制度的实体，填写完整的公司名称，无缩写
    @Excel(name = "联系人姓名")
    @Size( max = 40, message = " 68-107 : 联系人姓名不能为空")
    private String personaContacto;

    /** 位置 108-120: 申报识别号，13位数字 */
    @Excel(name = "申报识别号")
    @Size(max = 13, message = " 108-120: 申报识别号，13位数字不能为空")
    private String numeroIdentificativo;

    /** 位置 121-122: 补充或替代申报 C-补充申报，S-替代申报*/
    // 121 补充申报：如果本次申报的目的是包含本应出现在同一财年先前提交的另一份申报中但被完全遗漏的记录，则填写 "C"。 旨在修改同一财年先前提交的另一份申报中已申报数据内容的补充申报
    // 122 替代申报：如果本次提交的目的是完全作废并替代同一财年的另一份先前申报，则填写 "S"。一份替代申报只能作废一份先前的申报
    @Excel(name = "申报类型")
    @Size( max = 2, message = " 121-122: 补充或替代申报 C-补充申报，S-替代申报")
    private String declaracionTipo;

    /** 位置 123-135: 前一申报的识别号 13位数字内容字段。应出现的识别号将是一个序列号，其前三位数字对应于代码 349*/
    @Excel(name = "前一申报识别号")
    @Size( max = 13, message = " 123-135: 前一申报的识别号不能为空")
    private String numeroDeclaracionAnterior;

    /** 位置 136-137: 期间 (如 "01", "1T") */
    @Excel(name = "申报期间")
    @NotNull(message = "申报期间不能为空")
    @Size(max = 2, message = " 123-135: 申报期间2位")
    private String periodo;

    /** 位置 138-146: 欧盟运营商总数 */
    @Excel(name = "欧盟内经营者总数")
    private Integer totalOperadoresIntracomunitarios;

    /** 位置 147-161: 欧盟内部交易总额 (细分整数和小数部分) */
    @Excel(name = "欧盟内交易总额", readConverterExp = "欧=元")
    private BigDecimal importeOperaciones;

    /** 位置 162-170: 需要修正的经营者总数 */
    @Excel(name = "需要修正的经营者总数")
    private Integer totalOperadoresRectificaciones;

    /** 位置 171-185: 修正金额总额（欧元） */
    @Excel(name = "修正金额总额", readConverterExp = "欧=元")
    private BigDecimal importeRectificaciones;

    /** 位置 186: 申报频率变更指示器：X-月度申报 */
    @Excel(name = "申报频率变更指示器")
    @Size(max = 1, message = "位置 186: 需要修正的经营者总数不能为空")
    private String indicadorCambioPeriodicidad;

    // 位置 187-390: 空白

    /** 位置 391-399： 法定代表人的NIF税号 如果申报人未满14岁，则在此字段填写其法定代表人（父亲、母亲或监护人）的税号*/
    @Excel(name = "法定代表人的NIF")
    @Size(max = 15, message = "位置 391-399： 法定代表人的NIF税号不能为空")
    private String nifRepresentanteLegal;

    // 位置 400-500 空白
    // 所有金额均为正数
    // 无内容的数字字段将填充为零
    // 无内容的字母数字/字母字段将填充为空白
    // 所有数字字段右对齐，左侧用零填充
    // 所有字母数字/字母字段左对齐，右侧用空白填充，大写，无特殊字符且无重音元音，除非字段描述中另有说明

    /** 状态：P-待处理，E-已提交，A-已接受，R-已拒绝 */
    @Excel(name = "状态")
    private String estado;


    /** 349模型欧盟内经营者记录信息 */
    @Valid
    private List<Modelo349OperadorIntra> modelo349OperadorIntraList;


}
