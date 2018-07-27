package com.lsz.web;

import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.service.SaveFacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */
@Controller
public class IndexController {


    @Autowired
    private SaveFacesService saveFacesService;

    @RequestMapping("/index.php")
    public String index(Model model) {


        return "index";
    }

    @RequestMapping("/main.htm")
    public String mainhtm() {
        return "face";
    }

    @RequestMapping("/datas/navbar1.json")
    @ResponseBody
    public List<LayuiNavbarBO> navbar1() {

        return saveFacesService.getNavbar();
//        List<LayuiNavbarBO> list = new ArrayList<>();
//
//        LayuiNavbarBO layuiNavbarBO = new LayuiNavbarBO();
//        layuiNavbarBO.setId("1");
//        layuiNavbarBO.setIcon("fa-cubes");
//        layuiNavbarBO.setTitle("基本元素");
//        layuiNavbarBO.setSpread(true);
//        list.add(layuiNavbarBO);
//
//        LayuiNavbarBO layuiNavbarBO3 = new LayuiNavbarBO();
//        layuiNavbarBO3.setId("13");
//        layuiNavbarBO3.setIcon("fa-cubes");
//        layuiNavbarBO3.setTitle("基本元素3");
//        layuiNavbarBO3.setUrl("www.baidu.com");
//        layuiNavbarBO.getChildren().add(layuiNavbarBO3);
//
//        LayuiNavbarBO layuiNavbarBO2 = new LayuiNavbarBO();
//        layuiNavbarBO2.setId("11");
//        layuiNavbarBO2.setIcon("fa-cubes");
//        layuiNavbarBO2.setTitle("基本元素1");
//        layuiNavbarBO2.setSpread(true);
//        list.add(layuiNavbarBO2);
//
//        return list;
    }
}
