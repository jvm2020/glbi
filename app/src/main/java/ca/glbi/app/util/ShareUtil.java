package ca.glbi.app.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;

import ca.glbi.app.R;

public class ShareUtil {

    public static void shareBrief(Context context, File pdfFile) {
        Uri pdfUri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                pdfFile
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.brief_share_subject));
        shareIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.brief_share_body));
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        Intent chooser = Intent.createChooser(shareIntent, "Share Brief");
        context.startActivity(chooser);
    }

    public static void openUrl(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }
}
