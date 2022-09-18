package app.watchnode.ui.components;
import app.watchnode.R;
import app.watchnode.ui.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;

public class AppBar extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.topAppBar);
        System.out.println(999);
        myToolbar.setOnMenuItemClickListener(item->{
            System.out.println(1234);
            switch (item.getItemId()) {
                case R.id.logoutItem:
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return true;
                default:
                    return false;
            }
        });
    }
}
