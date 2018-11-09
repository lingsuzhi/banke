package com.lsz.web;

import com.lsz.common.soa.ResponseInfo;
import com.lsz.dto.OpenDoDTO;
import com.lsz.model.bo.LayuiNavbarBO;
import com.lsz.service.SaveFacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
public class OpenController {


    @Autowired
    private SaveFacesService saveFacesService;


    @RequestMapping("/z/{id}")
    public String openId(@PathVariable String id, Model model) {

        OpenDoDTO openDoDTO = saveFacesService.getMappingStr(id);
        if (openDoDTO == null || StringUtils.isEmpty(openDoDTO.getPath())) {
            return "404";
        }
        return saveFacesService.opendo(openDoDTO.getPath(), openDoDTO.getProjectName(), openDoDTO.getType(), model);
    }
}
