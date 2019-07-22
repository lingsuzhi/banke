package com.lsz.web;

import com.lsz.common.soa.ResponseInfo;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.service.SaveFacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.lsz.service.SaveFacesService.ApiDir;

/**
 * Created by ex-lingsuzhi on 2018/3/20.
 */
@Controller
public class IndexController {


    @Autowired
    private SaveFacesService saveFacesService;

    @RequestMapping({"/index","/"})
    public String index(Model model,String proName,String s) {
        model.addAttribute("headMenu", saveFacesService.getHeadMenu());
        if(!StringUtils.isEmpty(s)){
            model.addAttribute("proName", "lsz");
            model.addAttribute("leftMenu",s);
        }else{
            model.addAttribute("proName", proName);
        }

        return "index";
    }

    @RequestMapping("/test1.php")
    public String test1(Model model) {
        return "test1";
    }

    @RequestMapping("/upload.php")
    public String upload(Model model) {
        return "upload";
    }

    @RequestMapping("/config")
    public String config(Model model) {
        String defUrl = "/wls/api/code";
        if (File.separator.equals("\\")) {
            defUrl = "d:\\source";
        }
        model.addAttribute("defUrl",defUrl);
        return "config";
    }
    @RequestMapping("/main.htm")
    public String mainhtm(Model model) {

        return "main";
    }
    @RequestMapping("/datas/navbarLeftMenu")
    @ResponseBody
    public List<LayuiNavbarBO> navbarLeftMenu(@RequestParam String leftMenu) {
        return saveFacesService.getLeftMenu(leftMenu);
    }
    @RequestMapping("/datas/navbar1")
    @ResponseBody
    public List<LayuiNavbarBO> navbar1(@RequestParam String dirName) {

        return saveFacesService.getNavbar(dirName,ApiDir);
    }
    @RequestMapping("/datas/navbarDto")
    @ResponseBody
    public List<LayuiNavbarBO> navbarDto(@RequestParam String dirName) {

        return saveFacesService.getNavbar(dirName,"dto");
    }
    /**
     * 搜索功能
     *
     * @param txtUrl      搜索路径
     * @param txtName     搜索名称
     * @param projectName 项目名称
     * @param findUrl     上一个路径
     * @return 菜单
     */
    @RequestMapping("/index/navbarFind")
    @ResponseBody
    public LayuiNavbarBO navbarFind(
            @RequestParam String txtUrl, @RequestParam String txtName, @RequestParam String projectName, @RequestParam String findUrl) {
        return saveFacesService.navbarFind(txtUrl, txtName, projectName, findUrl);
    }

    @RequestMapping(value = "/upload/postFile")
    @ResponseBody
    public ResponseInfo<?> uploadNunWidthBizId(@RequestParam("file") @RequestPart MultipartFile file,
                                               @RequestParam(value = "fileName", required = false) String fileName) throws IOException {
        if (!file.isEmpty()) {
            Path fullPath = Paths.get(SaveFacesService.FileDirP ,"head.json");
            File file1 = new File(fullPath.toUri());
            if(file1.exists()){
                file1.delete();
            }
            Files.write(fullPath, file.getBytes(), StandardOpenOption.CREATE);
            return new ResponseInfo("0","成功",null);
        } else {
            return ResponseInfo.error(null);
        }
    }

    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();//存储remove的位置

        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);//此处相当于要移除最后一个数据
        Iterator<Integer> iterator = integerList.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            if (next  == 2 || next== 4){
                iterator.remove();
            }
        }
        for (Integer i :integerList){
            System.out.println(i);
        }


    }

}
