package com.jasonette.seed.Action;

import com.onesignal.OneSignal;
import org.json.JSONObject;
import android.content.Context;
import com.jasonette.seed.Helper.JasonHelper;
import android.util.Log;

public class JasonOnesignalAction {

    public void set(final JSONObject action, JSONObject data, final JSONObject event, final Context context) {
        /*
         *  This function let you set the external onesignal id for the actual user
         *
         *            "type": "$onesignal.set",
         *            "options": {
         *              "externalId": "%id%",
         *            }
         */

        try {
            if (action.has("options")) {
                JSONObject options = action.getJSONObject("options");
                if(options.has("externalId")){

                    // REGISTER ID
                    String externalId = options.getString("externalId");
                    OneSignal.setExternalUserId(externalId);
                    JasonHelper.next("success", action, new JSONObject(), event, context);
                    Log.d("Warning", "externalId");
                }
                else {
                    JSONObject err = new JSONObject();
                    err.put("message", "externalId is empty");
                    JasonHelper.next("error", action, err, event, context);
                }
            }
            else {
                JSONObject err = new JSONObject();
                err.put("message", "$onesignal.set has no options defined");
                JasonHelper.next("error", action, err, event, context);
            }
        } catch (Exception e) {
            Log.d("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
        }
    }

    public void setTags(final JSONObject action, JSONObject data, final JSONObject event, final Context context) {
        /*
         *  This function let you set the All other event and user properties
         *
         */

        try {
            if (action.has("options")) {
                JSONObject options = action.getJSONObject("options");
                if(options.has("tagKey") && options.has("tagValue")){

                    // SET TAG
                    String key = options.getString("tagKey");
                    String value = options.getString("tagValue");
                    OneSignal.sendTag(key, value);
                    JasonHelper.next("success", action, new JSONObject(), event, context);

                }
                else {
                    JSONObject err = new JSONObject();
                    err.put("message", "Tag key or it's value is empty");
                    JasonHelper.next("error", action, err, event, context);
                }
            }
            else {
                JSONObject err = new JSONObject();
                err.put("message", "$onesignal.tag has no options defined");

            }
        } catch (Exception e) {
            Log.d("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
        }
    }

    public void getUserID(final JSONObject action, JSONObject data, final JSONObject event, final Context context){
        try {
            String userId = OneSignal.getDeviceState().getUserId();
            Log.d("Warning", userId);
            JSONObject ret = new JSONObject();
            ret.put("userId", userId);
            JasonHelper.next("success", action, ret, event, context);
        }
        catch (Exception e) {
            Log.d("Warning", e.getStackTrace()[0].getMethodName() + " : " + e.toString());
        }

    }
}

