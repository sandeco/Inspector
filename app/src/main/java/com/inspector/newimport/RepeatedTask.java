package com.inspector.newimport;

public abstract class RepeatedTask implements Runnable {
    private static final String TAG = "RepeatedTask";

    private boolean mRunning;
    private int mSeconds;
    private Thread mThread;

    public RepeatedTask() {
        this.mThread = new Thread(this);
        this.mRunning = false;
    }

    @Override
    public void run() {

        if (mRunning) {
            onRunning();

            try {
                Thread.sleep(mSeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract void onRunning();

    /**
     * Executa periodicamente a sincronização de dados no intervalo de segundos passado
     * @param seconds Segundo entre cada tentativa de sincronização
     * @throws UnsupportedOperationException Disparada quando as tentativas de sincronismo já foram ativadas.
     */
    public void syncEachSeconds(int seconds) {

        if (mRunning) {
            throw new UnsupportedOperationException("Already running various sync attempts, stop them with stopSync()");
        } else {
            mRunning = true;
            mSeconds = seconds;
            mThread.start();
        }
    }

    /**
     * Parar o ciclo de tentativas de sincronismo
     */
    public void stopSync() {
        mRunning = false;
    }
}
