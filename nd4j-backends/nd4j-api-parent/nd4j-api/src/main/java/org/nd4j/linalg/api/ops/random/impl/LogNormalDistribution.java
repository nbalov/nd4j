package org.nd4j.linalg.api.ops.random.impl;

import lombok.NonNull;
import org.nd4j.autodiff.functions.DifferentialFunction;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.random.BaseRandomOp;

import java.util.List;

/**
 * This Op generates log-normal distribution over provided mean and stddev
 *
 * @author raver119@gmail.com
 */
public class LogNormalDistribution extends BaseRandomOp {
    private double mean;
    private double stddev;

    public LogNormalDistribution() {
        super();
    }

    /**
     * This op fills Z with random values within stddev..mean..stddev boundaries
     * @param z
     * @param mean
     * @param stddev
     */
    public LogNormalDistribution(@NonNull INDArray z, double mean, double stddev) {
        init(z, z, z, z.lengthLong());
        this.mean = mean;
        this.stddev = stddev;
        this.extraArgs = new Object[] {this.mean, this.stddev};
    }


    public LogNormalDistribution(@NonNull INDArray z, @NonNull INDArray means, double stddev) {
        if (z.lengthLong() != means.lengthLong())
            throw new IllegalStateException("Result length should be equal to provided Means length");

        if (means.elementWiseStride() < 1)
            throw new IllegalStateException("Means array can't have negative EWS");

        init(z, means, z, z.lengthLong());
        this.mean = 0.0;
        this.stddev = stddev;
        this.extraArgs = new Object[] {this.mean, this.stddev};
    }

    /**
     * This op fills Z with random values within -1.0..0..1.0
     * @param z
     */
    public LogNormalDistribution(@NonNull INDArray z) {
        this(z, 0.0, 1.0);
    }

    /**
     * This op fills Z with random values within stddev..0..stddev
     * @param z
     */
    public LogNormalDistribution(@NonNull INDArray z, double stddev) {
        this(z, 0.0, stddev);
    }

    @Override
    public int opNum() {
        return 10;
    }

    @Override
    public String name() {
        return "distribution_lognormal";
    }

    @Override
    public boolean isExecSpecial() {
        return true;
    }

    @Override
    public List<DifferentialFunction> doDiff(List<DifferentialFunction> f1) {
        return null;
    }
}
