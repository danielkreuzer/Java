package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import swt6.spring.worklog.logic.WorkLogFacade;

@Controller
public class EmployeeController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private WorkLogFacade workLog;

    @RequestMapping(value = "/employees")
    public String listEmployees(Model model) {
        var employees = workLog.findAllEmployees();
        logger.info("employees = {]", employees);
        model.addAttribute("employees",employees);

        return "employeeList";
    }
}
