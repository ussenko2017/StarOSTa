package ru.mylx.starosta;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class stud_listFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList itemsSTUD_ID;
    ArrayList itemsFIO;
    ArrayList itemsBIRTHDAY;
    ArrayList itemsNUMBER;
    ArrayList itemsOTDEL_ID;
    ArrayList itemsGROUP;

    ArrayList itemsID;
    ArrayList itemsTEXT;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public stud_listFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList itemsArr_otdel = DBHelper.getDataFromTableOtdel(getView());
        ArrayList itemsArr_stud = DBHelper.getDataFromTableStud(getView().getContext());

        itemsSTUD_ID = (ArrayList) itemsArr_stud.get(0);
        itemsFIO = (ArrayList) itemsArr_stud.get(1);
        itemsBIRTHDAY = (ArrayList) itemsArr_stud.get(2);
        itemsNUMBER = (ArrayList) itemsArr_stud.get(3);
        itemsOTDEL_ID = (ArrayList) itemsArr_stud.get(4);
        itemsGROUP = (ArrayList) itemsArr_stud.get(5);

        itemsID = (ArrayList) itemsArr_otdel.get(0);
        itemsTEXT = (ArrayList) itemsArr_otdel.get(1);


        ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,itemsFIO);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, final View v, final int position, final long id) {
        super.onListItemClick(l, v, position, id);
        final int STUD_ID =  Integer.valueOf(itemsSTUD_ID.get(position).toString());
        String FIO = (itemsFIO.get(position).toString());
        String BIRTHDAY = (itemsBIRTHDAY.get(position).toString());
        String NUMBER =  (itemsNUMBER.get(position).toString());
        int OTDEL_ID =  Integer.valueOf(itemsOTDEL_ID.get(position).toString())-1;
        int GROUP =  Integer.valueOf(itemsGROUP.get(position).toString());
        final int iditems = (Integer) Integer.valueOf(itemsID.get(OTDEL_ID).toString());
        String textitems = (String) itemsTEXT.get(OTDEL_ID).toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        final DBHelper dbHelper = new DBHelper(v.getContext());
        String grp = "";
        if (GROUP == 0){
            grp = "А";
        }
        else{
            grp = "Б";
        }
        builder.setTitle(FIO)
                .setMessage(
                                "\n Дата рождения: "+BIRTHDAY+
                                "\n"+
                                "\n Личный номер телефона: "+ NUMBER+
                                "\n"+
                                "\n Отделение: " + textitems +
                                "\n"+
                                "\n Группа: "+grp +
                                "\n"+
                                "\n"+
                                "\n"+
                                "\n"+
                                "\n Удалить выбранный элемент?" )
                .setCancelable(true)
                .setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            dbHelper.delete(getView(),STUD_ID,2);

                            Toast.makeText(v.getContext(), "Удалено id"+String.valueOf(STUD_ID), Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e){
                            Toast.makeText(v.getContext(), "Функция платная, приобретите полную версию", Toast.LENGTH_LONG).show();

                        }
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        try {
            alert.show();
        }catch (Exception e){
            Toast.makeText(v.getContext(),"Err", Toast.LENGTH_SHORT).show();
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
