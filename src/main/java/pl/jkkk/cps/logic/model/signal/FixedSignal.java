package pl.jkkk.cps.logic.model.signal;

import java.util.List;

import pl.jkkk.cps.logic.model.Data;

public class FixedSignal extends Signal{
    
    private List<Data> sourceData;

    public FixedSignal(List<Data> sourceData){
        super(sourceData.size());
        this.sourceData = sourceData;
    }

    @Override
    public void generate(){
        for(int i = 0; i < sourceData.size(); i++){
            data[i] = sourceData.get(i);
        }
    }
}
