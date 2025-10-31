package com.web.service.serviceImpl;

import com.web.entity.ContactInformation;
import com.web.mapper.ContactInformationMapper;
import com.web.service.IContactInformationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mai
 * @since 2024-09-25
 */
@Service
public class ContactInformationServiceImpl extends ServiceImpl<ContactInformationMapper, ContactInformation> implements IContactInformationService {

}
