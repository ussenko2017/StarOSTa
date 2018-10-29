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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class journalToStudentFragment extends ListFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int activitychecker = 0;

    //ozh
    ArrayList ozh_id_arr;
    ArrayList ozh_fio_arr;
    ArrayList ozh_date_arr;
    ArrayList ozh_ball_arr;
    ArrayList ozh_arr;

    //stud
    ArrayList stud_id_arr;
    ArrayList stud_fio_arr;

    //predmet
    ArrayList predmet_id_arr;
    ArrayList predmet_text_arr;


    int select_id_predmet;
    int select_id_stud;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment journalToStudentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static journalToStudentFragment newInstance(String param1, String param2) {
        journalToStudentFragment fragment = new journalToStudentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public journalToStudentFragment() {
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
        activitychecker = 0;

        ArrayList predmet_arr = DBHelper.getDataFromTablePredmet(getView());
        ArrayList stud_arr = DBHelper.getDataFromTableStud(getView().getContext());


        predmet_id_arr = (ArrayList) predmet_arr.get(0);
        predmet_text_arr = (ArrayList)  predmet_arr.get(1);

        stud_id_arr = (ArrayList)  stud_arr.get(0);
        stud_fio_arr = (ArrayList)  stud_arr.get(1);

        ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,predmet_text_arr);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, final View v, final int position, final long id) {
        super.onListItemClick(l, v, position, id);
        if (activitychecker ==  0){
            select_id_predmet = Integer.valueOf(predmet_id_arr.get(position).toString());

            activitychecker+=1;
            ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,stud_fio_arr);
            setListAdapter(adapter);

        }else if (activitychecker == 1){
            select_id_stud = Integer.valueOf(stud_id_arr.get(position).toString());
            ozh_arr = DBHelper.returnJournalToStudent(v,select_id_predmet,select_id_stud);
            ListAdapter adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,(ArrayList) ozh_arr.get(4));
            setListAdapter(adapter);
            activitychecker+=1;

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
