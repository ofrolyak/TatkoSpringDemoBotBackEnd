package com.tatko.telegram.bot.service.custom.button;

import lombok.Getter;

@Getter
public enum KeyButton {

    /**
     * SEND_NEXT_DATE_FACT Key Button.
     */
    SEND_NEXT_DATE_FACT("send next date fact"),

    /**
     * SEND_AD Key Button.
     */
    SEND_AD("send ad"),

    /**
     * GET_MY_DATA Key Button.
     */
    GET_MY_DATA("get my data"),

    /**
     * DELETE_MY_DATA Key Button.
     */
    DELETE_MY_DATA("delete my data");

    /**
     * Text label for Key Button.
     */
    private final String label;

    /**
     * Constructor itself.
     * @param labelInput
     */
    KeyButton(final String labelInput) {
        this.label = labelInput;
    }
}
