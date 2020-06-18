package com.example.demochatsms16.dialog;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonActivity {


    public static boolean isNullOrEmpty(Object input) {
        if (input == null) {
            return true;
        }
        if (input instanceof String) {
            return input.toString().trim().isEmpty();
        }
        if (input instanceof EditText) {
            return ((EditText) input).getText().toString().trim().isEmpty();
        }
        if (input instanceof List) {
            return ((List) input).isEmpty();
        }

        if (input instanceof HashMap) {
            return ((HashMap) input).isEmpty();
        }

        return input instanceof ArrayList && ((ArrayList) input).isEmpty();
    }
}
