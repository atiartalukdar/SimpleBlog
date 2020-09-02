package com.athkarapplication.athkar.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.athkarapplication.athkar.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);

        ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setChooserTitle(R.string.app_name)
                .setText("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())
                .startChooser();

        return root;
    }
}