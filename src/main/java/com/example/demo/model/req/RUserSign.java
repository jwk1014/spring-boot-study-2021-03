package com.example.demo.model.req;

import com.example.demo.common.Constant;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RUserSign {

    @Getter
    public static class In {
        @NotBlank
        @Pattern(regexp = Constant.EMAIL_REGEX)
        private String email;
        @NotBlank
        @Pattern(regexp = Constant.PASSWORD_REGEX)
        private String password;
    }

    @Getter
    public static class Up {
        @NotBlank
        @Pattern(regexp = Constant.EMAIL_REGEX)
        private String email;
        @NotBlank
        @Pattern(regexp = Constant.PASSWORD_REGEX)
        private String password;
        @NotBlank
        @Pattern(regexp = "^[A-Za-z가-힣\\s]+$")
        private String name;
    }
}
