package aad.app.hello.quickcontactbadge;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class GridViewItem extends FrameLayout {

	private String mName;
	private Uri mPhotoUri;
	
	private TextView mTextView;
	private ImageView mImageView;
	private QuickContactBadge mQuickContactBadge;
	
	public GridViewItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	public GridViewItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	public GridViewItem(Context context) {
		super(context);
		initialize(context);
	}
	
	public void initialize(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.gridview_item, this, true);
		
		mImageView = (ImageView) this.findViewById(R.id.photoImageView);
		mTextView = (TextView) this.findViewById(R.id.nameTextView);
		
		mQuickContactBadge = (QuickContactBadge) this.findViewById(R.id.quickbadge);		
	}

	
	public ImageView getImageView() {
		return mImageView;
	}
	
	public TextView getTextView() {
		return mTextView;
	}
	
	public void setName(String name) {
		mName = name;
		mTextView.setText(mName);
	}
	
	public void setPhoto(Uri photoUri, boolean useQCB) {
		mPhotoUri = photoUri;	
		if (useQCB)
			mQuickContactBadge.setImageURI(mPhotoUri);
		else
			mImageView.setImageURI(mPhotoUri);
	}
	
	public void setContactURI(Uri contactURI) {	
		mQuickContactBadge.assignContactUri(contactURI);		
	}
}
