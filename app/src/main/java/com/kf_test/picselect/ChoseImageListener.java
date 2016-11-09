package com.kf_test.picselect;




/**
 * @author xiaolf1
 */
public interface ChoseImageListener {

    public void onSelected(ImageBean image, int selectedCount);

    public void onCancelSelect(ImageBean image, int selectedCount);
}
