package com.ubi.userservice.mapper;


import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import com.ubi.userservice.entity.ContactInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoMapper {
    @Autowired
    ModelMapper modelMapper;

    public ContactInfo toContactInfo(ContactInfoDto contactInfoDto){
        return modelMapper.map(contactInfoDto,ContactInfo.class);
    }

    public ContactInfoDto toContactInfoDto(ContactInfo contactInfo) {
        return modelMapper.map(contactInfo,ContactInfoDto.class);
    }
}
