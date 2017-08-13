package com.j.enjpery.app.ui.publishtimeline;

/**
 * Created by J on 2017/8/13.
 */

class DeleteImageEvent {
    public DeleteImageEvent(Boolean deleteImage) {
        this.deleteImage = deleteImage;
    }

    private Boolean deleteImage;

    public Boolean getDeleteImage() {
        return deleteImage;
    }

    public void setDeleteImage(Boolean deleteImage) {
        this.deleteImage = deleteImage;
    }
}
