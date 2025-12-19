package com.okyun.models.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.okyun.models.domain.ConfigDeclarante;
import com.okyun.models.service.IConfigDeclaranteService;
import com.okyun.common.utils.poi.ExcelUtil;
import com.okyun.common.core.page.TableDataInfo;

/**
 * 申报人档案Controller
 * 
 * @author hubiao
 * @date 2025-12-15
 */
@RestController
@RequestMapping("/models/configDeclarante")
public class ConfigDeclaranteController extends BaseController
{
    @Autowired
    private IConfigDeclaranteService configDeclaranteService;

    /**
     * 查询申报人档案列表
     */
    @PreAuthorize("@ss.hasPermi('models:configDeclarante:list')")
    @GetMapping("/list")
    public TableDataInfo list(ConfigDeclarante configDeclarante)
    {
        startPage();
        List<ConfigDeclarante> list = configDeclaranteService.selectConfigDeclaranteList(configDeclarante);
        return getDataTable(list);
    }

    /**
     * 导出申报人档案列表
     */
    @PreAuthorize("@ss.hasPermi('models:configDeclarante:export')")
    @Log(title = "申报人档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConfigDeclarante configDeclarante)
    {
        List<ConfigDeclarante> list = configDeclaranteService.selectConfigDeclaranteList(configDeclarante);
        ExcelUtil<ConfigDeclarante> util = new ExcelUtil<ConfigDeclarante>(ConfigDeclarante.class);
        util.exportExcel(response, list, "申报人档案数据");
    }

    /**
     * 获取申报人档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('models:configDeclarante:query')")
    @GetMapping(value = "/{declaracionId}")
    public AjaxResult getInfo(@PathVariable("declaracionId") Long declaracionId)
    {
        return success(configDeclaranteService.selectConfigDeclaranteByDeclaracionId(declaracionId));
    }

    /**
     * 新增申报人档案
     */
    @PreAuthorize("@ss.hasPermi('models:configDeclarante:add')")
    @Log(title = "申报人档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigDeclarante configDeclarante)
    {
        return toAjax(configDeclaranteService.insertConfigDeclarante(configDeclarante));
    }

    /**
     * 修改申报人档案
     */
    @PreAuthorize("@ss.hasPermi('models:configDeclarante:edit')")
    @Log(title = "申报人档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigDeclarante configDeclarante)
    {
        return toAjax(configDeclaranteService.updateConfigDeclarante(configDeclarante));
    }

    /**
     * 删除申报人档案
     */
    @PreAuthorize("@ss.hasPermi('models:configDeclarante:remove')")
    @Log(title = "申报人档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{declaracionIds}")
    public AjaxResult remove(@PathVariable Long[] declaracionIds)
    {
        return toAjax(configDeclaranteService.deleteConfigDeclaranteByDeclaracionIds(declaracionIds));
    }

    /**
     * 下拉菜单
     */
    @PreAuthorize("@ss.hasPermi('models:configDeclarante:list')")
    @GetMapping("/selectDeclarantes")
    public AjaxResult selectDeclarantes()
    {
        return success(configDeclaranteService.selectDeclarantes());
    }
}
