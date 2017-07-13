package com.seeker.tony.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.meiyou.jet.annotation.JFindView;
import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.process.Jet;

/**
 * BLank Fragment
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/25 上午9:45
 */
public class BlankFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context context;

    @JFindView(R.id.btn_findView)
    Button btn_findView;

    @JFindViewOnClick(R.id.btn_findview_onclick)
    Button btn_findview_onclick;

    @JFindView(R.id.listview)
    ListView listView;

    private String mParam1;
    private String mParam2;

    public BlankFragment() {
    }

    public static BlankFragment newInstance(Activity activity) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        String param1 = "Hello ";
        String param2 = "world";
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
        context = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = View.inflate(context, R.layout.fragment_blank, null);
//        View viewById = view.findViewById(R.id.btn_findView);
        Jet.bind(this, view);

        setListView();
        return view;
    }

    private void setListView() {
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_findView:
                Toast.makeText(context, "Btn findView", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_findview_onclick:
                Toast.makeText(context, "findView on Click ", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_fragment_blank, null);
                viewHolder = new ViewHolder(convertView);
//                viewHolder.btn_findView = (Button) convertView.findViewById(R.id.btn_findView);
//                viewHolder.btn_findview_onclick = (Button) convertView.findViewById(R.id.btn_findview_onclick);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.btn_findView.setText("Hello, New set");

            return convertView;
        }
    }

    class ViewHolder {
        @JFindView(R.id.btn_findView)
        Button btn_findView;

        @JFindView(R.id.btn_findview_onclick)
        Button btn_findview_onclick;


        public ViewHolder(View view) {
            Jet.bind(this, view);
        }

    }


}
