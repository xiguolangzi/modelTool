package com.okyun.models.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.okyun.common.annotation.Excel;
import com.okyun.common.core.domain.BaseEntity;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.validation.constraints.Size;

/**
 * 349模型欧盟内经营者记录对象 modelo_349_operador_intra
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Modelo349OperadorIntra extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 申报人ID */
    @Excel(name = "申报记录ID", type = Excel.Type.EXPORT, sort = 1)
    private Long declaranteId;

    /** 位置 1 ： 记录类型，固定为2 */
    @Excel(name = "记录类型", type = Excel.Type.EXPORT, sort = 2)
    @Size(max = 1, message = "长度不能超过1个字符")
    private String tipoRegistro;

    /** 位置 2-4 ：申报模型，固定为349 */
    @Excel(name = "申报模型", type = Excel.Type.EXPORT, sort = 3)
    @Size(max = 3, message = "长度不能超过3个字符")
    private String modeloDeclaracion;

    /** 位置 5-8 ：财政年度 */
    @Excel(name = "财政年度", type = Excel.Type.EXPORT, sort = 4)
    @Size(max = 4, message = "长度不能超过4个字符")
    private String ejercicio;

    /** 位置 9-17 ：申报人NIF税号 */
    @Excel(name = "申报人NIF", type = Excel.Type.EXPORT, sort = 5)
    @Size(max = 9, message = "长度不能超过15个字符")
    private String nifDeclarante;

    // 更正/非更正 位置 18-75 ： 都是空白

    /** 位置 76-77 ：经营者 国家代码：DE, FR, IT等 */
    @Excel(name = "欧盟税号-国家代码", sort = 6, headerBackgroundColor = IndexedColors.DARK_BLUE)
    @Size(max = 2, message = "长度不能超过2个字符")
    private String codigoPais;

    /** 位置 78-92 ：经营者 欧盟税号-编号 */
    @Excel(name = "欧盟税号-编号", sort = 7, headerBackgroundColor = IndexedColors.DARK_BLUE)
    @Size(max = 15, message = "长度不能超过15个字符")
    private String nifOperador;

    /** 位置 93-132 ：经营者姓名或公司名称 */
    @Excel(name = "经营者姓名", sort = 8, headerBackgroundColor = IndexedColors.DARK_BLUE)
    @Size(max = 40, message = "长度不能超过40个字符")
    private String nombreOperador;

    /** 位置 133 ： 操作代码 */
    @Excel(name = "操作代码", sort = 9, headerBackgroundColor = IndexedColors.DARK_BLUE)
    private String claveOperacion;

    // 更正位置  134-146 ： 空白
    /** 位置 134-146 ：应税基础或金额（欧元） */
    @Excel(name = "交易金额",  sort = 10, headerBackgroundColor = IndexedColors.DARK_BLUE)
    private BigDecimal baseImponible;

    // 非更正位置 147-178 ： 空白
    /** 更正位置 147-150 ：修正的财政年度 */
    @Excel(name = "修正的财政年度", sort = 11,  width = 20)
    private String ejercicioRectificacion;

    /** 更正位置 151-152 ：修正的期间 */
    @Excel(name = "修正的期间", sort = 12)
    private String periodoRectificacion;

    /** 更正位置 153-165 ： 修正后的应税基础 */
    @Excel(name = "修正后的交易金额", sort = 13,  width = 20)
    private BigDecimal baseImponibleRectificada;

    /** 更正位置 166-178 ： 先前申报的应税基础 */
    @Excel(name = "先前申报的交易金额", sort = 14, width = 20)
    private BigDecimal baseImponibleAnterior;


    /** 位置 179-195 ： 替代最终接收者的欧盟税号（仅当操作代码为C时填写） 179-180 国家编码*/
    @Excel(name = "替代最终接收者税号", readConverterExp = "仅=当操作代码为C时填写", sort = 15, width = 20, headerBackgroundColor = IndexedColors.BLUE_GREY)
    private String nifDestinatarioSustituto;

    /** 位置 196-235 ：替代最终接收者的姓名（仅当操作代码为C时填写） */
    @Excel(name = "替代最终接收者姓名", readConverterExp = "仅=当操作代码为C时填写", sort = 16, width = 20, headerBackgroundColor = IndexedColors.BLUE_GREY)
    private String nombreDestinatarioSustituto;

    // 位置 236-500 ：空白


}
