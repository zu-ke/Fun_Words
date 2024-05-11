package com.zk.web;

import com.google.gson.Gson;
import com.zk.entity.Classify;
import com.zk.entity.Msg;
import com.zk.entity.Word;
import com.zk.service.ClassifyService;
import com.zk.service.WordService;
import com.zk.service.impl.ClassifyServiceImp;
import com.zk.service.impl.WordServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getClassify")
public class ClassifyServlet extends BasicServlet{

    private ClassifyService classifyService = new ClassifyServiceImp();

    protected void getClassify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Classify> claList = classifyService.getAll();
        Msg msg = Msg.success().add("claList", claList);
        Gson gson = new Gson();
        String resultJson = gson.toJson(msg);
        response.getWriter().write(resultJson);
    }
}
