package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class NoticeForm {
    @NotEmpty
    @Size(min = 5, message = "Expected more than 4 letters")
    @Size(max = 255, message = "Expected less than 256 letters")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
