package com.okyun.models.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.okyun.common.core.domain.entity.SysUser;
import com.okyun.models.domain.ImportItemsTemplate1;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.okyun.models.domain.Modelo349OperadorIntra;
import com.okyun.models.service.IModelo349OperadorIntraService;
import com.okyun.common.utils.poi.ExcelUtil;
import com.okyun.common.core.page.TableDataInfo;

/**
 * 349经营者记录明细Controller
 * 
 * @author hubiao
 * @date 2025-12-18
 */
@RestController
@RequestMapping("/models/modelo349OperadorIntra")
public class Modelo349OperadorIntraController extends BaseController
{
    @Autowired
    private IModelo349OperadorIntraService modelo349OperadorIntraService;

    /**
     * 查询349经营者记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349OperadorIntra:list')")
    @GetMapping("/list")
    public TableDataInfo list(Modelo349OperadorIntra modelo349OperadorIntra)
    {
        startPage();
        List<Modelo349OperadorIntra> list = modelo349OperadorIntraService.selectModelo349OperadorIntraList(modelo349OperadorIntra);
        return getDataTable(list);
    }

    /**
     * 导出349经营者记录明细列表
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349OperadorIntra:export')")
    @Log(title = "349经营者记录明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Modelo349OperadorIntra modelo349OperadorIntra)
    {
        List<Modelo349OperadorIntra> list = modelo349OperadorIntraService.selectModelo349OperadorIntraList(modelo349OperadorIntra);
        ExcelUtil<Modelo349OperadorIntra> util = new ExcelUtil<Modelo349OperadorIntra>(Modelo349OperadorIntra.class);
        util.exportExcel(response, list, "349经营者记录明细数据");
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Modelo349OperadorIntra> util = new ExcelUtil<Modelo349OperadorIntra>(Modelo349OperadorIntra.class);
        util.importTemplateExcel(response, "申报349记录明细");
    }

    @PostMapping("/importCustomer1Template")
    public void importCustomer1Template(HttpServletResponse response)
    {
        ExcelUtil<ImportItemsTemplate1> util = new ExcelUtil<ImportItemsTemplate1>(ImportItemsTemplate1.class);
        util.importTemplateExcel(response, "申报349记录明细");
    }

    /**
     * 获取349经营者记录明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349OperadorIntra:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(modelo349OperadorIntraService.selectModelo349OperadorIntraById(id));
    }

    /**
     * 新增349经营者记录明细
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349OperadorIntra:add')")
    @Log(title = "349经营者记录明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Modelo349OperadorIntra modelo349OperadorIntra)
    {
        return toAjax(modelo349OperadorIntraService.insertModelo349OperadorIntra(modelo349OperadorIntra));
    }

    /**
     * 修改349经营者记录明细
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349OperadorIntra:edit')")
    @Log(title = "349经营者记录明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Modelo349OperadorIntra modelo349OperadorIntra)
    {
        return toAjax(modelo349OperadorIntraService.updateModelo349OperadorIntra(modelo349OperadorIntra));
    }

    /**
     * 删除349经营者记录明细
     */
    @PreAuthorize("@ss.hasPermi('models:modelo349OperadorIntra:remove')")
    @Log(title = "349经营者记录明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(modelo349OperadorIntraService.deleteModelo349OperadorIntraByIds(ids));
    }
}
