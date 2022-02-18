import java.util.*;

public class Combination {
	    // members:
	    private List<Integer[]> indexcombo = new ArrayList<Integer[]>();

	    // methods
	    
	    // this method shold be private and have a wrapper that calls it coorectly while only needing an alphabet (arr)
	    // and a lenght for combinantions (len), given the correction suggested in the comments below
	    public void combinations(Integer[] arr, int len, int startPosition, Integer[] result){

	    	if (len == 0){
	    		//this should be result.clone() for when the method is called with len>2
			Integer[] Intarr = {result[0], result[1]}; 
			this.indexcombo.add(Intarr); 
			return;
		}
		for(int i = startPosition; i <= arr.length-len; i++){
			result[result.length - len] = arr[i];
			combinations(arr, len-1, i+1, result);
		}
	    }

	    public List<Integer[]> getCombos(){
	    	return this.indexcombo;
	    }
}

