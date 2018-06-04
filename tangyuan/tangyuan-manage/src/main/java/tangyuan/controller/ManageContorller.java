package tangyuan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tangyuan.domain.Instance;
import tangyuan.service.ManageService;

/**
 * 作者：sunna
 * 时间: 2018/5/11 11:22
 */
@RestController
@RequestMapping("tangyuan/manage/instances")
public class ManageContorller
{
    @Autowired
    private ManageService manageService;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public String getInstanceList()
    {
        return manageService.getInstanceList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public void addInstance(@RequestBody Instance instance)
    {
        manageService.addInstance(instance);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getInstanceInfo(@PathVariable("id") String id)
    {
        return manageService.getInstanceInfo(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteInstance(@PathVariable("id") String id)
    {
        manageService.deleteInstance(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void updateInstance(@PathVariable String id, @RequestBody Instance instance)
    {
        manageService.updateInstance(id, instance);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public void stopInstance(@PathVariable("id") String id)
    {
        manageService.stopInstance(id);
    }
}
