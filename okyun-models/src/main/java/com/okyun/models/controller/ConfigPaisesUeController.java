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
import com.okyun.models.domain.ConfigPaisesUe;
import com.okyun.models.service.IConfigPaisesUeService;
import com.okyun.common.utils.poi.ExcelUtil;
import com.okyun.common.core.page.TableDataInfo;

/**
 * 欧盟国家代码配置Controller
 * 
 * @author hubiao
 * @date 2025-12-14
 */
@RestController
@RequestMapping("/models/configPaisesUe")
public class ConfigPaisesUeController extends BaseController
{
    @Autowired
    private IConfigPaisesUeService configPaisesUeService;

    /**
     * 查询欧盟国家代码配置列表
     */
    @PreAuthorize("@ss.hasPermi('models:configPaisesUe:list')")
    @GetMapping("/list")
    public TableDataInfo list(ConfigPaisesUe configPaisesUe)
    {
        startPage();
        List<ConfigPaisesUe> list = configPaisesUeService.selectConfigPaisesUeList(configPaisesUe);
        return getDataTable(list);
    }

    /**
     * 导出欧盟国家代码配置列表
     */
    @PreAuthorize("@ss.hasPermi('models:configPaisesUe:export')")
    @Log(title = "欧盟国家代码配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConfigPaisesUe configPaisesUe)
    {
        List<ConfigPaisesUe> list = configPaisesUeService.selectConfigPaisesUeList(configPaisesUe);
        ExcelUtil<ConfigPaisesUe> util = new ExcelUtil<ConfigPaisesUe>(ConfigPaisesUe.class);
        util.exportExcel(response, list, "欧盟国家代码配置数据");
    }

    /**
     * 获取欧盟国家代码配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('models:configPaisesUe:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(configPaisesUeService.selectConfigPaisesUeById(id));
    }

    /**
     * 新增欧盟国家代码配置
     */
    @PreAuthorize("@ss.hasPermi('models:configPaisesUe:add')")
    @Log(title = "欧盟国家代码配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConfigPaisesUe configPaisesUe)
    {
        return toAjax(configPaisesUeService.insertConfigPaisesUe(configPaisesUe));
    }

    /**
     * 修改欧盟国家代码配置
     */
    @PreAuthorize("@ss.hasPermi('models:configPaisesUe:edit')")
    @Log(title = "欧盟国家代码配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConfigPaisesUe configPaisesUe)
    {
        return toAjax(configPaisesUeService.updateConfigPaisesUe(configPaisesUe));
    }

    /**
     * 删除欧盟国家代码配置
     */
    @PreAuthorize("@ss.hasPermi('models:configPaisesUe:remove')")
    @Log(title = "欧盟国家代码配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(configPaisesUeService.deleteConfigPaisesUeByIds(ids));
    }
}
