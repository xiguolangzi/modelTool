package com.okyun.models.constants;

public class Model349Constants {

    /** 主体记录类型 */
    public static final String DECLARANTE_DEFAULT_TIPO_REGISTRO = "1";

    /** 明细记录类型  */
    public static final String OPERADOR_INTRA_DEFAULT_TIPO_REGISTRO = "2";

    /**  349申报模型 */
    public static final String DEFAULT_MODELO_349 = "349";



    /** 申报类型 - 补充 C */
    public static final String DECLARACION_TIPO_COMPLEMENTARIO = "C";

    /** 申报类型 - 替代 S */
    public static final String DECLARACION_TIPO_SUSTITUTIVO = "S";


    /** 判断是否是更正类型 */
    public static boolean isCorrector(String declaracionTipo) {
        return DECLARACION_TIPO_COMPLEMENTARIO.equals(declaracionTipo) || DECLARACION_TIPO_SUSTITUTIVO.equals(declaracionTipo);
    }




    /** 申报状态 - 初始状态 P */
    public static final String DECLARACION_STATUS_INIT = "P";

    /** 申报状态 - 已提交状态 E */
    public static final String DECLARACION_STATUS_COMMIT = "E";

    /** 申报状态 - 已接收状态 A */
    public static final String DECLARACION_STATUS_RECEIVE = "A";

    /** 申报状态 - 已替换状态 S */
    public static final String DECLARACION_STATUS_REPLACE = "S";

    /** 申报状态 - 已更正状态该 I */
    public static final String DECLARACION_STATUS_CORRECT = "I";

    /** 操作类型 - 欧盟免税货物交付 E */
    public static final String OPERACION_TYPE_E = "E";

    /** 操作类型 - 免税进口后交付 M */
    public static final String OPERACION_TYPE_M = "M";

    /** 操作类型 - 代表人免税进口后交付 H */
    public static final String OPERACION_TYPE_H = "H";

    /** 操作类型 - 三角贸易交付 T */
    public static final String OPERACION_TYPE_T = "T";

    /** 操作类型 - 欧盟内采购货物 A */
    public static final String OPERACION_TYPE_A = "A";

    /** 操作类型 - 欧盟提供服务 S */
    public static final String OPERACION_TYPE_S = "S";

    /** 操作类型 - 欧盟内采购服务 I */
    public static final String OPERACION_TYPE_I = "I";

    /** 操作类型 - 寄售货物转移 R */
    public static final String OPERACION_TYPE_R = "R";

    /** 操作类型 - 寄售货物退回 D */
    public static final String OPERACION_TYPE_D = "D";

    /** 操作类型 - 寄售最终客户替换 C */
    public static final String OPERACION_TYPE_C = "C";

}
