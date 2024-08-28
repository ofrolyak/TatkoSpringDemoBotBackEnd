package com.tatko.telegram.bot.service.custom.button;

import lombok.Getter;

@Getter
public enum InlineKeyButton {

    /**
     * GET_NEXT_DATE_FACT Inline Key Board Button.
     */
    GET_NEXT_DATE_FACT("get next date fact");

    /**
     * Text label for Inline Key Button.
     */
    private final String label;

    /**
     * Constructor itself.
     * @param labelInput Text labelInput.
     */
    InlineKeyButton(final String labelInput) {
        this.label = labelInput;
    }
}
