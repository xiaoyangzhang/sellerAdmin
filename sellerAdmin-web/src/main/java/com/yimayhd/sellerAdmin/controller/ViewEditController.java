package com.yimayhd.sellerAdmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.taobao.tair.DataEntry;
import com.taobao.tair.Result;
import com.taobao.tair.ResultCode;
import com.taobao.tair.TairManager;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.result.item.CategoryResult;
import com.yimayhd.ic.client.service.item.CategoryService;
import com.yimayhd.sellerAdmin.base.BaseController;
import com.yimayhd.sellerAdmin.base.BaseException;
import com.yimayhd.sellerAdmin.base.ResponseVo;
import com.yimayhd.sellerAdmin.service.TfsService;

//import com.yimayhd.service.MessageCodeService;

/**
 *
 * @author
 */
@Controller
@RequestMapping("/view")
public class ViewEditController extends BaseController {

    @Autowired
    private CategoryService categoryServiceRef;
    @Autowired
    private TairManager tempDefaultTairManager;
    @Autowired
    private TfsService tfsService;

    /**
     * 富文本编辑页面
     * @return 富文本编辑页面
     * @throws Exception
     */
    @RequestMapping(value = "/toViewEditTest", method = RequestMethod.GET)
    public
    String toViewEdit(Model model) throws Exception {
        CategoryResult categoryResult = categoryServiceRef.getCategory(1);
        System.out.println(categoryResult.getResultCode());
        model.addAttribute("message","请输入帐号和密码");
        return "/demo/editView";
    }

    /**
     * 富文本编辑页面
     * @return 富文本编辑页面
     * @throws Exception
     */
    @RequestMapping(value = "/toCalendar", method = RequestMethod.GET)
    public
    String toCalendarEdit(Model model) throws Exception {

        return "/demo/calendar";
    }

    /**
     * tair测试
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/testTair", method = RequestMethod.GET)
    public
    String testTair(Model model) throws Exception {
        int nameSpase = 299;
        HotelDO hotelDO = new HotelDO();
        hotelDO.setName("czf123");
        ResultCode a = tempDefaultTairManager.put(nameSpase, "czf100209", hotelDO);
        System.out.println(JSON.toJSONString(a));
        Result<DataEntry> b = tempDefaultTairManager.get(nameSpase, "czf100209");
        System.out.println(JSON.toJSONString(b));
        return "/demo/calendar";
    }
    /**
     * 服务端上传h5
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/testUploadImgOrUe", method = RequestMethod.GET)
    public String toTestUploadImgOrUe(Model model) throws Exception {
        model.addAttribute("html","<script>hello张三");
        return "/demo/testUploadImgOrUe";
    }

    /**
     * 服务端
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/testUploadImgOrUe", method = RequestMethod.POST)
    public ResponseVo testUploadImgOrUe(String description) throws Exception {
        String addr = tfsService.publishHtml5(description);
        return new ResponseVo(addr);
    }
    /**
     * 服务端
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/testException", method = RequestMethod.GET)
    public ResponseVo testException() throws Exception {
        if(1 == 1){
            throw new BaseException("hello");
        }
        return new ResponseVo();
    }

}
