package com.luv2code.springboot.demosecurity.controller;


import com.luv2code.springboot.demosecurity.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


/**  To add n employee in the db refer the thymeleafxspringboot folder for that we need to use the crud ops
 *
 *
 *
 * refer that to add an employee into the folder and also we can update and delete the employees aswell*/




@Controller
public class DemoController {
    @GetMapping("/")
    public String showHome(){
        return "home";  //this string home is actually a html page
    }

    //add a request mapping for leaders
    @GetMapping("/leaders")
    public String showLeaders(){
        return "leaders";  //this string home is actually a html page
    }

    //add a request mapping for leaders
    @GetMapping("/systems")
    public String showSystemAdmins(){
        return "systems";  //this string home is actually a html page
    }

    //add a request mapping for access-denied
    @GetMapping("/access-denied")
    public String showAccessDenied(){
        return "accessDenied";  //this string home is actually a html page
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel){

        Member theMember =new Member();

        theModel.addAttribute("member", theMember);

        return "member-form";
    }

    @GetMapping("/save")
    public String saveMember(@ModelAttribute("member") Member theMember){
        //need to define employeesecurity and crud ops
    }
}
