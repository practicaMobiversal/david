package com.example.david.thumbsplit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Emanuela on 10/17/2017.
 */

public class LastElement {

    public long getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(long created_at) {
        this.createdAt = created_at;
    }

    public LastElement(long created_at) {
        this.createdAt = created_at;
    }
    @SerializedName("created_at")
    private long createdAt;
}
