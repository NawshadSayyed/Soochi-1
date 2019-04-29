package Extra;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.prathamesh.Authentication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bill extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill);

        String one = getIntent().getExtras().getString("1");
        String two = getIntent().getExtras().getString("2");
        String three = getIntent().getExtras().getString("3");
        String four = getIntent().getExtras().getString("4");
        String five = getIntent().getExtras().getString("5");

        TextView invoice, date, recieved, total, balance;
        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(2250, 1400, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);


        // draw something on the page
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.print);

        View content;

       content = inflater.inflate( R.layout.layout_bill, null); // the LayoutParams of view are set here
        layout.addView(content);
        layout.removeView(findViewById(R.id.button_pdf));
       // View content = (View) LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bill, layout,false);
      // layout.addView(content);


        int measureWidth = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getHeight(), View.MeasureSpec.EXACTLY);

        content.measure(measureWidth, measuredHeight);
        content.layout(0, 0, page.getCanvas().getWidth(), page.getCanvas().getHeight());

        invoice = (TextView)content.findViewById(R.id.text_View1);
        date = (TextView)content.findViewById(R.id.text_View2);
        total = (TextView)content.findViewById(R.id.text_View3);
        recieved = (TextView)content.findViewById(R.id.text_View4);
        balance = (TextView)content.findViewById(R.id.text_View5);

        invoice.setText(one);
        date.setText(two);
        total.setText(three);
        recieved.setText(four);
        balance.setText(five);


        content.draw(page.getCanvas());

        // finish the page
        document.finishPage(page);
        // add more pages
        // write the document content

        try {
            File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"filename.pdf");
            document.writeTo(new FileOutputStream(file));
           /* Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file),"application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            } */
        } catch (IOException e) {
            e.printStackTrace();
        }



        document.close();




    }
    }

