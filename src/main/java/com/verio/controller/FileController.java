package com.verio.controller;

import com.verio.dto.Resp;
import com.verio.dto.RespResult;
import com.verio.entity.User;
import com.verio.utils.Assert;
import com.verio.utils.OssUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
@RequestMapping("/file")
public class FileController extends BaseController<User> {

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        String url = OssUtil.upload(file, loginUser.getId() + "");
        if (Assert.isEmpty(url)) {
            return Resp.resp(RespResult.fail("上传失败", url));
        }
        return Resp.resp(RespResult.success("上传成功", url));
    }
}
