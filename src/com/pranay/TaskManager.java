package com.pranay;

public interface TaskManager {

    public int add(int job);
    public void remove(int jobId);
    public int status(int jobId);
    public void stop();

}
