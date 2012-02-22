package com.szlab.solarcharger;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;

public class helpActivity  extends SolarChargerBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		//TextView txt = (TextView) findViewById(R.id.textView1);
		//txt.setText(Html.fromHtml("<h2>Title</h2><br><p>Description here</p>"));
		//txt.setText(Html.fromHtml(getResources().getString(R.string.helpLong)));
		
WebView web = (WebView) findViewById(R.id.webView1);
web.setBackgroundColor(0);
web.loadData(getResources().getString(R.string.helpLong), "text/html", null);

	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		//menu.removeItem();
		menu.findItem(R.id.menuHelp).setEnabled(false);
		return super.onPrepareOptionsMenu(menu);
	};
	
}
