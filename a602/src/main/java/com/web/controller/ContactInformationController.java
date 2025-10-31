package com.web.controller;


import com.web.common.result.Result;
import com.web.entity.ContactInformation;
import com.web.mapper.ContactInformationMapper;
import com.web.service.IContactInformationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Mai
 * @since 2024-09-25
 */
@RestController
@RequestMapping("/contactInformation")
public class ContactInformationController {

    @Autowired
    private IContactInformationService iContactInformationService;
    @Autowired
    private ContactInformationMapper contactInformationMapper;

    @GetMapping()
    @ApiOperation("获取联系信息")
    public Result<List<ContactInformation>> getContactInformation(){
        List<ContactInformation> contactInformation = iContactInformationService.list();
        return Result.success(contactInformation);
    }
}
