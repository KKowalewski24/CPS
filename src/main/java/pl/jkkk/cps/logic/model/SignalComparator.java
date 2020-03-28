package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.exception.NotSameLengthException;
import pl.jkkk.cps.logic.model.signal.Signal;

public class SignalComparator {

    public double meanSquaredError(Signal result, Signal origin){
        if(result.getData().size() != origin.getData().size())
            throw new NotSameLengthException();

        double sum = 0.0;
        for(int i = 0; i < result.getData().size(); i++){
            sum += Math.pow(result.getData().get(i).getY() - origin.getData().get(i).getY(), 
                    2.0);
        }

        return sum / result.getData().size();
    }

    public double signalToNoiseRatio(Signal result, Signal origin){
        if(result.getData().size() != origin.getData().size())
            throw new NotSameLengthException();

        double resultSquaredSum = 0.0;
        double diffSquaredSum = 0.0;
        for(int i = 0; i < result.getData().size(); i++){
            resultSquaredSum += Math.pow(result.getData().get(i).getY(), 2.0);
            diffSquaredSum += Math.pow(result.getData().get(i).getY() - 
                    origin.getData().get(i).getY(), 2.0);
        }

        return 10.0 * Math.log10(resultSquaredSum / diffSquaredSum);
    }

    public double peakSignalToNoiseRatio(Signal result, Signal origin){
        if(result.getData().size() != origin.getData().size())
            throw new NotSameLengthException();

        double resultMax = Double.MIN_VALUE;
        double diffSquaredSum = 0.0;
        for(int i = 0; i < result.getData().size(); i++){
            if(result.getData().get(i).getY() > resultMax)
                resultMax = result.getData().get(i).getY();
            diffSquaredSum += Math.pow(result.getData().get(i).getY() - 
                    origin.getData().get(i).getY(), 2.0);
        }

        return 10.0 * Math.log10(resultMax / (diffSquaredSum / result.getData().size()));
    }

    public double maximumDifference(Signal result, Signal origin){
        if(result.getData().size() != origin.getData().size())
            throw new NotSameLengthException();

        double maxDiff = Double.MIN_VALUE;
        for(int i = 0; i < result.getData().size(); i++){
            double diff = Math.abs(result.getData().get(i).getY() - 
                    origin.getData().get(i).getY());
            if(diff > maxDiff)
                maxDiff = diff;
        }

        return maxDiff;
    }

    public double effectiveNumberOfBits(Signal result, Signal origin){
        return (signalToNoiseRatio(result, origin) - 1.76) / 6.02;       
    }
}
