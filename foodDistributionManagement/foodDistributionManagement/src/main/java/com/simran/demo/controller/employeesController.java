package com.simran.demo.controller;
import com.simran.demo.dao.*;
import com.simran.demo.model.*;
// import com.simran.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class employeesController {


    @Autowired
    private employeeDAO employeeDAO;


    @GetMapping("/Employees")
    public String listEmployees(Model model, HttpSession session, @RequestParam(required = false) String id) {
        // if(!authenticationService.isAuthenticated(session)){
        //     return "redirect:/login";
        // }
        List<Employees> employees = new ArrayList<>();
        if (id == null || id == "") {
            employees = employeeDAO.getAllEmployee();
        } else {
            String intid = id;
            try {
                Employees emps = employeeDAO.getEmployeeByID(intid);
                employees.add(emps);
            } catch (Exception e) {
                System.out.println("Employee NOT FOUND!");
                // System.out.println(e);
            }
        }
        model.addAttribute("employees", employees);
        return "Employees";
    }

    @GetMapping ("/Employees/create")
    public String createEmployee(Model model, HttpSession session)
    {
        // if(!authenticationService.isAuthenticated(session)){
        //     return "redirect:/login";
        // }
        Employees employees = new Employees();
        model.addAttribute("employees", employees);
        return "EmployeeCreate";
    }
    
    @PostMapping("/Employees/create")
    public String createEmployeePost(@ModelAttribute("employees") Employees employees, HttpSession session)
    {
        // if(!authenticationService.isAuthenticated(session)){
        //     return "redirect:/login";
        // }
        employeeDAO.insertEmployee(employees);
        return "redirect:/Employees";
    }
    @GetMapping ("/Employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") String id, HttpSession session)
    {
        // if(!authenticationService.isAuthenticated(session)){
        //     return "redirect:/login";
        // }
        employeeDAO.deleteEmployee(id);
        return "redirect:/Employees";
    }
    @GetMapping ("/Employees/edit/{id}")
    public String editEmployee(@PathVariable("id") String id, Model model, HttpSession session)
    {
        // if(!authenticationService.isAuthenticated(session)){
        //     return "redirect:/login";
        // }
        Employees employees = employeeDAO.getEmployeeByID(id);
        System.out.println("HAHAHAH NOOOBS 1");
        model.addAttribute("employees", employees);
        return "EmployeeEdit.html";
    }

    @PostMapping ("/Employees/edit/{id}")
    public String editEmployeePost(@PathVariable("id") String id, @ModelAttribute("employees") Employees employees, HttpSession session)
    {
        System.out.println("HAHAHAH NOOOBS 2");
        // if(!authenticationService.isAuthenticated(session)){
        //     return "redirect:/login";
        // }
        employeeDAO.updateEmployee(id,employees);
        return "redirect:/Employees";
    }
}
