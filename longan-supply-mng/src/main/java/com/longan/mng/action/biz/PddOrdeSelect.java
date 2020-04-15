package com.longan.mng.action.biz;

import com.longan.biz.core.BizOrderService;
import com.longan.biz.core.PddOrderService;
import com.longan.biz.core.UserService;
import com.longan.biz.dataobject.BizOrder;
import com.longan.biz.dataobject.PddOrder;
import com.longan.biz.domain.Result;
import com.longan.mng.action.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("pdd/pddOrder")
public class PddOrdeSelect extends BaseController {
    //accessToken是从memcached中获取的
    @Resource
    private UserService userService;
    @Resource
    private PddOrderService pddOrderService;
    @Resource
    private BizOrderService bizOrderService;

    @RequestMapping(method = RequestMethod.GET)
    public String selectPddOrder(@RequestParam("id") Long id, Model model) throws Exception {
        Result<BizOrder> bizOrder = bizOrderService.selectRemaks(id);
        BizOrder bizOrders = bizOrder.getModule();
        if (bizOrders.getUserId().equals("329")) {
            Result<PddOrder> pddOrderResult = pddOrderService.selectOrder(bizOrders.getId());
            PddOrder pddOrder = pddOrderResult.getModule();


        } else {
            return null;
        }

        return null;
    }



}
