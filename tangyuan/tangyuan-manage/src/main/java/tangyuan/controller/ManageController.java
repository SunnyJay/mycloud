package tangyuan.controller;

import com.tangyuan.exception.InternalServerException;
import com.tangyuan.exception.NotFoundException;
import com.tangyuan.exception.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tangyuan.domain.Instance;
import tangyuan.service.ManageService;

/**
 * 作者：sunna
 * 时间: 2018/5/11 11:22
 */
@RestController
@RequestMapping("tangyuan/manage")
public class ManageController
{
    @Autowired
    private ManageService manageService;

    @ApiOperation(value="获取实例列表", notes="获取实例列表")
    @GetMapping(value = "/instances")
    public Object getInstanceList()
    {
        return Result.get(manageService.getInstanceList());
    }

    @ApiOperation(value="添加实例", notes="根据instance创建实例")
    @PostMapping(value = "/instances")
    public Object addInstance(@RequestBody Instance instance) throws InternalServerException {
        return Result.get(manageService.addInstance(instance));
    }

    @ApiOperation(value="获取实例信息", notes="根据id获取实例信息")
    @GetMapping(value = "/instances/{id}")
    public Object getInstance(@PathVariable("id") String id) throws NotFoundException
    {
        return Result.get(manageService.getInstance(id));
    }

    @ApiOperation(value="删除实例", notes="根据id删除实例")
    @DeleteMapping(value = "/instances/{id}")
    public void deleteInstance(@PathVariable("id") String id)
    {
        manageService.deleteInstance(id);
    }

    @ApiOperation(value="更新实例信息", notes="根据id和instance更新实例信息")
    @PutMapping(value = "/instances/{id}")
    public Object updateInstance(@PathVariable String id, @RequestBody Instance instance) throws NotFoundException
    {
        return Result.get(manageService.updateInstance(id, instance));
    }

    @ApiOperation(value="部分更新实例信息", notes="根据id和instance部分更新实例信息")
    @PatchMapping(value = "/instances/{id}")
    public Object partialUpdateInstance(@PathVariable String id, @RequestBody Instance instance) throws NotFoundException
    {
        return Result.get(manageService.partialUpdateInstance(id, instance));
    }

    @ApiOperation(value="测试用接口", notes="测试用接口")
    @GetMapping(value = "/test")
    public String test()
    {
        return "test get!";
    }
}
