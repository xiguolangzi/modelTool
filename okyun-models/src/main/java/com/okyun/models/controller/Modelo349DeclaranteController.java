package com.okyun.models.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.okyun.common.exception.ServiceException;
import com.okyun.models.domain.Modelo349OperadorIntra;
import com.okyun.models.utils.GenerateFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.okyun.common.annotation.Log;
import com.okyun.common.core.controller.BaseController;
import com.okyun.common.core.domain.AjaxResult;
import com.okyun.common.enums.BusinessType;
import com.okyun.models.domain.Modelo349Declarante;
import com.okyun.models.service.IModelo349DeclaranteService;
import com.okyun.common.utils.poi.ExcelUtil;
import com.okyun.common.core.page.TableDataInfo;

/**
 * 349模型申报人主Controller
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@RestController
@RequestMapping("/models/modelo349Declarante")
public class Modelo349DeclaranteController extends BaseController
{
    @Autowired
    private IModelo349DeclaranteService modelo349DeclaranteService;

    @Autowired
    private GenerateFile generateFile;

    /**
     * 查询349模型申报人主列表
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349Declarante:list')")
    @GetMapping("/list")
    public TableDataInfo list(Modelo349Declarante modelo349Declarante)
    {
        startPage();
        List<Modelo349Declarante> list = modelo349DeclaranteService.selectModelo349DeclaranteList(modelo349Declarante);
        return getDataTable(list);
    }

    /**
     * 导出349模型申报人主列表
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349Declarante:export')")
    @Log(title = "349模型申报人主", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Modelo349Declarante modelo349Declarante)
    {
        List<Modelo349Declarante> list = modelo349DeclaranteService.selectModelo349DeclaranteList(modelo349Declarante);
        ExcelUtil<Modelo349Declarante> util = new ExcelUtil<Modelo349Declarante>(Modelo349Declarante.class);
        util.exportExcel(response, list, "349模型申报人主数据");
    }

    /**
     * 根据ID导出349模型文件
     */
    @GetMapping("/export/{id}")
    public ResponseEntity<byte[]> exportModelo349(@PathVariable Long id) {
        // 1. 获取申报人信息
        Modelo349Declarante declarante = modelo349DeclaranteService.selectModelo349DeclaranteById(id);
        if (declarante == null){
            throw new ServiceException("申报人信息不存在");
        }

        // 2. 获取欧盟经营者记录
        List<Modelo349OperadorIntra> operadores = declarante.getModelo349OperadorIntraList();
        if (CollectionUtils.isEmpty(operadores)){
            throw new ServiceException("申请记录明细不存在！");
        }

        // 4. 生成文件内容
        byte[] fileContent = generateFile.generateModelo349File(declarante, operadores);

        // 5. 设置响应头
        String fileName = String.format("349_%s_%s_%s.txt",
                declarante.getNifDeclarante(),
                declarante.getEjercicio(),
                declarante.getPeriodo());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(fileContent.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);
    }

    /**
     * 获取349模型申报人主详细信息
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349Declarante:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(modelo349DeclaranteService.selectModelo349DeclaranteById(id));
    }

    /**
     * 新增349模型申报人主
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349Declarante:add')")
    @Log(title = "349模型申报人主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody Modelo349Declarante modelo349Declarante)
    {
        return success(modelo349DeclaranteService.insertModelo349Declarante(modelo349Declarante));
    }

    /**
     * 修改349模型申报人主
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349Declarante:edit')")
    @Log(title = "349模型申报人主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody Modelo349Declarante modelo349Declarante)
    {
        return success(modelo349DeclaranteService.updateModelo349Declarante(modelo349Declarante));
    }

    /**
     * 删除349模型申报人主
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349Declarante:remove')")
    @Log(title = "349模型申报人主", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(modelo349DeclaranteService.deleteModelo349DeclaranteByIds(ids));
    }
}
