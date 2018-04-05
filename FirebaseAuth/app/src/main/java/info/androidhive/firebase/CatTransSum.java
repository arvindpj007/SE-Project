package info.androidhive.firebase;

public class CatTransSum {

    String key;
    Integer sum;

    CatTransSum(){
        key="";
        sum=0;
    }

    CatTransSum(String key,Integer sum){
        this.key = key;
        this.sum = sum;
    }

    public String getKey(){
        return key;
    }

    public Integer getSum(){
        return sum;
    }

}
