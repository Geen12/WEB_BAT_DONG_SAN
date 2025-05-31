package com.example.Intern.Controller;

import com.example.Intern.Entity.Property;
import com.example.Intern.Handle.PropertyHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private PropertyHandle propertyHandle;

    // Hiển thị trang chủ và danh sách các bất động sản
    @GetMapping({"/"})
    public String publicAccess(Model model) {
        List<Property> properties = propertyHandle.findPropertyDetails(); // Lấy danh sách bất động sản
        model.addAttribute("properties", properties); // Truyền dữ liệu vào model
        return "home"; // Trả về trang index
    }

    @GetMapping("/index")
    public String publicAAccess(Model model) {
        List<Property> properties = propertyHandle.findPropertyDetails(); // Lấy danh sách bất động sản
        model.addAttribute("properties", properties); // Truyền dữ liệu vào model
        return "index"; // Trả về trang index
    }
}