package com.jujutsu.me;

import com.jujutsu.tsne.TSneConfiguration;
import com.jujutsu.tsne.barneshut.BHTSne;
import com.jujutsu.tsne.barneshut.BarnesHutTSne;
import com.jujutsu.tsne.barneshut.ParallelBHTsne;

/**
 * TsneThred
 *
 * @author hjl
 */
public class TsneThred implements Runnable {

    /**
     * TSneConfiguration
     */
    private TSneConfiguration config;

    public TsneThred(TSneConfiguration config) {
        this.config = config;
    }

    @Override
    public void run() {
        BarnesHutTSne tsne;
        boolean parallel = true;
        if (parallel) {
            tsne = new ParallelBHTsne();
        }
        else {
            tsne = new BHTSne();
        }

        tsne.tsne(config);
    }
}
