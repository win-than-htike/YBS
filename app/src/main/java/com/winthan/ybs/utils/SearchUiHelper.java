package com.winthan.ybs.utils;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/*
*
* Created by Ko Soe Gyi
*
* */

public class SearchUiHelper {

	private static final String EMPTY_STRING = "";

	public interface OnSearchListener {
		public void onSearchTextChanged(CharSequence searchText);
		public void onSearchSubmit(CharSequence searchText);
		public void onClearSearch();
	}
	
	private final TextWatcher mTextSearchTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
        	onQueryTextChanged(s, count);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        	if (mActionClearSearch == null) return;
        	if (TextUtils.isEmpty(mTextSearch.getText())) {
        		mActionClearSearch.setVisibility(View.GONE);
            } else {
            	mActionClearSearch.setVisibility(View.VISIBLE);
            }
        }
        
    };

    
    private final View.OnKeyListener mTextSearchOnKeyListener = new View.OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                switch (keyCode) {
	                case KeyEvent.KEYCODE_ENTER:
	                	return onQuerySubmit();
	                case KeyEvent.KEYCODE_ESCAPE:
	                	clearText();
	                    return true;
                }
            }

            return false;
        }
    };
    
    private final TextView.OnEditorActionListener mTextSearchOnEditorActionListener = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        	switch(actionId) {
				case EditorInfo.IME_ACTION_GO:
				case EditorInfo.IME_ACTION_NEXT:
				case EditorInfo.IME_ACTION_DONE:
				case EditorInfo.IME_ACTION_SEARCH:
				case EditorInfo.IME_ACTION_SEND:
				case EditorInfo.IME_ACTION_UNSPECIFIED:
					return onQuerySubmit();
				default:
					break;
			}
			
		    return false;
        }
    };
    
    private View.OnClickListener mSearchClearClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (mTextSearch != null) {
				mTextSearch.setText(EMPTY_STRING);
			}
		}
	};
	
	private final EditText mTextSearch;
	private final View mActionClearSearch;
	private CharSequence mSearchText;
	private OnSearchListener mSearchListener;
	private boolean mIsSearching;
	
	public SearchUiHelper(EditText textSearch, View actionClearSearch) {
		if (textSearch == null) {
			throw new NullPointerException();
		}
		mTextSearch = textSearch;
		mActionClearSearch = actionClearSearch;
		
		mTextSearch.addTextChangedListener(mTextSearchTextWatcher);
        mTextSearch.setOnKeyListener(mTextSearchOnKeyListener);
        mTextSearch.setOnEditorActionListener(mTextSearchOnEditorActionListener);
        mTextSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mTextSearch.setFocusableInTouchMode(true);
		mTextSearch.setSaveEnabled(true);
		
		if (mActionClearSearch != null) {
			mActionClearSearch.setOnClickListener(mSearchClearClick);
			if (TextUtils.isEmpty(mTextSearch.getText())) {
        		mActionClearSearch.setVisibility(View.GONE);
            } else {
            	mActionClearSearch.setVisibility(View.VISIBLE);
            }
		}
	}
	
	protected void onQueryTextChanged(CharSequence s, int count) {
		mSearchText = s;
		if (!mIsSearching && mSearchListener != null) {
			mSearchListener.onSearchTextChanged(mSearchText);
		}
	}
	
	protected boolean onQuerySubmit() {
		if (mTextSearch == null) return false;
		if (!mIsSearching && mSearchListener != null) {
			mSearchListener.onSearchSubmit(mSearchText);
		}
		return true;
	}
	
	public void clearText() {
		if (mTextSearch != null) {
			mTextSearch.clearComposingText();
			mTextSearch.setText(EMPTY_STRING);
			mSearchText = EMPTY_STRING;
			
			if (mSearchListener != null) {
				mSearchListener.onClearSearch();
			}
		}
	}
	
	public void setOnSearchListener(OnSearchListener listener) {
		mSearchListener = listener;
	}
	
	public CharSequence getSearchText() {
		if (mTextSearch != null) {
			mSearchText = mTextSearch.getText();
		}
		return mSearchText;
	}
	
	public boolean isSearching() {
		return mIsSearching;
	}
	
	public void setIsSearching(boolean value) {
		mIsSearching = value;
	}
}
