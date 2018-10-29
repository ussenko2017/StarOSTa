package ru.mylx.starosta;




import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addStudFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addStudFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addStudFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList itemsID;
    ArrayList itemsTEXT;


    int DIALOG_DATE = 1;
    int mYear = 2011;
    int mMonth = 02;
    int mDay = 03;
    int myYear = 2011;
    int myMonth = 02;
    int myDay = 03;
    TextView tvDate;

    View view;
    public addStudFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addStudFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addStudFragment newInstance(String param1, String param2) {
        addStudFragment fragment = new addStudFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }
    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        final int[] arr = new int[3];


        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                       myYear = year;
                       myMonth = monthOfYear+1;
                       myDay = dayOfMonth;
                        tvDate.setText("Дата рождения: "+myYear+"."+myMonth+"."+myDay);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.fragment_add_stud, container, false);
        // Inflate the layout for this fragment
        final EditText editText = (EditText) view.findViewById(R.id.editText);
        final EditText editText2 = (EditText) view.findViewById(R.id.editText2);
        final EditText editText3 = (EditText) view.findViewById(R.id.editText3);
        final EditText editText7 = (EditText) view.findViewById(R.id.editText7);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        final Button button = (Button) view.findViewById(R.id.button);
        tvDate = (TextView) view.findViewById(R.id.textView8);






        DBHelper dbHelper;
        SQLiteDatabase db;
        Cursor userCursor;
        SimpleCursorAdapter userAdapter;
        ArrayList itemsArr = DBHelper.getDataFromTableOtdel(view);
        itemsID = (ArrayList) itemsArr.get(0);
        itemsTEXT = (ArrayList) itemsArr.get(1);
        try {
            SpinnerAdapter adapter = new ArrayAdapter<>(view.getContext(),
                    android.R.layout.simple_spinner_dropdown_item, itemsTEXT);
            spinner2.setAdapter(adapter);
        }
        catch (Exception e){
            Toast.makeText(view.getContext(),"err",Toast.LENGTH_SHORT).show();
        }
        /*
        //ne rabotaet(((
        try{
        dbHelper = new DBHelper(view.getContext());
        db = dbHelper.getReadableDatabase();
        userCursor = db.rawQuery("select * from "+DBHelper.T4,null);
        String[] headers = new String[] {DBHelper.T4_OTDEL_ID, DBHelper.T4_OTDELNAME};
        userAdapter = new SimpleCursorAdapter(view.getContext(),android.R.layout.simple_spinner_dropdown_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 1);
        spinner2.setAdapter(userAdapter);
        userCursor.close();
        }
        catch (Exception E){
            Toast.makeText(view.getContext(),"Ошибка",Toast.LENGTH_SHORT).show();
        }
*/
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               callDatePicker();

            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Добавление студента ")
                        .setMessage("Вы уверены в правильности введенных данных? ")
                        .setCancelable(true)
                        .setNegativeButton("Подтвердить ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {

                                    int id_otdel = Integer.valueOf(itemsID.get(spinner2.getSelectedItemPosition()).toString());
                                    if (editText.getText().toString().equals("")|
                                            editText2.getText().toString().equals("")|
                                            editText3.getText().toString().equals("")|
                                            editText7.getText().toString().equals("")) {
                                        Toast.makeText(view.getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        DBHelper.add_to_t2(view,editText.getText().toString() +" "+
                                                        editText2.getText().toString() +" "+
                                                        editText3.getText().toString(),

                                                myYear+"."+myMonth+"."+myDay,
                                                spinner.getSelectedItemPosition(),
                                                editText7.getText().toString(),
                                                id_otdel);
                                    }

                                    Toast.makeText(view.getContext(), "Готово" , Toast.LENGTH_LONG).show();
                                }
                                catch (Exception e){
                                    Toast.makeText(view.getContext(), "Функция платная, приобретите полную версию", Toast.LENGTH_LONG).show();

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
        });



        return view;


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
