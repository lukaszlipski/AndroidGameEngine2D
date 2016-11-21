package com.lucek.androidgameengine2d.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucek.androidgameengine2d.core.graphics.CustomSurfaceView;

public class GameSurfaceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CustomSurfaceView surfaceView = new CustomSurfaceView(getActivity());
        return surfaceView;
    }
}
