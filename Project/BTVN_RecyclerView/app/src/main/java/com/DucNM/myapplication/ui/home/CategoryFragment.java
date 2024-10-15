package com.DucNM.myapplication.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.DucNM.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    private List<Category> data;
    private RecyclerView rcv;

    private void createData() {
        data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add(new Category(R.drawable.pharmacy, "pharmacy"));
            data.add(new Category(R.drawable.registry, "registry"));
            data.add(new Category(R.drawable.cartwheel, "cartwheel"));
            data.add(new Category(R.drawable.clothing, "clothing"));
            data.add(new Category(R.drawable.shoes, "shoes"));
            data.add(new Category(R.drawable.accessories, "accessories"));
            data.add(new Category(R.drawable.baby, "baby"));
            data.add(new Category(R.drawable.home, "home"));
            data.add(new Category(R.drawable.patio_garden, "patio & garden"));
        }
    }

    private void bindingView(View view) {
        rcv = view.findViewById(R.id.rcv);
    }

    private void bindingAction() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_category, container, false);
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        createData();
        bindingView(view);
        bindingAction();
        initRcv();
        Log.d("DucNM_Debug", "create Category View roi nha");
        Log.d("DucNM_Debug", "rcv: " + rcv);
        Log.d("DucNM_Debug", "data8: " + data.get(8).getCategoryName());
        return view;
    }

    private void initRcv() {
        RecyclerView.Adapter adapter = new ListCategoryAdapter(data);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    }

}