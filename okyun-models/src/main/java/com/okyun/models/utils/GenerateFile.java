package com.okyun.models.utils;

import com.okyun.models.constants.Model349Constants;
import com.okyun.models.domain.Modelo349Declarante;
import com.okyun.models.domain.Modelo349OperadorIntra;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenerateFile {

    private static final String BLANK = " ";
    private static final String ZERO = "0";

    /**
     * 生成349模型文件内容
     * @param declarante
     * @return
     */
    public byte[] generateModelo349File(Modelo349Declarante declarante, List<Modelo349OperadorIntra> operadores) {

        // 1. 获取申报人信息
        StringBuilder content = new StringBuilder();

        // 1. 生成申报人记录 (Type 1)
        content.append(generateRegistroDeclarante(declarante))
                .append(System.lineSeparator());

        // 2 是否是更正状态
        boolean isRectificacion = Model349Constants.isCorrector(declarante.getDeclaracionTipo());

        // 2. 生成欧盟经营者记录 (Type 2)
        for (Modelo349OperadorIntra operador : operadores) {
            if (operador.getClaveOperacion() != null && !operador.getClaveOperacion().isEmpty()) {
                content.append(generateRegistroOperador(operador, isRectificacion))
                        .append(System.lineSeparator());
            }
        }

        return content.toString().getBytes(StandardCharsets.ISO_8859_1);
    }


    /**
     * 生成申报人记录
     * @param declarante
     * @return
     */
    private String generateRegistroDeclarante(Modelo349Declarante declarante) {

        StringBuilder record = new StringBuilder(500);

        // 1. 位置 1: Tipo de Registro (1位)
        record.append(formatNumeric(declarante.getTipoRegistro(), 1));

        // 2. 位置 2-4: Modelo Declaración (3位)
        record.append(formatNumeric(declarante.getModeloDeclaracion(), 3));

        // 3. 位置 5-8: Ejercicio (4位)
        record.append(formatNumeric(declarante.getEjercicio(), 4));

        // 4. 位置 9-17: NIF Declarante (9位) - 右对齐，左侧补零
        record.append(formatNif(declarante.getNifDeclarante(), 9));

        // 5. 位置 18-57: Nombre Declarante (40位) - 左对齐，右侧补空格，大写
        record.append(formatAlphanumeric(declarante.getNombreDeclarante(), 40));

        // 6. 位置 58: Posición 58: Blanco (1位) 空白
        record.append(BLANK);

        // 7. 位置 59-67: Teléfono Contacto (9位) - 右对齐，左侧补零
        record.append(formatNumeric(declarante.getTelefonoContacto(), 9));

        // 8. 位置 68-107 : Persona Contacto (40位) - 左对齐，右侧补空格，大写
        record.append(formatAlphanumeric(declarante.getPersonaContacto(), 40));

        // 9. 位置 108-120: Número Identificativo (13位) - 右对齐，左侧补零
        record.append(formatNumeric(declarante.getNumeroIdentificativo(), 13));

        // 10. 位置 121-122: Declaración Tipo (2位)
        // 位置 121: 补充申报 (C 或 空格)
        if (Model349Constants.DECLARACION_TIPO_COMPLEMENTARIO.equals(declarante.getDeclaracionTipo())) {
            record.append("C");  // 第121位
        } else {
            record.append(BLANK);  // 第121位
        }

        // 位置 122: 替代申报 (S 或 空格)
        if (Model349Constants.DECLARACION_TIPO_SUSTITUTIVO.equals(declarante.getDeclaracionTipo())) {
            record.append("S");  // 第122位
        } else {
            record.append(BLANK);  // 第122位
        }

        // 11. 位置 123-135: 先前申报识别号
        if (Model349Constants.isCorrector(declarante.getDeclaracionTipo()) ) {
            // 如果是补充或替代申报，必须填写先前申报号
            record.append(formatNumeric(declarante.getNumeroDeclaracionAnterior(), 13));
        } else {
            // 普通申报，填写13个0
            record.append(repeatString("0", 13));
        }


        // 12. 位置 136-137: Periodo (2位)
        record.append(formatAlphanumeric(declarante.getPeriodo(), 2));

        // 13. 位置 138-146: Total Operadores Intracomunitarios (9位数字)
        record.append(formatNumeric(String.valueOf(declarante.getTotalOperadoresIntracomunitarios()), 9));

        // 14. 位置 147-161: Importe Operaciones (15位: 13整数 + 2小数)
        record.append(formatAmount(declarante.getImporteOperaciones(), 13, 2));

        // 15. 位置 162-170:  Total Operadores Rectificaciones (9位)
        record.append(formatNumeric(String.valueOf(declarante.getTotalOperadoresRectificaciones()), 9));

        // 16. 位置 171-185: Importe Rectificaciones (15位: 13整数 + 2小数)
        record.append(formatAmount(declarante.getImporteRectificaciones(), 13, 2));

        // 17. 位置 186: Indicador Cambio Periodicidad (1位)
        record.append(formatAlphabetic(declarante.getIndicadorCambioPeriodicidad(), 1));

        // 18. 位置 187-390 : Posiciones 187-390: Blancos (204位)
        record.append(repeatString(BLANK, 204));

        // 19. 位置 391-399： NIF Representante Legal (9位) - 右对齐，左侧补零
        record.append(formatNif(declarante.getNifRepresentanteLegal(), 9));

        // 20. 位置 400-500 : Posiciones 400-500: Blancos (101位)
        record.append(repeatString(BLANK, 101));

        return record.toString();
    }

    /**
     * 生成欧盟经营者记录
     * @param operador
     * @return
     */
    private String generateRegistroOperador(Modelo349OperadorIntra operador, boolean isRectificacion) {

        StringBuilder record = new StringBuilder(500);

        // 1. 位置 1 ： Tipo de Registro (1位)
        record.append(formatNumeric("2", 1));

        // 2. 位置 2-4 ： Modelo Declaración (3位)
        record.append(formatNumeric(operador.getModeloDeclaracion(), 3));

        // 3. 位置 5-8 ： Ejercicio (4位)
        record.append(formatNumeric(operador.getEjercicio(), 4));

        // 4. 位置 9-17 ： NIF Declarante (9位)
        record.append(formatNif(operador.getNifDeclarante(), 9));

        // 5. 位置 18-75 ：Posiciones 18-75: Blancos (58位)
        record.append(repeatString(BLANK, 58));

        // 6. NIF Operador Comunitario (17位)
        // 6.1 位置 76-77 ：欧盟税号 国家代码 部分
        record.append(formatAlphabetic(operador.getCodigoPais(), 2));
        // 6.2 位置 78-92 ：欧盟税号 编码 部分
        record.append(formatAlphanumeric(operador.getNifOperador(), 15));

        // 7. 位置 93-132 ： Nombre Operador (40位)
        record.append(formatAlphanumeric(operador.getNombreOperador(), 40));

        // 8. 位置 133 ： Clave Operación (1位)
        record.append(formatAlphabetic(operador.getClaveOperacion(), 1));

        if (isRectificacion) {
            // 修正记录格式
            return generateRectificacionRecord(record, operador);
        } else {
            // 经营者记录格式
            return generateOperadorRecord(record, operador);
        }
    }

    /**
     * 生成经营者记录
     */
    private String generateOperadorRecord(StringBuilder record, Modelo349OperadorIntra operador) {

        // 9. 位置 134-146 ： Base Imponible (13位: 11整数 + 2小数)
        record.append(formatAmount(operador.getBaseImponible(), 11, 2));

        // 10. 位置 147-178 ： Posiciones 147-178: Blancos (32位)
        record.append(repeatString(BLANK, 32));

        // 11. 位置 179-195 ：NIF Destinatario Sustituto (17位) - 仅当操作代码为C时
        if (Model349Constants.OPERACION_TYPE_C.equals(operador.getClaveOperacion())) {
            record.append(formatAlphanumeric(operador.getNifDestinatarioSustituto(), 17));
        } else {
            record.append(repeatString(BLANK, 17));
        }

        // 12. 位置 196-235 ： Nombre Destinatario Sustituto (40位) - 仅当操作代码为C时
        if (Model349Constants.OPERACION_TYPE_C.equals(operador.getClaveOperacion())) {
            record.append(formatAlphanumeric(operador.getNombreDestinatarioSustituto(), 40));
        } else {
            record.append(repeatString(BLANK, 40));
        }

        // 13. 位置 236-500 ： Posiciones 236-500: Blancos (265位)
        record.append(repeatString(BLANK, 265));

        return record.toString();
    }

    /**
     * 生成修正记录
     */
    private String generateRectificacionRecord(StringBuilder record, Modelo349OperadorIntra rectificacion) {


        // 9. 更正位置  134-146 ：Posiciones 134-146: Blancos (13位)
        record.append(repeatString(BLANK, 13));

        // 10. 位置 147-178 ：Rectificaciones (32位)
        // 10.1 更正位置 147-150 ：Ejercicio Rectificación (4位)
        record.append(formatNumeric(rectificacion.getEjercicioRectificacion(), 4));

        // 10.2 更正位置 153-165 ：Periodo Rectificación (2位)
        record.append(formatAlphanumeric(rectificacion.getPeriodoRectificacion(), 2));

        // 10.3 更正位置 153-165 ：Base Imponible Rectificada (13位: 11整数 + 2小数)
        record.append(formatAmount(rectificacion.getBaseImponibleRectificada(), 11, 2));

        // 10.4 更正位置 166-178 ：Base Imponible Anterior (13位: 11整数 + 2小数)
        record.append(formatAmount(rectificacion.getBaseImponibleAnterior(), 11, 2));

        // 11. 位置 179-195 ：NIF Destinatario Sustituto (17位) - 仅当操作代码为C时
        if (Model349Constants.OPERACION_TYPE_C.equals(rectificacion.getClaveOperacion())) {
            record.append(formatAlphanumeric(rectificacion.getNifDestinatarioSustituto(), 17));
        } else {
            record.append(repeatString(BLANK, 17));
        }

        // 12. 位置 196-235 ： Nombre Destinatario Sustituto (40位) - 仅当操作代码为C时
        if (Model349Constants.OPERACION_TYPE_C.equals(rectificacion.getClaveOperacion())) {
            record.append(formatAlphanumeric(rectificacion.getNombreDestinatarioSustituto(), 40));
        } else {
            record.append(repeatString(BLANK, 40));
        }

        // 13. 位置 236-500 ： Posiciones 236-500: Blancos (265位)
        record.append(repeatString(BLANK, 265));

        return record.toString();
    }

    /**
     * 格式化 数字 字段
     */
    private String formatNumeric(String value, int length) {
        if (value == null) {
            value = "";
        }
        // 移除所有非数字字符
        value = value.replaceAll("[^0-9]", "");

        if (value.length() > length) {
            value = value.substring(0, length);
        }

        // 右对齐，左侧补零
        return String.format("%" + length + "s", value).replace(' ', '0');
    }

    /**
     * 格式化 NIF税号
     */
    private String formatNif(String value, int length) {
        if (value == null) {
            // 空补充空格
            return repeatString(BLANK, length);
        }
        // 移除空格
        value = value.replaceAll("\\s", "");

        if (value.length() > length) {
            value = value.substring(0, length);
        }

        // 右对齐，左侧补零
        return String.format("%" + length + "s", value).replace(' ', '0');
    }

    /**
     * 格式化 字母数字 字段
     */
    private String formatAlphanumeric(String value, int length) {
        if (value == null) {
            value = "";
        }
        // 转换为大写，移除重音和特殊字符
        value = normalizeText(value);

        if (value.length() > length) {
            value = value.substring(0, length);
        }

        // 左对齐，右侧补空格
        return String.format("%-" + length + "s", value);
    }

    /**
     * 格式化字母字段
     */
    private String formatAlphabetic(String value, int length) {
        if (value == null) {
            value = "";
        }
        // 只保留字母，转换为大写
        value = value.replaceAll("[^A-Za-z]", "").toUpperCase();

        if (value.length() > length) {
            value = value.substring(0, length);
        }

        // 左对齐，右侧补空格
        return String.format("%-" + length + "s", value);
    }

    /**
     * 格式化金额字段
     */
    private String formatAmount(BigDecimal amount, int integerLength, int decimalLength) {
        if (amount == null) {
            amount = BigDecimal.ZERO;
        }

        // 确保金额为正数
        amount = amount.abs();

        // 四舍五入到指定小数位
        amount = amount.setScale(decimalLength, RoundingMode.HALF_UP);

        String[] parts = amount.toString().split("\\.");
        String integerPart = parts[0];
        String decimalPart = parts.length > 1 ? parts[1] : "0".repeat(decimalLength);

        // 格式化整数部分
        if (integerPart.length() > integerLength) {
            integerPart = integerPart.substring(0, integerLength);
        }
        integerPart = String.format("%" + integerLength + "s", integerPart).replace(' ', '0');

        // 格式化小数部分
        if (decimalPart.length() > decimalLength) {
            decimalPart = decimalPart.substring(0, decimalLength);
        } else if (decimalPart.length() < decimalLength) {
            decimalPart = String.format("%-" + decimalLength + "s", decimalPart).replace(' ', '0');
        }

        return integerPart + decimalPart;
    }

    /**
     * 标准化文本（大写、移除重音、特殊字符）
     */
    private String normalizeText(String text) {
        if (text == null) {
            return "";
        }

        text = text.toUpperCase();

        // 移除重音
        text = text.replaceAll("[ÁÀÄÂ]", "A")
                .replaceAll("[ÉÈËÊ]", "E")
                .replaceAll("[ÍÌÏÎ]", "I")
                .replaceAll("[ÓÒÖÔ]", "O")
                .replaceAll("[ÚÙÜÛ]", "U")
                .replaceAll("Ñ", "N")
                .replaceAll("Ç", "C");

        // 移除特殊字符，只保留字母、数字和空格
        text = text.replaceAll("[^A-Z0-9 ]", "");

        return text;
    }

    /**
     * 重复字符串
     */
    private String repeatString(String str, int times) {
        return new String(new char[times]).replace("\0", str);
    }
}
