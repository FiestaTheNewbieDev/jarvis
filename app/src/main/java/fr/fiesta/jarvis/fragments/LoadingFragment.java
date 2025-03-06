package fr.fiesta.jarvis.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.fiesta.jarvis.R;

public class LoadingFragment extends Fragment {
    private FrameLayout frameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading, container, false);

        frameLayout = rootView.findViewById(R.id.frameLayout);

        return rootView;
    }

    public void show() {
        if (frameLayout != null) {
            frameLayout.setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if (frameLayout != null) {
            frameLayout.setVisibility(View.GONE);
        }
    }
}