package com.example.catalin.chat;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

class InputTextHandler extends android.support.v7.widget.AppCompatEditText{

    private final EditText inputTextView;

    InputTextHandler(Context context, EditText editText)
    {
        super(context);
        inputTextView = editText;
        inputTextView.setOnClickListener(new InputTextClickHandler());
    }

    Editable getInputTextAsEditable()
    {
        return inputTextView.getText();
    }

    String getInputTextAsString()
    {
        return inputTextView.getText().toString();
    }

    void setInputViewText(String text)
    {
        inputTextView.setText(text);
    }

    void setKeyboardCursorVisible(boolean s)
    {
        inputTextView.setCursorVisible(s);
    }

    private class InputTextClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            setKeyboardCursorVisible(true);
        }
    }

}
