package com.dantsu.escposprinter.connection;

import java.io.IOException;
import java.io.OutputStream;

public abstract class DeviceConnection {
    protected OutputStream stream;
    protected byte[] data;

    public DeviceConnection() {
        this.stream = null;
        this.data = new byte[0];
    }

    public abstract boolean connect();
    public abstract boolean disconnect();

    /**
     * Check if OutputStream is open.
     *
     * @return true if is connected
     */
    public boolean isConnected() {
        return this.stream != null;
    }

    /**
     * Add data to send.
     */
    public void write(byte[] bytes) {
        byte[] data = new byte[bytes.length + this.data.length];
        System.arraycopy(this.data, 0, data, 0, this.data.length);
        System.arraycopy(bytes, 0, data, this.data.length, bytes.length);
        this.data = data;
    }


    /**
     * Send data to the device.
     */
    public void send() {
        try {
            this.stream.write(this.data);
            this.stream.flush();
            this.data = new byte[0];

            int waitingTime = (int) Math.floor(this.data.length / 16f);
            if(waitingTime > 0) {
                Thread.sleep(waitingTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
