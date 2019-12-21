package com.firstdata.shopping.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;

public class ProgressDialogFragment extends DialogFragment {
    public static final String EXTRA_MESSAGE = "progressMessage";

    public static ProgressDialogFragment newInstance(String message) {
        final Bundle args = new Bundle();
        args.putString(EXTRA_MESSAGE, message);
        final ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.setArguments(args);
        return progressDialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        Bundle args = getArguments();
        final String messgae = args.getString(EXTRA_MESSAGE);
        dialog.setMessage(messgae);
        setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
