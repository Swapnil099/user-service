package com.ubi.userservice.util;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;

public class AutogeneratePassword {

    @Autowired
    SecureRandom secureRandom;

    public String generate(){
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);

        PasswordGenerator passwordGenerator = new PasswordGenerator(secureRandom);
        String password = passwordGenerator.generatePassword(8, alphabets, digits);
        return password;
    }
}
