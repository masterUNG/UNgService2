package app.ewtc.masterung.ungservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import app.ewtc.masterung.ungservice.R;
import app.ewtc.masterung.ungservice.utility.MyAlert;
import app.ewtc.masterung.ungservice.utility.MyConstant;
import app.ewtc.masterung.ungservice.utility.MyGetAllData;

/**
 * Created by masterung on 28/10/2017 AD.
 */

public class MainFragment extends Fragment {

    private String userString, passwordString;

    //    Create Main Method
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();

    }   // Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                if (userString.equals("") || passwordString.equals("")) {

                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("Have Space", "Please Fill All");

                } else {

                    checkUserAndPass();

                }

            }   // onClick
        });
    }

    private void checkUserAndPass() {

        try {

            String tag = "29octV1";
            boolean b = true;
            String[] strings = new String[4];
            MyConstant myConstant = new MyConstant();
            MyAlert myAlert = new MyAlert(getActivity());
            String[] columnUserStrings1 = myConstant.getColumeUserStrings();
            MyGetAllData myGetAllData = new MyGetAllData(getActivity());
            myGetAllData.execute(myConstant.getUrlGetUserString());

            String resultJSON = myGetAllData.get();
            Log.d(tag, "JSON ==> " + resultJSON);

            JSONArray jsonArray = new JSONArray(resultJSON);
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {
                    b = false;
                    for (int i1=0; i1<strings.length; i1++) {
                        strings[i1] = jsonObject.getString(columnUserStrings1[i1]);
                    }
                }
            }

            if (b) {
                myAlert.myDialog("User False", "No This User in my Database");
            } else {
                if (passwordString.equals(strings[3])) {
                    Toast.makeText(getActivity(),
                            "Welcome " + strings[1], Toast.LENGTH_SHORT).show();
                } else {
                    myAlert.myDialog("Password False",
                            "Please Try Agains Password False");
                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtNewRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Move to RegisterFragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFragmentMain, new RegisterFragment())
                        .addToBackStack(null).commit();

            }
        });
    }

    //    Create View Group
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

}   // Main Class
