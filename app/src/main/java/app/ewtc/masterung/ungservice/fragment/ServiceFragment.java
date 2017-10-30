package app.ewtc.masterung.ungservice.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import app.ewtc.masterung.ungservice.MainActivity;
import app.ewtc.masterung.ungservice.R;
import app.ewtc.masterung.ungservice.utility.MyConstant;
import app.ewtc.masterung.ungservice.utility.MyDeleteData;
import app.ewtc.masterung.ungservice.utility.MyGetAllData;

/**
 * Created by masterung on 29/10/2017 AD.
 */

public class ServiceFragment extends Fragment {

    private String[] userStrings = new String[4];

    public static ServiceFragment serviceInstance(String[] strings) {

        ServiceFragment serviceFragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("User", strings);
        serviceFragment.setArguments(bundle);
        return serviceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userStrings = getArguments().getStringArray("User");
        Log.d("29octV1", "Name ==> " + userStrings[1]);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        Create ListView
        createListView();

    }

    private void createListView() {
        ListView listView = getView().findViewById(R.id.livService);
        MyConstant myConstant = new MyConstant();
        String[] columnStrings = myConstant.getColumeUserStrings();
        try {

            MyGetAllData myGetAllData = new MyGetAllData(getActivity());
            myGetAllData.execute(myConstant.getUrlGetUserString());
            String resultJSON = myGetAllData.get();

            JSONArray jsonArray = new JSONArray(resultJSON);
            final String[] idStrings = new String[jsonArray.length()];
            final String[] nameStrings = new String[jsonArray.length()];
            final String[] userStrings = new String[jsonArray.length()];
            final String[] passwordStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                idStrings[i] = jsonObject.getString(columnStrings[0]);
                nameStrings[i] = jsonObject.getString(columnStrings[1]);
                userStrings[i] = jsonObject.getString(columnStrings[2]);
                passwordStrings[i] = jsonObject.getString(columnStrings[3]);

            }

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, nameStrings);
            listView.setAdapter(stringArrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showAlert(idStrings[position], nameStrings[position],
                            userStrings[position], passwordStrings[position]);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showAlert(final String idString, String nameString, String userString, String passwordString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.icon_alert);
        builder.setTitle("Delete ==> " + nameString + " Are You Sure ?");
        builder.setMessage("User ==> " + userString + "\n" + "Password ==> " + passwordString);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRecordData(idString);
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void deleteRecordData(String idString) {
        try {

            MyConstant myConstant = new MyConstant();
            MyDeleteData myDeleteData = new MyDeleteData(getActivity());
            myDeleteData.execute(idString, myConstant.getUrlDeleteDataString());

            if (Boolean.parseBoolean(myDeleteData.get())) {
                Toast.makeText(getActivity(), "Delete Success", Toast.LENGTH_SHORT).show();
                createListView();
            } else {
                Toast.makeText(getActivity(), "Delete Error", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarService);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Welcome " + userStrings[1]);

        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;
    }
}
