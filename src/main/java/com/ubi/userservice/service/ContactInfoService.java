package com.ubi.userservice.service;

import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import com.ubi.userservice.entity.ContactInfo;

public interface ContactInfoService {
    public ContactInfo createContactInfo(ContactInfoDto contactInfoDto);

    ContactInfo updateContactInfo(ContactInfoDto contactInfoDto,Long contactInfoId);
}
