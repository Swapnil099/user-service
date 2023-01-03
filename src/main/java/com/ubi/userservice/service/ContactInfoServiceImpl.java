package com.ubi.userservice.service;

import com.ubi.userservice.dto.contactInfo.ContactInfoDto;
import com.ubi.userservice.entity.ContactInfo;
import com.ubi.userservice.error.CustomException;
import com.ubi.userservice.error.HttpStatusCode;
import com.ubi.userservice.mapper.ContactInfoMapper;
import com.ubi.userservice.repository.ContactInfoRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class ContactInfoServiceImpl implements ContactInfoService{

    @Autowired
    ContactInfoMapper contactInfoMapper;

    @Autowired
    ContactInfoRepository contactInfoRepository;

    @Override
    public ContactInfo createContactInfo(ContactInfoDto contactInfoDto) {
        ContactInfo contactInfo = contactInfoMapper.toContactInfo(contactInfoDto);
        contactInfo = contactInfoRepository.save(contactInfo);
        return contactInfo;
    }

    @Override
    public ContactInfo updateContactInfo(ContactInfoDto contactInfoDto,Long contactInfoId) {
        ContactInfo contactInfo = contactInfoRepository.getReferenceById(contactInfoId);

        contactInfo.setAadharCardNumber(contactInfoDto.getAadharCardNumber());
        contactInfo.setAddress(contactInfoDto.getAddress());
        contactInfo.setAge(contactInfoDto.getAge());
        contactInfo.setBloodGroup(contactInfoDto.getBloodGroup());
        contactInfo.setCity(contactInfoDto.getCity());
        contactInfo.setContactNumber(contactInfoDto.getContactNumber());
        contactInfo.setDob(contactInfoDto.getDob());
        contactInfo.setEmail(contactInfoDto.getEmail());
        contactInfo.setFirstName(contactInfoDto.getFirstName());
        contactInfo.setGender(contactInfoDto.getGender());
        contactInfo.setLastName(contactInfoDto.getLastName());
        contactInfo.setMiddleName(contactInfoDto.getMiddleName());
        contactInfo.setNationality(contactInfoDto.getNationality());
        contactInfo.setState(contactInfoDto.getState());

        ContactInfo savedContactInfo = contactInfoRepository.save(contactInfo);
        return savedContactInfo;
    }
}
