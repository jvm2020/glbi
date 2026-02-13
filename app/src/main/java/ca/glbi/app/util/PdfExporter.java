package ca.glbi.app.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ca.glbi.app.model.BriefSubmission;

public class PdfExporter {
    private static final int PAGE_WIDTH = 595; // A4 width in points
    private static final int PAGE_HEIGHT = 842; // A4 height in points
    private static final int MARGIN = 50;
    private static final int LINE_HEIGHT = 20;

    public static File exportBrief(Context context, BriefSubmission brief) throws IOException {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextSize(12);

        int yPos = MARGIN;

        // Title
        paint.setTextSize(16);
        paint.setFakeBoldText(true);
        canvas.drawText("Parliamentary Brief - GLBI Support", MARGIN, yPos, paint);
        yPos += LINE_HEIGHT * 2;

        paint.setTextSize(12);
        paint.setFakeBoldText(false);

        // Name
        canvas.drawText("Name: " + brief.getName(), MARGIN, yPos, paint);
        yPos += LINE_HEIGHT;

        // Email
        if (brief.getEmail() != null && !brief.getEmail().isEmpty()) {
            canvas.drawText("Email: " + brief.getEmail(), MARGIN, yPos, paint);
            yPos += LINE_HEIGHT;
        }

        // Province
        canvas.drawText("Province/Territory: " + brief.getProvince(), MARGIN, yPos, paint);
        yPos += LINE_HEIGHT;

        // Riding
        if (brief.getRiding() != null && !brief.getRiding().isEmpty()) {
            canvas.drawText("Riding: " + brief.getRiding(), MARGIN, yPos, paint);
            yPos += LINE_HEIGHT;
        }

        // Organization
        if (brief.getOrganization() != null && !brief.getOrganization().isEmpty()) {
            canvas.drawText("Organization: " + brief.getOrganization(), MARGIN, yPos, paint);
            yPos += LINE_HEIGHT;
        }

        yPos += LINE_HEIGHT;

        // Position Statement
        paint.setFakeBoldText(true);
        canvas.drawText("Position Statement:", MARGIN, yPos, paint);
        yPos += LINE_HEIGHT;
        paint.setFakeBoldText(false);
        yPos = drawMultilineText(canvas, paint, brief.getPositionStatement(), MARGIN, yPos);

        yPos += LINE_HEIGHT;

        // Full Brief
        paint.setFakeBoldText(true);
        canvas.drawText("Full Brief:", MARGIN, yPos, paint);
        yPos += LINE_HEIGHT;
        paint.setFakeBoldText(false);
        drawMultilineText(canvas, paint, brief.getFullBrief(), MARGIN, yPos);

        pdfDocument.finishPage(page);

        // Save to file
        File pdfFile = createPdfFile(context);
        FileOutputStream fos = new FileOutputStream(pdfFile);
        pdfDocument.writeTo(fos);
        pdfDocument.close();
        fos.close();

        return pdfFile;
    }

    private static int drawMultilineText(Canvas canvas, Paint paint, String text, int x, int y) {
        int maxWidth = PAGE_WIDTH - (2 * MARGIN);
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int yPos = y;

        for (String word : words) {
            String testLine = line + word + " ";
            float testWidth = paint.measureText(testLine);

            if (testWidth > maxWidth && line.length() > 0) {
                canvas.drawText(line.toString().trim(), x, yPos, paint);
                yPos += LINE_HEIGHT;
                line = new StringBuilder(word + " ");
            } else {
                line.append(word).append(" ");
            }
        }

        if (line.length() > 0) {
            canvas.drawText(line.toString().trim(), x, yPos, paint);
            yPos += LINE_HEIGHT;
        }

        return yPos;
    }

    private static File createPdfFile(Context context) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String filename = "GLBI_Brief_" + timestamp + ".pdf";

        File directory;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        } else {
            directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GLBICanada");
            if (!directory.exists()) {
                directory.mkdirs();
            }
        }

        return new File(directory, filename);
    }
}
