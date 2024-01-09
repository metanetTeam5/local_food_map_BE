package com.metanet.amatmu.utils;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {

	private final String ENGLISH_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String NUMBER_CHARACTERS = "0123456789";
    private final String SPECIAL_CHARACTERS = "@$!%*#?&";
    private final String ALL_CHARACTERS = ENGLISH_CHARACTERS + NUMBER_CHARACTERS + SPECIAL_CHARACTERS;
    
    private final SecureRandom random = new SecureRandom();
    
    public String generateTemporaryPassword() {
        StringBuilder passwordBuilder = new StringBuilder();

        passwordBuilder.append(getRandomCharacter(ENGLISH_CHARACTERS));
        passwordBuilder.append(getRandomCharacter(NUMBER_CHARACTERS));
        passwordBuilder.append(getRandomCharacter(SPECIAL_CHARACTERS));

        for (int i = 4; i < 8; i++) {
            passwordBuilder.append(getRandomCharacter(ALL_CHARACTERS));
        }

        char[] passwordChars = passwordBuilder.toString().toCharArray();
        for (int i = passwordChars.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordChars[index];
            passwordChars[index] = passwordChars[i];
            passwordChars[i] = temp;
        }

        return passwordBuilder.toString();
    }

    private char getRandomCharacter(String characterSet) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }
}
