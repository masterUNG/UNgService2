package app.ewtc.masterung.ungservice.utility;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import app.ewtc.masterung.ungservice.R;

/**
 * Created by masterung on 29/10/2017 AD.
 */

public class MyAlert {

    //    Explicit
    private Context context;
//    Context คือ การสื่อสารระหว่าง Object

//    การสร้าง Constructor โดย Alr insert

    public MyAlert(Context context) {
        this.context = context;
    }   // Constructor

    public void myDialog(String strTitle, String strMessage) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);   // Cannot Put Undo
        builder.setIcon(R.drawable.icon_alert);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    }


}   // Main Class
