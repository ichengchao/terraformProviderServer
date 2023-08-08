package terraformDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import terraformDemo.model.Demo;
import terraformDemo.model.WebResult;
import terraformDemo.service.DemoService;

@RestController
@RequestMapping("/rest")
public class UserController extends BaseController {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Object getAllUser() {
        WebResult result = new WebResult();
        try {
            return demoService.searchDemo(null, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = "application/json")
    public Object addUser(@RequestBody Demo demo) {
        WebResult result = new WebResult();
        try {
            demoService.addDemo(demo);
            return demo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, produces = "application/json")
    public Object updateUser(@PathVariable String id, @RequestBody Demo demo) {
        WebResult result = new WebResult();
        try {
            demo.setId(id);
            demoService.updateDemo(demo);
            return demo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Object getUserById(@PathVariable String id) {
        WebResult result = new WebResult();
        try {
            Demo demo = demoService.getDemoById(id);
            return demo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;

    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public Object deleteUser(@PathVariable String id) {
        WebResult result = new WebResult();
        try {
            Demo demo = demoService.getDemoById(id);
            demoService.deleteDemo(id);
            return demo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

}
