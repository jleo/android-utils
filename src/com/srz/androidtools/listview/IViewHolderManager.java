package com.srz.androidtools.listview;

import android.view.View;

public interface IViewHolderManager {
      public ViewHolder creatViewHolder(View convertView);
      public void clearCache();
}
