package com.photo.warehouse.controller.backstage;

import com.photo.warehouse.biz.backstage.OptionsLibBiz;
import com.photo.warehouse.model.backstage.OptionsLib;
import com.photo.warehouse.util.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by CDZ on 2018/12/8.
 * 只有新增 删除 编辑状态，查看，没有修改   因为表结构没有id
 */
@RestController
@RequestMapping("/api/photo/optionsLib")
public class OptionsLibController extends BaseController<OptionsLibBiz,OptionsLib> {

    /**
     * 编辑状态
     * @param
     */
    @RequestMapping(value = "/updateIblank/{iBlank}",method = RequestMethod.PUT)
    public void editIBlank(@PathVariable("iBlank") Integer iBlank,@RequestBody List<OptionsLib> optionsLibList){
        for(OptionsLib optionsLib : optionsLibList){
            optionsLib.setiBlank(iBlank);
            baseBiz.updateByIBlank(optionsLib);
        }
    }

    @RequestMapping(value = "/all/{cOpti}",method = RequestMethod.GET)
    @ResponseBody
    public List<OptionsLib> all(@PathVariable("cOpti") String cOpti) {
        return baseBiz.selectByvcDescribe(null,cOpti);
    }


    /**
     * 删除
     * @param
     */
    @RequestMapping(value = "/delete",method = RequestMethod.PUT)
    public void deleteOptionsLib(@RequestBody List<OptionsLib>  optionsLibList){
        for(OptionsLib optionsLib : optionsLibList){
            baseBiz.deleteOptionsLib(optionsLib);
        }
    }
}
