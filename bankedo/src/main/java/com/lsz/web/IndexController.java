package com.lsz.web;

import com.lsz.common.soa.ResponseInfo;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.service.SaveFacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
        model.addAttribute("headMenu", saveFacesService.getHeadMenu());
        return "index";
    }

    @RequestMapping("/test1.php")
    public String test1(Model model) {
        return "test1";
    }

    @RequestMapping("/main.htm")
    public String mainhtm() {
        return "main";
    }

    @RequestMapping("/datas/navbar1")
    @ResponseBody
    public List<LayuiNavbarBO> navbar1(@RequestParam String dirName) {

        return saveFacesService.getNavbar(dirName,"api");
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
    public ResponseInfo<?> uploadNunWidthBizId(@RequestParam("file") MultipartFile file,
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

}
